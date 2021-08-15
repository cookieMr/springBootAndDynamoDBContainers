package mr.cookie.dynamodb.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.ListTablesRequest;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DynamoDBUtils {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class McuMovieTable {

        public static final String TABLE_NAME = "mcu_movies";
        public static final String PARTITION_KEY_NAME = "title";
        public static final String SORT_KEY_NAME = "release_year";

        public static final @NotNull ListTablesRequest LIST_TABLES_REQUEST = ListTablesRequest.builder().build();

        public static final @NotNull CreateTableRequest CREATE_TABLE_REQUEST = CreateTableRequest.builder()
                    .attributeDefinitions(
                            AttributeDefinition.builder()
                                    .attributeName(PARTITION_KEY_NAME)
                                    .attributeType(ScalarAttributeType.S)
                                    .build(),
                            AttributeDefinition.builder()
                                    .attributeName(SORT_KEY_NAME)
                                    .attributeType(ScalarAttributeType.N)
                                    .build())
                    .keySchema(
                            KeySchemaElement.builder()
                                    .attributeName(PARTITION_KEY_NAME)
                                    .keyType(KeyType.HASH)
                                    .build(),
                            KeySchemaElement.builder()
                                    .attributeName(SORT_KEY_NAME)
                                    .keyType(KeyType.RANGE)
                                    .build())
                    .provisionedThroughput(
                            ProvisionedThroughput.builder()
                                    .readCapacityUnits(10L)
                                    .writeCapacityUnits(10L).build())
                    .tableName(TABLE_NAME)
                    .build();

    }

}
