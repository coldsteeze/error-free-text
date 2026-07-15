package korobkin.nikita.error_free_text.repository;

import korobkin.nikita.error_free_text.entity.Task;
import korobkin.nikita.error_free_text.entity.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findAllByStatus(TaskStatus status);
}
