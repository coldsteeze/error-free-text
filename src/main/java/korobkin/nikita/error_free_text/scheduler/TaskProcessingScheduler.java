package korobkin.nikita.error_free_text.scheduler;

import korobkin.nikita.error_free_text.service.TaskProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskProcessingScheduler {

    private final TaskProcessingService taskProcessingService;

    @Scheduled(fixedDelayString = "${scheduler.task-processing.fixed-delay}")
    public void processNewTasks() {
        taskProcessingService.processPendingTasks();
    }
}
