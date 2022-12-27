package dev.rhapsy.d4j.auth.service;

import dev.rhapsy.d4j.auth.config.JwtProvider;
import dev.rhapsy.d4j.auth.model.dto.TokenDto;
import dev.rhapsy.d4j.auth.model.dto.UserDto;
import dev.rhapsy.d4j.auth.model.entity.UserEntity;
import dev.rhapsy.d4j.auth.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final ModelMapper mapper;
    private final JwtProvider jwtProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder encoder, ModelMapper mapper, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.mapper = mapper;
        this.jwtProvider = jwtProvider;
    }

    public UserDto save(UserDto user) {
        Optional<UserEntity> response = userRepository.findByUsername(user.getUsername());
        if (response.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("UserEntity %s already exists", user.getUsername()));
        }
        UserEntity savedUser = userRepository.save(new UserEntity(user.getUsername(), encoder.encode(user.getPassword())));

        return mapper.map(savedUser, UserDto.class);
    }

    public TokenDto login(UserDto user) {
        UserEntity userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (encoder.matches(user.getPassword(), userEntity.getPassword())) {
            return new TokenDto(jwtProvider.createToken(userEntity));
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    public TokenDto validate(String token) {
        jwtProvider.validate(token);
        String username = jwtProvider.getUsernameFromToken(token);
        userRepository.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        return new TokenDto(token);
    }
}
