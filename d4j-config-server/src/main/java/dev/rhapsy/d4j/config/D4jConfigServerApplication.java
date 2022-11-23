package dev.rhapsy.d4j.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class D4jConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(D4jConfigServerApplication.class, args);
    }


}
