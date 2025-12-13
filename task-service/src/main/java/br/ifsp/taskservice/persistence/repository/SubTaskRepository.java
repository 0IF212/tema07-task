package br.ifsp.taskservice.persistence.repository;

import br.ifsp.taskservice.persistence.entity.SubTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubTaskRepository extends JpaRepository<SubTaskEntity, Long> {
    List<SubTaskEntity> findByTaskIdAndOwnerId(Long taskId, Long userId);
    List<SubTaskEntity> findByOwnerId(Long userId);
    Optional<SubTaskEntity> findByIdAndOwnerId(Long subTaskId, Long userId);
}

