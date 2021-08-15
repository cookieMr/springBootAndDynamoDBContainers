package mr.cookie;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class CookieApp {

    public static void main(@Nullable String[] args) {
        SpringApplication.run(CookieApp.class, args);
        log.info("The backend application is running...");
    }

}
