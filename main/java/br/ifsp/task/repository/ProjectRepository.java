package br.ifsp.task.repository;

import br.ifsp.task.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {}
