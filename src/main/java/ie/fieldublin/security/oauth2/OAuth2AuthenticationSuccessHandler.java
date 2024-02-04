package ie.fieldublin.security.oauth2;

import ie.fieldublin.security.constants.SecurityConstants;
import ie.fieldublin.security.jwt.JWTService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTService jwtService;

    public OAuth2AuthenticationSuccessHandler(JWTService jwtService) {
        this.jwtService = jwtService;
        super.setUseReferer(true);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        System.out.println("OAuth2AuthenticationFailureHandler.onAuthenticationFailure");

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        OAuth2User oauth2User = oauthToken.getPrincipal();

        String jwtToken = jwtService.generateToken(oauth2User.getAttribute("email"));

        String callbackRedirect = UriComponentsBuilder.fromUriString("http://localhost:4200/auth/callback")
                .queryParam("jwt-token", SecurityConstants.JWT_PREFIX + jwtToken)
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, callbackRedirect);

    }

}
