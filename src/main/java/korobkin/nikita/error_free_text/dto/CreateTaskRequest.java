package korobkin.nikita.error_free_text.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import korobkin.nikita.error_free_text.entity.enums.TaskLanguage;
import korobkin.nikita.error_free_text.validation.annotation.ValidText;

public record CreateTaskRequest(

        @NotBlank(message = "Text is required")
        @Size(min = 3, message = "Text must be at least 3 characters long")
        @ValidText
        String text,

        @NotNull(message = "Language is required")
        TaskLanguage language
) {
}
