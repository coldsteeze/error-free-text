package korobkin.nikita.error_free_text.service;

import korobkin.nikita.error_free_text.dto.CreateTaskRequest;
import korobkin.nikita.error_free_text.dto.CreateTaskResponse;
import korobkin.nikita.error_free_text.dto.TaskResponse;

import java.util.UUID;

public interface TaskService {

    CreateTaskResponse createTask(CreateTaskRequest request);

    TaskResponse getTask(UUID id);
}
