package org.restfeeds.server.example;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import javax.annotation.PostConstruct;
import org.restfeeds.server.FeedItem;
import org.restfeeds.server.FeedItemRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

@Component
public class MongodbFeedItemRepository implements FeedItemRepository {

  private final MongoTemplate mongoTemplate;

  public MongodbFeedItemRepository(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @PostConstruct
  void importMoviesFromFile() throws Exception {
    BufferedReader bufferedReader =
        new BufferedReader(
            new InputStreamReader(
                new GZIPInputStream(
                    new ClassPathResource("movie_ids_12_14_2019.json.gz").getInputStream())));
    ObjectMapper objectMapper =
        Jackson2ObjectMapperBuilder.json().failOnUnknownProperties(false).build();
    String line;
    int i = 0;
    while ((line = bufferedReader.readLine()) != null) {
      Movie movie = objectMapper.readValue(line, Movie.class);
      FeedItem item = new FeedItem();
      item.setOffset(i++);
      item.setId(UUID.randomUUID().toString());
      item.setMethod("PUT");
      item.setUri("/movies/" + movie.getId());
      item.setType("application/vnd.org.themoviedb.movie");
      item.setTimestamp(Instant.now().toString());
      item.setData(movie);
      mongoTemplate.save(item);
    }
  }

  @Override
  public List<FeedItem> findByOffsetGreaterThanEqual(long offset, int limit) {
    return mongoTemplate.find(query(where("_id").gte(offset)).limit(limit), FeedItem.class);
  }
}
