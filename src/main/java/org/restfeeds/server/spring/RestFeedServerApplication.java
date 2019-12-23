package org.restfeeds.server.spring;

import org.restfeeds.server.FeedItemRepository;
import org.restfeeds.server.RestFeedEndpoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestFeedServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(RestFeedServerApplication.class, args);
  }

  @Bean
  RestFeedEndpoint restFeedEndpoint(FeedItemRepository feedItemRepository) {
    return new RestFeedEndpoint(feedItemRepository);
  }
}
