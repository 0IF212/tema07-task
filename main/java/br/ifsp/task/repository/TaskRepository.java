package br.ifsp.task.repository;

import br.ifsp.task.model.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectId(Long projectId);
}
