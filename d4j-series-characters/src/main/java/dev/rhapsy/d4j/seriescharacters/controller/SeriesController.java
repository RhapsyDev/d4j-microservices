package dev.rhapsy.d4j.seriescharacters.controller;

import com.github.javafaker.Faker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("api/v1/got")
public class SeriesController {

    private final Faker faker = new Faker();
    private final List<String> randomCharacters = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(SeriesController.class);

    private final ServerProperties serverProperties;

    public SeriesController(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            randomCharacters.add(faker.gameOfThrones().character());
        }
    }

    @GetMapping()
    public ResponseEntity<String> index() {
        log.info("index() called...");
        return ResponseEntity.ok(
                String.format("Running Instance on port: %s", serverProperties.getPort()));
    }

    @GetMapping("/characters")
    @Operation(summary = "Get a list of characters from GoT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the characters",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))}),
            @ApiResponse(responseCode = "404", description = "Characters not found",
                    content = @Content)})
    public ResponseEntity<?> getRandomCharacters() {
        log.info("Getting Game of Thrones Random Characters...");
        return ResponseEntity.ok(Map.of("GameOfThrones", randomCharacters));
    }
}
