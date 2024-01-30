package ie.fieldublin.security;

import ie.fieldublin.security.filter.ExceptionHandlerFilter;
import ie.fieldublin.security.jwt.JWTAuthenticationFilter;
import ie.fieldublin.security.jwt.JWTAuthorizationFilter;
import ie.fieldublin.security.jwt.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final List<String> URLS_WHITE_LIST = List.of("http://localhost:4200");

    private final JWTService jwtService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/swagger-ui.html/**",
                                "/swagger-ui/**",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/auth/**")
                        .permitAll()
                        //.requestMatchers("/api/user/**").hasAnyAuthority("ADMIN")
                        .anyRequest().authenticated())

                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())

                .httpBasic(withDefaults())

                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                //When filters below are commented, AuthController.login() is used instead
                .addFilterBefore(exceptionHandlerFilter(), JWTAuthenticationFilter.class)
                .addFilter(jwtAuthenticationFilter())
                .addFilter(jwtAuthorizationFilter())

                .logout(l -> l.clearAuthentication(true));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(authProvider);
    }

    private ExceptionHandlerFilter exceptionHandlerFilter() {
        return new ExceptionHandlerFilter();
    }

    private JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter(authenticationManager(), jwtService);
    }

    private JWTAuthorizationFilter jwtAuthorizationFilter() {
        return new JWTAuthorizationFilter(authenticationManager(), jwtService, userDetailsService());
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(URLS_WHITE_LIST);
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS", "HEAD"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
