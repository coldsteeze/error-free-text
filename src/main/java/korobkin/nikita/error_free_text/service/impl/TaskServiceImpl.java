package korobkin.nikita.error_free_text.service.impl;

import korobkin.nikita.error_free_text.dto.CreateTaskRequest;
import korobkin.nikita.error_free_text.dto.CreateTaskResponse;
import korobkin.nikita.error_free_text.dto.TaskResponse;
import korobkin.nikita.error_free_text.entity.Task;
import korobkin.nikita.error_free_text.entity.enums.TaskStatus;
import korobkin.nikita.error_free_text.exception.TaskNotFoundException;
import korobkin.nikita.error_free_text.repository.TaskRepository;
import korobkin.nikita.error_free_text.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public CreateTaskResponse createTask(CreateTaskRequest request) {

        Task task = Task.builder()
                .text(request.text())
                .language(request.language())
                .status(TaskStatus.NEW)
                .build();

        taskRepository.save(task);

        log.info("Created text correction task with id: {}, language: {}",
                task.getId(),
                task.getLanguage());

        return new CreateTaskResponse(task.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponse getTask(UUID id) {

        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        log.debug("Retrieved task with id: {}, status: {}", id, task.getStatus());

        return new TaskResponse(
                task.getId(),
                task.getStatus(),
                task.getCorrectedText(),
                task.getErrorMessage()
        );
    }
}
