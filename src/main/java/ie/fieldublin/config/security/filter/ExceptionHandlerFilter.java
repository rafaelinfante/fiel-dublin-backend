package ie.fieldublin.config.security.filter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ie.fieldublin.config.security.ErrorMessage;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;

@Log4j2
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (UnsupportedMediaTypeStatusException e) {
            setErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, httpServletResponse, e, httpServletRequest.getRequestURI());
        } catch (MethodNotAllowedException e) {
            setErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, httpServletResponse, e, httpServletRequest.getRequestURI());
        }
    }

    private void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex, String path) {
        response.setStatus(status.value());
        response.setContentType("application/json");

        ErrorMessage apiError = new ErrorMessage(status.value(), LocalDateTime.now(), Set.of(ex.getMessage()), path);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            response.getWriter().write(mapper.writeValueAsString(apiError));
        } catch (IOException e) {
            log.warn("Exception has been caught", e);
        }
    }
}
