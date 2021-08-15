package mr.cookie.models.dynamodb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class MovieDB {

    private String title;
    private Integer releaseYear;
    private String createdAt;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("title")
    public String getTitle() {
        return title;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("release_year")
    public Integer getReleaseYear() {
        return releaseYear;
    }

    @DynamoDbAttribute("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

}
