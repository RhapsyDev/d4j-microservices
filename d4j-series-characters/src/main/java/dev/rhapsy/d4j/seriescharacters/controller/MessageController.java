package dev.rhapsy.d4j.seriescharacters.controller;

import dev.rhapsy.d4j.seriescharacters.service.TranslationService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/translations")
public class MessageController {

    @Autowired
    private TranslationService service;
    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    @GetMapping
    @Operation(summary = "Simple Controller For Testing Cache using Redis")
    public ResponseEntity<String> getTranslation(@RequestParam("message") String message) {
        log.info("Message received {} ", message);
        Optional<String> translation = service.getTranslation(message);
        if (translation.isPresent()) {
            return ResponseEntity.ok(translation.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    @Operation(summary = "Simple Controller for Clear the Cache stored in Redis")
    public void clearCache(@RequestParam("message") String message) {
        log.info("Cleaning cache for {}", message);
        service.clearCache(message);
    }
}