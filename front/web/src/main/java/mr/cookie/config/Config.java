package mr.cookie.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class Config {

    @Value("${spring.profiles.active:#{null}}")
    private @Nullable String activeProfile;

    @PostConstruct
    public void postConstruct() {
        log.info("The active spring profile is: {}", activeProfile);
    }

    @Bean
    public @NotNull ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper;
    }

    @Bean
    public @NotNull RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
