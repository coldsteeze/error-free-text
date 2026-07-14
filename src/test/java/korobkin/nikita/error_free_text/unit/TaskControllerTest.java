package korobkin.nikita.error_free_text.unit;

import korobkin.nikita.error_free_text.controller.TaskController;
import korobkin.nikita.error_free_text.dto.CreateTaskRequest;
import korobkin.nikita.error_free_text.entity.enums.TaskLanguage;
import korobkin.nikita.error_free_text.entity.enums.TaskStatus;
import korobkin.nikita.error_free_text.exception.ErrorCode;
import korobkin.nikita.error_free_text.exception.TaskNotFoundException;
import korobkin.nikita.error_free_text.service.TaskService;
import korobkin.nikita.error_free_text.unit.fixtures.TaskFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TaskService taskService;

    @Test
    void createTask_shouldReturnCreatedTaskId() throws Exception {
        given(taskService.createTask(any(CreateTaskRequest.class)))
                .willReturn(TaskFixtures.createTaskResponse());

        mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(TaskFixtures.createTaskRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

        verify(taskService).createTask(any(CreateTaskRequest.class));
    }

    @Test
    void createTask_withEmptyText_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(TaskFixtures.createTaskRequest("", TaskLanguage.EN))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.VALIDATION_ERROR.getCode()));
    }

    @Test
    void createTask_withUnacceptableCharacters_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(TaskFixtures.createTaskRequest("ab", TaskLanguage.EN))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.VALIDATION_ERROR.getCode()));
    }

    @Test
    void createTask_withOnlyNumberText_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(TaskFixtures.createTaskRequest("12345", TaskLanguage.EN))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.VALIDATION_ERROR.getCode()));
    }

    @Test
    void createTask_withLackLanguage_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(TaskFixtures.createTaskRequest("abcd", null))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.VALIDATION_ERROR.getCode()));
    }

    @Test
    void getTask_shouldReturnResponse() throws Exception {
        given(taskService.getTask(any(UUID.class))).willReturn(TaskFixtures.taskResponse());

        mockMvc.perform(get("/api/v1/tasks/" + UUID.randomUUID()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.status").value(TaskStatus.NEW.name()));
    }

    @Test
    void getTask_withInvalidId_shouldReturnNotFound() throws Exception {
        given(taskService.getTask(any(UUID.class))).willThrow(new TaskNotFoundException(UUID.randomUUID()));

        mockMvc.perform(get("/api/v1/tasks/" + UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.TASK_NOT_FOUND.getCode()));
    }

    private String json(Object o) {
        return objectMapper.writeValueAsString(o);
    }
}
