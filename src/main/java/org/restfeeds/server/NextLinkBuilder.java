package org.restfeeds.server;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class NextLinkBuilder {

  public String nextLink(String feed, long currentPosition) {
    long nextPosition = currentPosition + 1;
    ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
    builder.replaceQueryParam("offset", nextPosition);

//    // ServletUriComponentsBuilder does not reflect the x-forwarded-proto header?
//    getCurrentHttpRequest()
//        .ifPresent(
//            request -> {
//              String protoHeader = request.getHeader("x-forwarded-proto");
//              if (protoHeader != null) {
//                builder.scheme(protoHeader);
//              }
//            });

    return builder.toUriString();
  }

  public static Optional<HttpServletRequest> getCurrentHttpRequest() {
    return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
        .filter(
            requestAttributes ->
                ServletRequestAttributes.class.isAssignableFrom(requestAttributes.getClass()))
        .map(requestAttributes -> ((ServletRequestAttributes) requestAttributes))
        .map(ServletRequestAttributes::getRequest);
  }
}
