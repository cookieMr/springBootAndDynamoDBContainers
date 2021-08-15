package mr.cookie.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mr.cookie.dynamodb.DynamoDbService;
import mr.cookie.models.dto.MovieDto;
import mr.cookie.models.mappers.MovieMapper;
import mr.cookie.mq.MovieMQListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/movies", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MovieController {

    private final MovieMapper movieMapper;
    private final DynamoDbService dynamoDbService;
    private final MovieMQListener movieMQListener;

    @GetMapping
    public @NotNull List<MovieDto> getAllMovies() {
        return dynamoDbService.getAll()
                .stream()
                .map(movieMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{title}/{year}")
    public @Nullable MovieDto findMovie(
            @PathVariable @NotNull String title,
            @PathVariable @NotNull Integer year) {
        return movieMapper.mapToDto(dynamoDbService.findMovie(title, year));
    }

    @DeleteMapping("/{title}/{year}")
    public @Nullable MovieDto deleteMovie(
            @PathVariable @NotNull String title,
            @PathVariable @NotNull Integer year) {
        return movieMapper.mapToDto(dynamoDbService.deleteMovie(title, year));
    }

    @GetMapping("/processed")
    public @NotNull Integer getProcessedMovieCount() {
        return movieMQListener.getCounter();
    }

}
