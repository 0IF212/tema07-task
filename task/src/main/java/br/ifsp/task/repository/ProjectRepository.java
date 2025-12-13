package br.ifsp.task.repository;

import br.ifsp.task.model.Project;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwnerId(Long userId);

    Optional<Project> findByIdAndOwnerId(Long projectId, Long userId);
}
