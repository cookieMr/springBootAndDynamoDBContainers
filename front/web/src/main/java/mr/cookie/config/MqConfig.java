package mr.cookie.config;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Slf4j
@Configuration
public class MqConfig {

    @Value("${mr.cookie.queue.name:#{null}}")
    private @Nullable String queueName;

    @Value("${spring.rabbitmq.host:#{null}}")
    private @Nullable String rabbitMqHost;

    @PostConstruct
    public void postConstruct() {
        log.info("Connecting to RabbitMQ at {}", rabbitMqHost);
    }

    @Bean
    public @NotNull Queue queue() {
        Objects.requireNonNull(queueName, "Queue name is not configured!");
        return new Queue(queueName);
    }

}
