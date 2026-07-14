package korobkin.nikita.error_free_text.unit.fixtures;

import korobkin.nikita.error_free_text.dto.CreateTaskRequest;
import korobkin.nikita.error_free_text.dto.CreateTaskResponse;
import korobkin.nikita.error_free_text.dto.TaskResponse;
import korobkin.nikita.error_free_text.entity.Task;
import korobkin.nikita.error_free_text.entity.enums.TaskLanguage;
import korobkin.nikita.error_free_text.entity.enums.TaskStatus;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class TaskFixtures {

    public static CreateTaskRequest createTaskRequest() {
        return new CreateTaskRequest("Test text", TaskLanguage.EN);
    }

    public static CreateTaskRequest createTaskRequest(String text, TaskLanguage language) {
        return new CreateTaskRequest(text, language);
    }

    public static CreateTaskResponse createTaskResponse() {
        return new CreateTaskResponse(UUID.randomUUID());
    }

    public static TaskResponse taskResponse() {
        return new TaskResponse(UUID.randomUUID(), TaskStatus.NEW, null, null);
    }

    public static Task task() {
        return Task.builder()
                .text("Test text")
                .language(TaskLanguage.EN)
                .status(TaskStatus.NEW)
                .build();
    }
}
