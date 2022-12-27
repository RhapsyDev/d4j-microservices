package dev.rhapsy.d4j.auth.controller;

import dev.rhapsy.d4j.auth.model.dto.TokenDto;
import dev.rhapsy.d4j.auth.model.dto.UserDto;
import dev.rhapsy.d4j.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(service.login(userDto));
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenDto> validate(@RequestParam String token){
        return ResponseEntity.ok(service.validate(token));
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto){
        return ResponseEntity.ok(service.save(userDto));
    }
}
