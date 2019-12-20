package org.restfeeds.server;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{feed:[a-zA-Z]+}")
public class RestFeedEndpointController {

  static final Logger log = LoggerFactory.getLogger(RestFeedEndpointController.class);

  private final RestFeedEndpoint restFeedEndpoint;

  public RestFeedEndpointController(RestFeedEndpoint restFeedEndpoint) {
    this.restFeedEndpoint = restFeedEndpoint;
  }

  @GetMapping
  public List<FeedItem> getFeed(
      @PathVariable("feed") String feed,
      @RequestParam(value = "offset", defaultValue = "0") long offset) {
    log.debug("GET feed {} with offset {}", feed, offset);
    List<FeedItem> items = restFeedEndpoint.poll(feed, offset);
    log.debug("GET feed {} with offset {} returned {} items", feed, offset, items.size());
    return items;
  }
}
