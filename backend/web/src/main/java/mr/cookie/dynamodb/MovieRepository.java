package mr.cookie.dynamodb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mr.cookie.models.dynamodb.MovieDB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static mr.cookie.dynamodb.utils.DynamoDBUtils.McuMovieTable.CREATE_TABLE_REQUEST;
import static mr.cookie.dynamodb.utils.DynamoDBUtils.McuMovieTable.LIST_TABLES_REQUEST;
import static mr.cookie.dynamodb.utils.DynamoDBUtils.McuMovieTable.TABLE_NAME;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MovieRepository {

    private final DynamoDbClient dynamoDbClient;
    private final DynamoDbEnhancedClient dynamoDbenhancedClient;

    @PostConstruct
    public void createTableIfDoesNotExist() {
        List<String> tableList = dynamoDbClient.listTables(LIST_TABLES_REQUEST).tableNames();

        if (!CollectionUtils.isEmpty(tableList)
                && tableList.stream().anyMatch(TABLE_NAME::equals)) {
            log.info("Table already {} exists.", TABLE_NAME);
            return;
        }
        log.info("Table {} does not exists.", TABLE_NAME);

        dynamoDbClient.createTable(CREATE_TABLE_REQUEST);
        log.info("Table created.");
    }

    public void save(@NotNull MovieDB movie) {
        movie.setCreatedAt(getCurrentTimeStamp());
        getTable().putItem(movie);
    }

    public @Nullable MovieDB getMovie(@NotNull String title, @NotNull Integer releaseYear) {
        Key key = Key.builder()
                .partitionValue(title)
                .sortValue(releaseYear)
                .build();

        return getTable().getItem(key);
    }

    public @Nullable MovieDB deleteMovie(@NotNull String title, @NotNull Integer releaseYear) {
        Key key = Key.builder()
                .partitionValue(title)
                .sortValue(releaseYear)
                .build();

        return getTable().deleteItem(key);
    }

    public @NotNull List<MovieDB> getAllMovies() {
        return getTable().scan()
                .items()
                .stream()
                .collect(Collectors.toList());
    }


    public @NotNull String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                .format(new Date());
    }


    private @NotNull DynamoDbTable<MovieDB> getTable() {
        return dynamoDbenhancedClient.table(
                TABLE_NAME,
                TableSchema.fromBean(MovieDB.class));
    }

}
