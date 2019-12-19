package org.restfeeds.server;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.restfeeds.server.RestFeedEndpointController.PATH;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PATH)
public class RestFeedEndpointController {

  protected static final String PATH = "/movies";

  private static final Logger logger = LoggerFactory.getLogger(RestFeedEndpointController.class);
  private final FeedItemRepository feedRepository;

  public RestFeedEndpointController(FeedItemRepository feedRepository) {
    this.feedRepository = feedRepository;
  }

  @GetMapping
  public List<FeedItem> getFeed(@RequestParam(value = "offset", defaultValue = "0") long offset) {
    logger.debug("GET feed {} with offset {}", PATH, offset);

    Instant timeout = Instant.now().plus(5, SECONDS);

    List<FeedItem> items;
    while (true) {
      items = feedRepository.findByOffsetGreaterThanEqual(offset, 100);

      if (!items.isEmpty() || Instant.now().isAfter(timeout)) {
        break;
      }

      try {
        Thread.sleep(50L);
      } catch (InterruptedException e) {
        break;
      }
    }

    return items.stream().map(this::addNextLink).collect(Collectors.toList());
  }

  private FeedItem addNextLink(FeedItem item) {
    long nextOffset = item.getOffset() + 1;
    item.setNext(PATH + "?offset=" + nextOffset);
    return item;
  }
}
