package mr.cookie.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mr.cookie.models.dto.MovieDto;
import mr.cookie.models.mappers.MovieMapper;
import mr.cookie.mq.MqService;
import mr.cookie.services.BackendService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/movies" , produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MovieController {

    private final MqService mqService;
    private final MovieMapper movieMapper;
    private final BackendService backendService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public @NotNull Integer addMovie(@NotNull @RequestBody MovieDto movie) {
        log.info("Received a post request.");
        Optional.of(movie)
                .map(movieMapper::mapFromDto)
                .ifPresent(mqService::insert);
        log.info("A Movie was put into queue.");

        return mqService.getQueueSize();
    }

    @GetMapping("/{title}/{year}")
    public @Nullable MovieDto findMovie(
            @PathVariable @NotNull String title,
            @PathVariable @NotNull Integer year) {
        return movieMapper.mapToDto(backendService.findMovie(title, year));
    }

    @DeleteMapping("/{title}/{year}")
    public void deleteMovie(
            @PathVariable @NotNull String title,
            @PathVariable @NotNull Integer year) {
        backendService.deleteMovie(title, year);
    }

    @GetMapping
    public @NotNull List<MovieDto> getAllMovies() {
        return backendService.getAll().stream()
                .map(movieMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/size/queue")
    public @NotNull Integer getQueueSize() {
        return mqService.getQueueSize();
    }

    @GetMapping(path = "/processed")
    public @NotNull Integer getProcessedMovieCount() {
        return backendService.getProcessedMovieCount();
    }

}
