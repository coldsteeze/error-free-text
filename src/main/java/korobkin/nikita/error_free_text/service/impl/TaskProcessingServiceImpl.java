package korobkin.nikita.error_free_text.service.impl;

import korobkin.nikita.error_free_text.entity.Task;
import korobkin.nikita.error_free_text.entity.enums.TaskStatus;
import korobkin.nikita.error_free_text.repository.TaskRepository;
import korobkin.nikita.error_free_text.service.TaskProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskProcessingServiceImpl implements TaskProcessingService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = true)
    public void processPendingTasks() {

        List<Task> pendingTasks = taskRepository.findAllByStatus(TaskStatus.NEW);

        if (pendingTasks.isEmpty()) {
            log.debug("No pending tasks found");
            return;
        }

        log.info("Found {} pending tasks for processing", pendingTasks.size());

        for (Task task : pendingTasks) {
            processTask(task);
        }
    }

    @Override
    @Transactional
    public void processTask(Task task) {

        log.info("Processing task with id: {}", task.getId());

        task.setStatus(TaskStatus.IN_PROGRESS);

        log.debug("Task {} status changed to {}", task.getId(), TaskStatus.IN_PROGRESS);

        taskRepository.save(task);
    }
}
