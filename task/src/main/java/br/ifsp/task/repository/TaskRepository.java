package br.ifsp.task.repository;

import br.ifsp.task.model.Task;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByOwnerId(Long userId);

    Optional<Task> findByIdAndOwnerId(Long taskId, Long userId);

    List<Task> findByProjectIdAndOwnerId(Long projectId, Long userId);
}
