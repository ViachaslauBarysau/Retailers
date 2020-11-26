package by.itechart.retailers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootConfiguration
@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@ComponentScan
@EnableRetry
@EnableScheduling
public class RetailersApplication {

    public static void main(String[] args) {
        SpringApplication.run(RetailersApplication.class, args);
    }

}
