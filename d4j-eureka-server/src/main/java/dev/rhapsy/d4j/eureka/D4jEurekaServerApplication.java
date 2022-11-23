package dev.rhapsy.d4j.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class D4jEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(D4jEurekaServerApplication.class, args);
	}

}
