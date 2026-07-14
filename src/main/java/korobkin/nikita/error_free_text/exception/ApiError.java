package korobkin.nikita.error_free_text.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ApiError(

        String errorMessage,
        int errorCode,
        LocalDateTime timestamp,
        String path
) {
}
