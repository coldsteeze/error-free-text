package korobkin.nikita.error_free_text.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class TaskNotFoundException extends AppException {

    public TaskNotFoundException(UUID id) {
        super(
                HttpStatus.NOT_FOUND,
                ErrorCode.TASK_NOT_FOUND,
                "Task with id: " + id + " not found"
        );
    }
}