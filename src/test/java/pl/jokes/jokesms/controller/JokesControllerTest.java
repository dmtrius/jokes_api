package pl.jokes.jokesms.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JokesController.class)
public class JokesControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldReturnDefaultJokes() throws Exception {
    mockMvc.perform(get("/jokes"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void shouldReturnSpecifiedNumberOfJokes() throws Exception {
    mockMvc.perform(get("/jokes").param("count", "3"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(3));
  }

  @Test
  void shouldReturnBadRequestForTooManyJokes() throws Exception {
    mockMvc.perform(get("/jokes").param("count", "101"))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.status().reason("more then 1, and no more then 100"));
  }
}
