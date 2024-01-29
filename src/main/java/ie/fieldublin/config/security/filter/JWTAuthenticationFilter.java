package ie.fieldublin.config.security.filter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.fieldublin.config.security.MyUserDetails;
import ie.fieldublin.config.security.constants.SecurityConstants;
import ie.fieldublin.config.security.dto.UserCredentials;
import ie.fieldublin.config.security.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import java.io.IOException;
import java.util.Collections;


@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.setFilterProcessesUrl(SecurityConstants.LOGIN_URL);
    }

    @Override
    public final Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        if (!req.getMethod().equals(HttpMethod.POST.name())) {
            throw new MethodNotAllowedException(req.getMethod(), Collections.singleton(HttpMethod.POST));
        }

        if (!req.getHeader(HttpHeaders.CONTENT_TYPE).contains(MediaType.APPLICATION_JSON_VALUE)) {
            throw new UnsupportedMediaTypeStatusException("Not supported media type");
        }

        try {
            UserCredentials credentials = new ObjectMapper().readValue(req.getInputStream(), UserCredentials.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password()));
        } catch (JsonMappingException | JsonParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        log.info("unsuccessfulAuthentication");
        throw failed;
    }

    @Override
    protected final void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {
        log.info("successfulAuthentication");

        MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();

        String jwtToken = jwtService.generateToken(userDetails.getUsername());

        res.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION);
        res.addHeader(HttpHeaders.AUTHORIZATION, jwtToken);
    }
}
