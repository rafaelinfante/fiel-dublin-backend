package ie.fieldublin.security.jwt;

import ie.fieldublin.security.MyUserDetails;
import ie.fieldublin.security.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
//@Setter
//@ConfigurationProperties(prefix = "jwt")
public class JWTService {

    /*  private String secret;
        private long expiration;
        @Getter
        private String prefix;
    */
    public String generateToken(MyUserDetails userDetails) {
        Claims claims = Jwts.claims()
                .subject(userDetails.getUsername())
                .expiration(new Date(System.currentTimeMillis() + SecurityConstants.JWT_EXPIRATION_TIME))
                .issuedAt(new Date())
                .add("roles", userDetails.getRoles())
                .build();

        return Jwts.builder()
                .claims(claims)
                .signWith(getKey())
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecurityConstants.JWT_SECRET));
    }

}
