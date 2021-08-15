package mr.cookie.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mr.cookie.models.mq.Movie;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackendServiceImpl implements BackendService {

    @Value("${mr.cookie.backend.url:#{null}}")
    private String url;

    @Value("${mr.cookie.backend.port:#{null}}")
    private Integer port;

    private final RestTemplate restTemplate;

    @PostConstruct
    public void validateUrlAndPort() {
        Objects.requireNonNull(url);
        Objects.requireNonNull(port);
        log.info("Connecting to backend service at {}", url);
    }

    @Override
    public int getProcessedMovieCount() {
        String url = buildUrl("processed");
        log.info("Sending request to: {}", url);

        return Optional.ofNullable(restTemplate.getForObject(url, Integer.class))
                .orElse(-1);
    }

    @Override
    public @NotNull List<Movie> getAll() {
        String url = buildUrl(null);
        log.info("Sending GET request to: {}", url);

        Movie[] movies = restTemplate.getForObject(url, Movie[].class);
        return movies == null
                ? Collections.emptyList()
                : Arrays.asList(movies);
    }

    @Override
    public @Nullable Movie findMovie(@NotNull String title, @NotNull Integer releaseYear) {
        String titleAndYear = String.format("%s/%d", title, releaseYear);
        String url = buildUrl(titleAndYear);
        log.info("Sending GET request to: {}", url);

        return restTemplate.getForObject(url, Movie.class);
    }

    @Override
    public void deleteMovie(@NotNull String title, @NotNull Integer releaseYear) {
        String titleAndYear = String.format("%s/%d", title, releaseYear);
        String url = buildUrl(titleAndYear);
        log.info("Sending DELETE request to: {}", url);

        restTemplate.delete(url);
    }

    private @NotNull String buildUrl(@Nullable String path) {
        StringBuilder urlBuilder =  new StringBuilder()
                .append(url)
                .append(":")
                .append(port.toString())
                .append("/api/movies");

        Optional.ofNullable(path)
                .ifPresent(subPath -> urlBuilder.append("/").append(subPath));

        return urlBuilder.toString();
    }

}
