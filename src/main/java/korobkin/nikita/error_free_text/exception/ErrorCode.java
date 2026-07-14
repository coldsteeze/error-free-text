package korobkin.nikita.error_free_text.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    TASK_NOT_FOUND(40401),
    VALIDATION_ERROR(40001);

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }

}
