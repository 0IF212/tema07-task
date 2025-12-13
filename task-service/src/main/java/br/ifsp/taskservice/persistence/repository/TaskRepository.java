package br.ifsp.taskservice.persistence.repository;

import br.ifsp.taskservice.persistence.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByOwnerId(Long userId);
    Optional<TaskEntity> findByIdAndOwnerId(Long taskId, Long userId);
    List<TaskEntity> findByProjectIdAndOwnerId(Long projectId, Long userId);
}

