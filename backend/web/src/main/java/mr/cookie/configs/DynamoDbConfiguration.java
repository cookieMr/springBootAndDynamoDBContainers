package mr.cookie.configs;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Objects;

@Slf4j
@Configuration
public class DynamoDbConfiguration {

    @Value("${amazon.dynamodb.endpoint:#{null}}")
    private String dynamodbEndpoint;

    @Value("${amazon.dynamodb.region:#{null}}")
    private String dynamodbRegion;

    @Value("${amazon.aws.accesskey:#{null}}")
    private String accessKey;

    @Value("${amazon.aws.secretkey:#{null}}")
    private String secretKey;

    @PostConstruct
    public void postConstruct() {
        log.info("Connecting to DynamoDB at {}", dynamodbEndpoint);
    }

    @Bean
    public @NotNull AwsCredentials awsCredentials() {
        Objects.requireNonNull(accessKey);
        Objects.requireNonNull(secretKey);
        return AwsBasicCredentials.create(accessKey, secretKey);
    }

    @Bean
    public @NotNull AwsCredentialsProvider awsCredentialsProvider(
            @NotNull AwsCredentials awsCredentials) {
        return StaticCredentialsProvider.create(awsCredentials);
    }

    @Bean
    public @NotNull DynamoDbClient dynamoDbClient(
            @NotNull AwsCredentialsProvider awsCredentialsProvider) {
        Objects.requireNonNull(dynamodbRegion);
        Objects.requireNonNull(dynamodbEndpoint);
        return DynamoDbClient.builder()
                .region(Region.of(dynamodbRegion))
                .credentialsProvider(awsCredentialsProvider)
                .endpointOverride(URI.create(dynamodbEndpoint))
                .build();
    }

    @Bean
    public @NotNull DynamoDbEnhancedClient getDynamoDbEnhancedClient(
            @NotNull DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

}
