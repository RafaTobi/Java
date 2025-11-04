package be.kdg.sa.warehouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class Clockconfig {
    @Bean
    public Clock clock(){
        return Clock.systemDefaultZone();
    }
}
