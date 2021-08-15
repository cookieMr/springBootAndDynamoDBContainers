package mr.cookie.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mr.cookie.models.mq.Movie;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MqServiceImpl implements MqService {

    @Value("${mr.cookie.queue.name:#{null}}")
    private String queueName;

    private final RabbitTemplate template;
    private final AmqpAdmin amqpAdmin;
    private final Queue queue;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void postConstruct() {
        log.info("The queue message count is: {}", getQueueSize());
    }

    @Override
    public void insert(@NotNull Movie movie) {
        try {
            String mqMsg = objectMapper.writeValueAsString(movie);
            template.convertAndSend(queue.getName(), mqMsg);
            log.info("A Movie was put into queue.");
        } catch (JsonProcessingException e) {
            log.error("Serialization of object failed.", e);
        }
    }

    @Override
    public int getQueueSize() {
        return Optional.ofNullable(queueName)
                .map(amqpAdmin::getQueueProperties)
                .map(properties -> properties.get("QUEUE_MESSAGE_COUNT"))
                .map(Integer.class::cast)
                .orElseThrow(() -> new IllegalStateException("Could not fetch message count from RabbitMQ's Queue."));
    }

}
