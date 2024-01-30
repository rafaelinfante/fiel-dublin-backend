package ie.fieldublin.common.exception;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record ErrorMessage(
        Integer statusCode,
        LocalDateTime timestamp,
        Set<String> message,
        String description) {
}
