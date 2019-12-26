package org.restfeeds.example.server.spring;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class RestFeedServerExampleApplicationTests {

  @Autowired
  MockMvc mvc;

  @Test
  void contextLoads() {}

  @Test
  void fetchFeedItems() throws Exception {
    // wait a bit until test data are initialized
    Thread.sleep(200L);

    mvc.perform(MockMvcRequestBuilders.get("/movies"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("[0].id").exists())
        .andExpect(jsonPath("[0].next").value("http://localhost/movies?offset=2"))
        .andExpect(jsonPath("[0].data.original_title").value("Blondie"))
        .andExpect(jsonPath("[999].id").exists())
        .andExpect(jsonPath("[1000].id").doesNotExist());
  }


}
