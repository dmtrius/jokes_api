package pl.jokes.jokesms.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jokes.jokesms.dto.Joke;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JokesServiceTest {

  private JokesService jokesService;

  @BeforeEach
  void setUp() {
    jokesService = new JokesServiceImpl(new ObjectMapper());
  }

  @Test
  void fetchSingleJoke() {
    List<Joke> jokes = jokesService.getJoke(1);
    assertEquals(1, jokes.size());
    assertNotNull(jokes.getFirst().getSetup());
    assertNotNull(jokes.getFirst().getPunchline());
  }

  @Test
  void fetchTenJokes() {
    Object jokeService;
    List<Joke> jokes = jokesService.getJoke(10);
    assertEquals(10, jokes.size());
    jokes.forEach(joke -> {
      assertNotNull(joke.getSetup());
      assertNotNull(joke.getPunchline());
    });
  }
}
