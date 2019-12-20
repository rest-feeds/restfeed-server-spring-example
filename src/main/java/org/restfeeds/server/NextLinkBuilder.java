package org.restfeeds.server;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class NextLinkBuilder {

  public String nextLink(String feed, long currentPosition) {
    long nextPosition = currentPosition + 1;
    ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
    builder.replaceQueryParam("offset", nextPosition);
    return builder.toUriString();
  }
}
