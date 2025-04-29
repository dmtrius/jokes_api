package pl.jokes.jokesms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.jokes.jokesms.dto.Joke;
import pl.jokes.jokesms.service.JokesService;

import java.util.List;

@RequestMapping("/api/v1")
@RestController
public class JokesController {
  private final JokesService jokesService;
  private static final int MAX_JOKES = 100;

  public JokesController(JokesService jokesService) {
    this.jokesService = jokesService;
  }

  @GetMapping("/jokes")
  public ResponseEntity<List<Joke>> getJokes(@RequestParam(value = "count", defaultValue = "5") int count) {
    if (count < 1 || count > MAX_JOKES) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "more then 1, and no more then " + MAX_JOKES);
    }
    return ResponseEntity.ok(jokesService.getJoke(count));
  }
}
