package pl.jokes.jokesms.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.jokes.jokesms.dto.Joke;
import pl.jokes.jokesms.service.JokesServiceImpl;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(JokesController.class)
public class JokesControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private JokesServiceImpl jokesService;

  @Test
  void shouldReturnDefaultJokes() throws Exception {
    List<Joke> mockJokes = List.of(new Joke(1, "general", "Why?", "Because."));
    when(jokesService.getJoke(5)).thenReturn(mockJokes);

    mockMvc.perform(get("/api/v1/jokes"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void shouldReturnSpecifiedNumberOfJokes() throws Exception {
    List<Joke> mockJokes = List.of(
        new Joke(1, "general", "Why?", "Because."),
        new Joke(2, "general", "What?", "That."),
        new Joke(3, "general", "How?", "This way.")
    );
    when(jokesService.getJoke(3)).thenReturn(mockJokes);

    mockMvc.perform(get("/api/v1/jokes").param("count", "3"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(3));
  }

  @Test
  void shouldReturnBadRequestForTooManyJokes() throws Exception {
    mockMvc.perform(get("/api/v1/jokes").param("count", "101"))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.status().reason("more then 1, and no more then 100"));
  }
}
