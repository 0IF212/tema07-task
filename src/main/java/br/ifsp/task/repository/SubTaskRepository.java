package br.ifsp.task.repository;

import br.ifsp.task.model.SubTask;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubTaskRepository extends JpaRepository<SubTask, Long> {
    List<SubTask> findByTaskId(Long taskId);
}
