package ie.fieldublin.config.security.filter;

import ie.fieldublin.config.security.MyUserDetails;
import ie.fieldublin.config.security.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

@Log4j2
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authManager, JWTService jwtService, UserDetailsService userDetailsService) {
        super(authManager);
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected final void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String jwtToken = req.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwtToken == null || !jwtToken.startsWith(jwtService.getPrefix())) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(jwtToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String jwtToken) {
        try {

            String username = jwtService.extractUsername(jwtToken);

            MyUserDetails myUserDetails = (MyUserDetails) userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(myUserDetails, null, myUserDetails.getAuthorities());

        } catch (Exception e) {
            //throw new AuthenticateException(e.getMessage());
            throw e;
        }
    }
}
