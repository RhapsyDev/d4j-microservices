package dev.rhapsy.d4j.cloudstream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootApplication
public class D4jCloudstreamApplication {

    private static final Logger log = LoggerFactory.getLogger(D4jCloudstreamApplication.class);


    @Bean
    public Function<String, String> toUpperCase() {
        // this function will act as a processor
        // transform the in String to Upper case and send it to toUpperCase-out-0 topic
        return String::toUpperCase;
    }

    /**
     *  producer-out-0
     */
//    @Bean //PRODUCER
    public Supplier<Flux<Long>> producer() {
        return () -> Flux.interval(Duration.ofSeconds(1)).log();
    }

    /**
     * processor-in-0
     * processor-out-0
     */
//    @Bean //FUNCTION
    public Function<Flux<Long>, Flux<Long>> processor() {
        return flx -> flx.map(number -> number * number);
    }

    /**
     * consumer-in-0
     */
//    @Bean //CONSUMER
    public Consumer<Long> consumer() {
        return (number) -> {
            log.info("Message received {}", number);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(D4jCloudstreamApplication.class, args);
    }

}
