package br.ifsp.task.service;

import br.ifsp.task.dto.ProjectDTO;
import br.ifsp.task.exception.ResourceNotFoundException;
import br.ifsp.task.model.Project;
import br.ifsp.task.repository.ProjectRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;

    public ProjectService(
        ProjectRepository projectRepository,
        UserService userService
    ) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    public List<Project> listar(Long userId) {
        return projectRepository.findByOwnerId(userId);
    }

    public Project criar(ProjectDTO dto, Long userId) {
        Project p = new Project();
        p.setTitle(dto.getTitle());
        p.setDescription(dto.getDescricao());
        p.setColor(dto.getCor());
        p.setDateStart(dto.getDataInicio());
        p.setDateEnd(dto.getDataFim());
        p.setOwner(userService.findById(userId));

        return projectRepository.save(p);
    }

    public Project atualizar(Long id, ProjectDTO dto, Long userId) {
        Project p = projectRepository
            .findByIdAndOwnerId(id, userId)
            .orElseThrow();

        p.setTitle(dto.getTitle());
        p.setDescription(dto.getDescricao());
        p.setColor(dto.getCor());
        p.setDateStart(dto.getDataInicio());
        p.setDateEnd(dto.getDataFim());

        return projectRepository.save(p);
    }

    public void excluir(Long id, Long userId) {
        Project p = projectRepository
            .findByIdAndOwnerId(id, userId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Projeto não encontrado")
            );
        projectRepository.delete(p);
    }

    public Project buscarPorId(Long id, Long userId) {
        return projectRepository
            .findByIdAndOwnerId(id, userId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Projeto não encontrado")
            );
    }
}
