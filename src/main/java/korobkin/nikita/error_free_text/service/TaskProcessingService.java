package korobkin.nikita.error_free_text.service;

import korobkin.nikita.error_free_text.entity.Task;

public interface TaskProcessingService {

    void processPendingTasks();

    void processTask(Task task);
}
