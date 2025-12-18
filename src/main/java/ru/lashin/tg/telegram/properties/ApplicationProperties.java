package ru.lashin.tg.telegram.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "bot")
@Data
public class ApplicationProperties {

    private String username;
    private String token;

}
