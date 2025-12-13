package br.ifsp.task.service;

import br.ifsp.task.dto.TaskDTO;
import br.ifsp.task.exception.ResourceNotFoundException;
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
    private final UserService userService;

    public TaskService(
        TaskRepository taskRepository,
        ProjectRepository projectRepository,
        UserService userService
    ) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    public List<Task> listar(Long userId) {
        return taskRepository.findByOwnerId(userId);
    }

    public Task criar(TaskDTO dto, Long userId) {
        Task t = new Task();
        t.setTitle(dto.getTitle());
        t.setDescription(dto.getDescription());
        t.setPriority(dto.getPriority());
        t.setDue(dto.getDue());
        t.setOwner(userService.findById(userId));

        if (dto.getProjectId() != null) {
            Project p = projectRepository
                .findByIdAndOwnerId(dto.getProjectId(), userId)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Projeto não encontrado")
                );
            t.setProject(p);
        }

        return taskRepository.save(t);
    }

    public Task atualizar(Long id, TaskDTO dto, Long userId) {
        Task t = taskRepository
            .findByIdAndOwnerId(id, userId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Tarefa não encontrado")
            );

        t.setTitle(dto.getTitle());
        t.setDescription(dto.getDescription());
        t.setPriority(dto.getPriority());
        t.setDue(dto.getDue());

        if (dto.getProjectId() != null) {
            Project p = projectRepository
                .findByIdAndOwnerId(dto.getProjectId(), userId)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Projeto não encontrado")
                );
            t.setProject(p);
        }

        return taskRepository.save(t);
    }

    public void excluir(Long id, Long userId) {
        Task t = taskRepository
            .findByIdAndOwnerId(id, userId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Tarefa não encontrada")
            );
        taskRepository.delete(t);
    }

    public Task buscarPorId(Long id, Long userId) {
        return taskRepository
            .findByIdAndOwnerId(id, userId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Tarefa não encontrada")
            );
    }

    public List<Task> listarPorProjeto(Long projectId, Long userId) {
        return taskRepository.findByProjectIdAndOwnerId(projectId, userId);
    }
}
