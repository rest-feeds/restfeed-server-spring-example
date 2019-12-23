package org.restfeeds.server.spring.example;

import java.math.BigDecimal;

public class Movie {

  private Long id;
  private String original_title;
  private BigDecimal popularity;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOriginal_title() {
    return original_title;
  }

  public void setOriginal_title(String original_title) {
    this.original_title = original_title;
  }

  public BigDecimal getPopularity() {
    return popularity;
  }

  public void setPopularity(BigDecimal popularity) {
    this.popularity = popularity;
  }

}
