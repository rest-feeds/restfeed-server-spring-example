package org.restfeeds.example.server.spring;

import org.restfeeds.server.RestFeedEndpoint;
import org.restfeeds.server.spring.RestFeedEndpointController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestFeedServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(RestFeedServerApplication.class, args);
  }

  @Bean
  public RestFeedEndpointController restFeedEndpointController(RestFeedEndpoint restFeedEndpoint) {
    return new RestFeedEndpointController(restFeedEndpoint);
  }
}
