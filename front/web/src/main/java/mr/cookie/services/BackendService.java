package mr.cookie.services;

import mr.cookie.models.mq.Movie;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface BackendService {

    int getProcessedMovieCount();

    @NotNull List<Movie> getAll();

    @Nullable Movie findMovie(@NotNull String title, @NotNull Integer releaseYear);

    void deleteMovie(@NotNull String title, @NotNull Integer releaseYear);

}
