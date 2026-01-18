package pl.lukasz.blcweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "pl.lukasz")
@EnableScheduling
@EnableJpaRepositories(basePackages = "pl.lukasz.blcdata.repository")
@EntityScan(basePackages = "pl.lukasz.blcdata.entity")
public class BlcWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlcWebApplication.class, args);
    }
}