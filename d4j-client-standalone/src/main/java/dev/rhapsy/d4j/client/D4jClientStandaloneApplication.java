package dev.rhapsy.d4j.client;

import dev.rhapsy.d4j.client.clients.GameCharacterClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;

@EnableFeignClients
@SpringBootApplication
public class D4jClientStandaloneApplication implements ApplicationRunner {

//    @Autowired
//    private EurekaClient eurekaClient;

    @Autowired
    private GameCharacterClient gameCharacterClient;

    private static final Logger log = LoggerFactory.getLogger(D4jClientStandaloneApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(D4jClientStandaloneApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        // Probando las peticiones varias veces para determinar el load balancer que instancia tomo
        for (int i = 0; i < 10; i++) {
            ResponseEntity<String> responseEntity = gameCharacterClient.getApplicationName();
            log.info("Status: {}", responseEntity.getStatusCode());
            log.info("Body: {}", responseEntity.getBody());
        }
    }

    /*
     * Implementation Cliente Eureka
     * */

//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//
//        Application application = eurekaClient.getApplication("d4j-game-characters");// microservice to fetch information
//
//        log.info("Application name {}", application.getName());
//        List<InstanceInfo> instances = application.getInstances();
//        for (InstanceInfo instanceInfo : instances) {
//            log.info("Ip address {}:{}", instanceInfo.getIPAddr(), instanceInfo.getPort());
//        }
//    }
}
