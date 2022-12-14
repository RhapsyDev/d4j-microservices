package dev.rhapsy.d4j.gamecharacters.controller;

import com.github.javafaker.Faker;
import dev.rhapsy.d4j.gamecharacters.service.FooService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/lol")
public class CharacterController {

    private final Faker faker = new Faker();
    private final List<String> randomCharacters = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(CharacterController.class);

    private final ServerProperties serverProperties;
    private final FooService fooService;

    public CharacterController(ServerProperties serverProperties, FooService fooService) {
        this.serverProperties = serverProperties;
        this.fooService = fooService;
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            randomCharacters.add(faker.leagueOfLegends().champion());
        }
    }

    @GetMapping()
    public ResponseEntity<String> index() {
        log.info("index() called...");
        return ResponseEntity.ok(
                String.format("Running Instance on port: %s", serverProperties.getPort()));
    }

    @GetMapping("/characters")
    public ResponseEntity<?> getRandomCharacters() {
        log.info("Getting League of Legends Random Characters...");
        fooService.printLog(); // for testing sleuth-zipkin
        return ResponseEntity.ok(Map.of("LeagueOfLegends", randomCharacters));
    }
}
