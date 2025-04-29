package pl.jokes.jokesms.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.jokes.jokesms.dto.Joke;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class JokesServiceImpl implements JokesService {
    private final ObjectMapper objectMapper;

    @Value("${jokes.apiUrl}")
    private String JOKE_API_URL;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    public JokesServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Joke> getJoke(int count) {
        try {
            List<Joke> jokes = new ArrayList<>();
            int batches = (int) Math.ceil(count / 10.0);

            for (int i = 0; i < batches; i++) {
                int batchSize = Math.min(10, count - (i * 10));
                List<CompletableFuture<Joke>> batch = new ArrayList<>();

                for (int j = 0; j < batchSize; j++) {
                    batch.add(CompletableFuture.supplyAsync(() -> {
                        try {
                          return fetchJoke();
                        } catch (Exception e) {
                            throw new CompletionException(e);
                        }
                    }, executor));
                }

                List<Joke> batchResults = batch.stream()
                    .map(CompletableFuture::join)
                    .toList();

                jokes.addAll(batchResults);
            }

            return jokes;
        } catch (CompletionException e) {
            //log.error("Error getting jokes", e);
            Throwable cause = e.getCause();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Failed to retrieve jokes: " + cause.getMessage(), cause);
        }
    }

    @SneakyThrows
    private Joke fetchJoke() {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(JOKE_API_URL))
            .GET()
            .build();
        try {
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), Joke.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to fetch joke", e);
        }
    }
}
