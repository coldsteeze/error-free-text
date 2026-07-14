package korobkin.nikita.error_free_text.unit;

import korobkin.nikita.error_free_text.dto.CreateTaskRequest;
import korobkin.nikita.error_free_text.dto.CreateTaskResponse;
import korobkin.nikita.error_free_text.dto.TaskResponse;
import korobkin.nikita.error_free_text.entity.Task;
import korobkin.nikita.error_free_text.exception.TaskNotFoundException;
import korobkin.nikita.error_free_text.repository.TaskRepository;
import korobkin.nikita.error_free_text.service.impl.TaskServiceImpl;
import korobkin.nikita.error_free_text.unit.fixtures.TaskFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private CreateTaskRequest createTaskRequest;

    @BeforeEach
    void setUp() {
        task = TaskFixtures.task();
        createTaskRequest = TaskFixtures.createTaskRequest();
    }

    @Test
    void createTask_shouldSavesAndReturnId() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        CreateTaskResponse result = taskService.createTask(createTaskRequest);

        assertNotNull(result);
        verify(taskRepository).save(any(Task.class));
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void getTask_shouldReturnResponse() {
        when(taskRepository.findById(any(UUID.class))).thenReturn(Optional.of(task));

        TaskResponse result = taskService.getTask(UUID.randomUUID());

        assertEquals(result.status(), task.getStatus());
        assertEquals(result.correctedText(), task.getCorrectedText());
        assertEquals(result.errorMessage(), task.getErrorMessage());
        verify(taskRepository).findById(any(UUID.class));
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void getTask_shouldThrowIfNotFound() {
        when(taskRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> taskService.getTask(UUID.randomUUID()));
    }
}

