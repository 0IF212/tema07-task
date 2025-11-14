package br.ifsp.task.service;

import br.ifsp.task.dto.ProjectDTO;
import br.ifsp.task.model.Project;
import br.ifsp.task.repository.ProjectRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> listar() {
        return projectRepository.findAll();
    }

    public Project criar(ProjectDTO dto) {
        Project p = new Project();
        p.setTitle(dto.getTitle());
        p.setDescription(dto.getDescricao());
        p.setColor(dto.getCor());
        p.setDateStart(dto.getDataInicio());
        p.setDateEnd(dto.getDataFim());
        return projectRepository.save(p);
    }

    public Project atualizar(Long id, ProjectDTO dto) {
        Project p = projectRepository.findById(id).orElseThrow();
        p.setTitle(dto.getTitle());
        p.setDescription(dto.getDescricao());
        p.setColor(dto.getCor());
        p.setDateStart(dto.getDataInicio());
        p.setDateEnd(dto.getDataFim());
        return projectRepository.save(p);
    }

    public void excluir(Long id) {
        projectRepository.deleteById(id);
    }

    public Project buscarPorId(Long id) {
        return projectRepository.findById(id).orElseThrow();
    }
}
