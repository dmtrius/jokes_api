package pl.jokes.jokesms.service;

import pl.jokes.jokesms.dto.Joke;

import java.util.List;

public interface JokesService {
  List<Joke> getJokes(int count);
  Joke getJoke();
}
