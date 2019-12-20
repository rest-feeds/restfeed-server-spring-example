package org.restfeeds.server;

import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RestFeedEndpoint {

  static final Logger log = LoggerFactory.getLogger(RestFeedEndpoint.class);

  static final int LIMIT = 1000;
  static final Duration POLL_INTERVAL = Duration.of(50L, MILLIS);
  static final Duration TIMEOUT = Duration.of(5, SECONDS);

  private final FeedItemRepository feedRepository;

  public RestFeedEndpoint(FeedItemRepository feedRepository) {
    this.feedRepository = feedRepository;
  }

  public List<FeedItem> poll(String feed, long offset) {
    Instant timeout = Instant.now().plus(TIMEOUT);
    log.debug(
        "Polling for items in feed {} with offset {} and a timeout of {}", feed, offset, timeout);
    List<FeedItem> items;
    while (true) {
      items = feedRepository.findByFeedPositionGreaterThanEqual(feed, offset, LIMIT);

      if (items.size() > 0) {
        log.debug("Returning {} items.", items.size());
        return items;
      }

      if (Instant.now().isAfter(timeout)) {
        log.debug("Polling timed out. Returning the empty response.");
        return items;
      }

      try {
        log.trace("No items found. Wait a bit and then retry again.");
        Thread.sleep(POLL_INTERVAL.toMillis());
      } catch (InterruptedException e) {
        log.debug("Thread was interrupted. Probably a graceful shutdown. Try to send response.");
        return items;
      }
    }
  }
}
