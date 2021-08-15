package mr.cookie.dynamodb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mr.cookie.models.mappers.MovieMapper;
import mr.cookie.models.mq.Movie;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DynamoDbServiceImpl implements DynamoDbService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public void save(@NotNull Movie movie) {
        Optional.of(movie)
                .map(movieMapper::mapToDynamoDb)
                .ifPresent(movieRepository::save);
    }

    @Override
    public @NotNull List<Movie> getAll() {
        return movieRepository.getAllMovies().stream()
                .map(movieMapper::mapFromDynamoDb)
                .collect(Collectors.toList());
    }

    @Override
    public @Nullable Movie findMovie(@NotNull String title, @NotNull Integer year) {
        return Optional.ofNullable(movieRepository.getMovie(title, year))
                .map(movieMapper::mapFromDynamoDb)
                .orElse(null);
    }

    @Override
    public @Nullable Movie deleteMovie(@NotNull String title, @NotNull Integer year) {
        return Optional.ofNullable(movieRepository.deleteMovie(title, year))
                .map(movieMapper::mapFromDynamoDb)
                .orElse(null);
    }

}
