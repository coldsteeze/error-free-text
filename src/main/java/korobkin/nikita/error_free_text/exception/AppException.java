package korobkin.nikita.error_free_text.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {

    private final HttpStatus status;
    private final ErrorCode errorCode;

    public AppException(HttpStatus status,
                        ErrorCode errorCode,
                        String message) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }
}
