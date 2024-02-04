package ie.fieldublin.security.jwt;

import ie.fieldublin.security.constants.SecurityConstants;
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
    public String generateToken(String username) {

        return Jwts.builder()
                .claims()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + SecurityConstants.JWT_EXPIRATION_TIME))
                .issuedAt(new Date())
                .and()
                .signWith(getKey())
                .compact();


                /* If role is needed in the token receive the list in the method (Set<Role> roles) and set as below
        Claims claims = Jwts.claims()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + SecurityConstants.JWT_EXPIRATION_TIME))
                .issuedAt(new Date())
                .add("roles", roles)
                .build();

        return Jwts.builder()
                .claims(claims)
                .signWith(getKey())
                .compact();
*/
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
