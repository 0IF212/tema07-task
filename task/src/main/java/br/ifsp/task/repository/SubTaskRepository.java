package br.ifsp.task.repository;

import br.ifsp.task.model.SubTask;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubTaskRepository extends JpaRepository<SubTask, Long> {
    List<SubTask> findByTaskIdAndOwnerId(Long taskId, Long userId);
    List<SubTask> findByOwnerId(Long ownerId);
    Optional<SubTask> findByIdAndOwnerId(Long subTaskId, Long userId);
}
