package ie.fieldublin.config.security;

import java.time.LocalDateTime;
import java.util.Set;

public record ErrorMessage(
        Integer statusCode,
        LocalDateTime timestamp,
        Set<String> message,
        String description) {
}
