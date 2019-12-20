package org.restfeeds.server.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.UUID;
import org.restfeeds.server.FeedItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

@Component
public class MovieFeedInitializer implements ApplicationListener<ApplicationReadyEvent> {

  private static final Logger log = LoggerFactory.getLogger(MovieFeedInitializer.class);

  private final FeedItemRepository feedItemRepository;

  public MovieFeedInitializer(FeedItemRepository feedItemRepository) {
    this.feedItemRepository = feedItemRepository;
  }

  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {

    try {
      BufferedReader bufferedReader =
          new BufferedReader(
              new InputStreamReader(
                  new ClassPathResource("example/movie_ids_12_14_2019.json").getInputStream()));
      ObjectMapper objectMapper =
          Jackson2ObjectMapperBuilder.json().failOnUnknownProperties(false).build();
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        Movie movie = objectMapper.readValue(line, Movie.class);

        feedItemRepository.append(
            "movies",
            UUID.randomUUID().toString(),
            "application/vnd.org.themoviedb.movie",
            "/movies/" + movie.getId(),
            "PUT",
            Instant.now().toString(),
            movie);

      }
      log.info("Finished initializing the database");

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
