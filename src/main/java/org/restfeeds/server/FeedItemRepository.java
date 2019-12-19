package org.restfeeds.server;

import java.util.List;

public interface FeedItemRepository {

  List<FeedItem> findByOffsetGreaterThanEqual(long offset, int limit);
}
