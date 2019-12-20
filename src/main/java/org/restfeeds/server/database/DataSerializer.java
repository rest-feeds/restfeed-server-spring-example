package org.restfeeds.server.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

class DataSerializer {

  private static final ObjectMapper objectMapper =
      Jackson2ObjectMapperBuilder.json().failOnUnknownProperties(false).build();

  static String toString(Object data) {
    try {
      return objectMapper.writeValueAsString(data);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  static Object toObject(String data) {
    try {
      return objectMapper.readValue(data, Object.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

}
