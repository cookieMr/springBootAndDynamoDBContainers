package mr.cookie.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mr.cookie.dynamodb.DynamoDbService;
import mr.cookie.models.mq.Movie;
import org.jetbrains.annotations.Nullable;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RabbitListener(queues = "insertMoviesQueue")
@RequiredArgsConstructor
public class MovieMQListenerImpl implements MovieMQListener {

    private final DynamoDbService dynamoDbService;
    private final ObjectMapper objectMapper;
    private final AtomicInteger counter = new AtomicInteger();

    @Value("${mr.cookie.queue.name:#{null}}")
    private @Nullable String queueName;

    @PostConstruct
    public void postConstruct() {
        log.info("A queue name is: {}", queueName);
        Objects.requireNonNull(queueName);
    }

    @Override
    @RabbitHandler
    public void consumeMessage(@Nullable String msg) throws JsonProcessingException {
        log.info("Consuming a message: {}", msg);
        Movie movie = objectMapper.readValue(msg, Movie.class);

        dynamoDbService.save(movie);
        counter.incrementAndGet();
    }

    @Override
    public int getCounter() {
        return counter.get();
    }

}
