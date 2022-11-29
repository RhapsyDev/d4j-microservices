package dev.rhapsy.d4jgamecharactersfailover.controller;

import com.github.javafaker.Faker;
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
@RequestMapping("api/v1/lol-failover")
public class CharacterController {

    private final Faker faker = new Faker();
    private final List<String> randomCharacters = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(CharacterController.class);

    private final ServerProperties serverProperties;

    public CharacterController(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            randomCharacters.add("Fail-over -> " + faker.leagueOfLegends().champion());
        }
    }

    @GetMapping()
    public ResponseEntity<String> index() {
        log.info("index() called...");
        return ResponseEntity.ok(
                String.format("Fail-over -> Running Instance on port: %s", serverProperties.getPort()));
    }

    @GetMapping("/characters")
    public ResponseEntity<?> getRandomCharacters() {
        log.info("Fail-over -> Getting League of Legends Random Characters...");
        return ResponseEntity.ok(Map.of("LeagueOfLegends", randomCharacters));
    }
}
