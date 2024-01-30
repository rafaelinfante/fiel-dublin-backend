package ie.fieldublin.domain.auth.service;

import ie.fieldublin.security.MyUserDetails;
import ie.fieldublin.security.dto.UserCredentials;
import ie.fieldublin.security.jwt.JWTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    public String login(UserCredentials userCredentials) {
        MyUserDetails userDetails;
        try {
            userDetails = (MyUserDetails) authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredentials.username(), userCredentials.password()));
        } catch (Exception ex) {
            log.error("login::failure", ex);
            throw ex;
        }

        log.info("login::success");

        return jwtService.generateToken(userDetails);
    }

}
