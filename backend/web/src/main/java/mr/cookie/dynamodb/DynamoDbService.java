package mr.cookie.dynamodb;

import mr.cookie.models.mq.Movie;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface DynamoDbService {

    void save(@NotNull Movie movie);

    @NotNull List<Movie> getAll();

    @Nullable Movie findMovie(@NotNull String title, @NotNull Integer year);

    @Nullable Movie  deleteMovie(@NotNull String title, @NotNull Integer year);

}
