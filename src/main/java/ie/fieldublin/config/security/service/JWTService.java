package ie.fieldublin.config.security.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
@Setter
@ConfigurationProperties(prefix = "jwt")
public class JWTService {

    private String secret;
    private long expiration;
    @Getter
    private String prefix;

    public String generateToken(String username) {
        return prefix + Jwts.builder()
                .claims()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .issuedAt(new Date())
                .and()
                .signWith(getKey())
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token.replace(prefix, ""))
                .getPayload()
                .getSubject();
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

}
