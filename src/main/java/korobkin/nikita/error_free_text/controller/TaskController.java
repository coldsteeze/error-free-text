package korobkin.nikita.error_free_text.controller;

import jakarta.validation.Valid;
import korobkin.nikita.error_free_text.dto.CreateTaskRequest;
import korobkin.nikita.error_free_text.dto.CreateTaskResponse;
import korobkin.nikita.error_free_text.dto.TaskResponse;
import korobkin.nikita.error_free_text.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<CreateTaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createTask(request));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable UUID taskId) {
        return ResponseEntity.ok(taskService.getTask(taskId));
    }
}
