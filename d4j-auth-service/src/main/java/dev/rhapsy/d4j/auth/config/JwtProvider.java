package dev.rhapsy.d4j.auth.config;

import dev.rhapsy.d4j.auth.model.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Map;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    public String createToken(UserEntity userEntity) {
        Map<String, Object> claims = Jwts.claims().setSubject(userEntity.getUsername());
        claims.put("id", userEntity.getId());
        claims.put("email", "rhapsydev@gmail.com");
        Date now = new Date();
        Date exp = new Date(now.getTime() + 3600 * 1000); //1 year after
        return Jwts.builder()
                .setHeaderParam("company name", "RhapsyDev")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret) // how to sign my token
                .compact();
    }

    public void validate(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody().getSubject();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Token");

        }
    }
}
