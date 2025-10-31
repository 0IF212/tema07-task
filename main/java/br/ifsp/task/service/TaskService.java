package br.ifsp.task.service;

import br.ifsp.task.dto.TaskDTO;
import br.ifsp.task.model.Project;
import br.ifsp.task.model.Task;
import br.ifsp.task.repository.ProjectRepository;
import br.ifsp.task.repository.TaskRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(
        TaskRepository taskRepository,
        ProjectRepository projectRepository
    ) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public List<Task> listar() {
        return taskRepository.findAll();
    }

    public Task criar(TaskDTO dto) {
        Task t = new Task();
        t.setTitulo(dto.getTitulo());
        t.setDescricao(dto.getDescricao());
        t.setPrioridade(dto.getPrioridade());
        t.setPrazo(dto.getPrazo());

        if (dto.getProjectId() != null) {
            Project p = projectRepository
                .findById(dto.getProjectId())
                .orElse(null);
            t.setProject(p);
        }

        return taskRepository.save(t);
    }

    public Task atualizar(Long id, TaskDTO dto) {
        Task t = taskRepository.findById(id).orElseThrow();
        t.setTitulo(dto.getTitulo());
        t.setDescricao(dto.getDescricao());
        t.setPrioridade(dto.getPrioridade());
        t.setPrazo(dto.getPrazo());

        if (dto.getProjectId() != null) {
            Project p = projectRepository
                .findById(dto.getProjectId())
                .orElse(null);
            t.setProject(p);
        }

        return taskRepository.save(t);
    }

    public void excluir(Long id) {
        taskRepository.deleteById(id);
    }

    public Optional<Task> buscarPorId(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> listarPorProjeto(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }
}
