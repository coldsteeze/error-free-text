package korobkin.nikita.error_free_text.dto;

import korobkin.nikita.error_free_text.entity.enums.TaskStatus;

import java.util.UUID;

public record TaskResponse(

        UUID id,
        TaskStatus status,
        String correctedText,
        String errorMessage
) {
}
