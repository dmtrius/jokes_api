package pl.jokes.jokesms.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import pl.jokes.jokesms.dto.Joke;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JokesServiceTest {
  @Mock
  private HttpClient httpClient;
  private JokesService jokesService;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() throws Exception {
    objectMapper = new ObjectMapper();
    jokesService = new JokesServiceImpl(objectMapper);
    ReflectionTestUtils.setField(jokesService, "httpClient", httpClient);
    ReflectionTestUtils.setField(jokesService, "JOKE_API_URL", "http://test.com");

    String mockResponse = """
            {
                "id": 1,
                "type": "general",
                "setup": "Test joke",
                "punchline": "Test punchline"
            }
            """;

    HttpResponse<Object> httpResponse = (HttpResponse<Object>) mock(HttpResponse.class);
    when(httpResponse.body()).thenReturn(mockResponse);
    when(httpClient.send(any(), any())).thenReturn(httpResponse);
  }

  @Test
  void shouldFetchSingleJoke() throws Exception {
    List<Joke> jokes = jokesService.getJoke(1);

    assertEquals(1, jokes.size());
    Joke joke = jokes.getFirst();
    assertEquals("Test joke", joke.getSetup());
    assertEquals("Test punchline", joke.getPunchline());
  }

  @Test
  void shouldFetchTenJokes() throws Exception {
    List<Joke> jokes = jokesService.getJoke(10);

    assertEquals(10, jokes.size());
    jokes.forEach(joke -> {
      assertEquals("Test joke", joke.getSetup());
      assertEquals("Test punchline", joke.getPunchline());
    });
  }
}
