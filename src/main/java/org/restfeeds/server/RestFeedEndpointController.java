package org.restfeeds.server;

import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.Duration;
import java.time.Instant;
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

  static final Logger logger = LoggerFactory.getLogger(RestFeedEndpointController.class);

  static final int LIMIT = 1000;
  static final Duration POLL_INTERVAL = Duration.of(50L, MILLIS);
  static final Duration TIMEOUT = Duration.of(5, SECONDS);

  private final FeedItemRepository feedRepository;

  public RestFeedEndpointController(FeedItemRepository feedRepository) {
    this.feedRepository = feedRepository;
  }

  @GetMapping
  public List<FeedItem> getFeed(
      @PathVariable("feed") String feed,
      @RequestParam(value = "offset", defaultValue = "0") long offset) {
    logger.debug("GET feed {} with offset {}", feed, offset);
    Instant timeout = Instant.now().plus(TIMEOUT);
    List<FeedItem> items = longPolling(feed, offset, timeout);
    logger.debug("GET feed {} with offset {} returned {} items", feed, offset, items.size());
    return items;
  }

  private List<FeedItem> longPolling(String feed, long offset, Instant timeout) {
    logger.debug(
        "Polling for items in feed {} with offset {} and a timeout of {}", feed, offset, timeout);
    List<FeedItem> items;
    while (true) {
      items = feedRepository.findByFeedPositionGreaterThanEqual(feed, offset, LIMIT);

      if (items.size() > 0) {
        logger.debug("Found {} items. Returning the response.", items.size());
        return items;
      }

      if (Instant.now().isAfter(timeout)) {
        logger.debug("Polling timed out. Returning the empty response.");
        return items;
      }

      try {
        logger.trace("No items found. Wait a bit and then retry again.");
        Thread.sleep(POLL_INTERVAL.toMillis());
      } catch (InterruptedException e) {
        logger.debug(
            "Thread was interrupted while sleeping. Probably a graceful shutdown was triggered. Try to send the current response to the client.");
        return items;
      }
    }
  }
}
