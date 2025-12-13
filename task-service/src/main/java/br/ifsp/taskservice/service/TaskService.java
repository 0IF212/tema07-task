package br.ifsp.taskservice.service;

import br.ifsp.taskservice.client.ProjectServiceClient;
import br.ifsp.taskservice.client.UserServiceClient;
import br.ifsp.taskservice.domain.TaskDomain;
import br.ifsp.taskservice.dto.TaskRequestDTO;
import br.ifsp.taskservice.dto.TaskResponseDTO;
import br.ifsp.taskservice.exception.ResourceNotFoundException;
import br.ifsp.taskservice.persistence.mapper.TaskMapper;
import br.ifsp.taskservice.persistence.repository.TaskRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ProjectServiceClient projectClient;
    private final UserServiceClient userClient;

    public TaskService(
        TaskRepository taskRepository,
        TaskMapper taskMapper,
        ProjectServiceClient projectClient,
        UserServiceClient userClient
    ) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.projectClient = projectClient;
        this.userClient = userClient;
    }

    public List<TaskResponseDTO> listar(Long userId) {
        return taskRepository
            .findByOwnerId(userId)
            .stream()
            .map(taskMapper::toDomain)
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public TaskResponseDTO criar(
        TaskRequestDTO dto,
        Long userId,
        String token
    ) {
        userClient.validateUser(userId, token);

        if (dto.getProjectId() != null) {
            projectClient.validateProject(dto.getProjectId(), userId, token);
        }

        TaskDomain domain = new TaskDomain();
        domain.setTitle(dto.getTitle());
        domain.setDescription(dto.getDescription());
        domain.setPriority(dto.getPriority());
        domain.setDue(dto.getDue());
        domain.setProjectId(dto.getProjectId());
        domain.setOwnerId(userId);
        domain.validate();

        var entity = taskMapper.toEntity(domain);
        var saved = taskRepository.save(entity);

        return toResponseDTO(taskMapper.toDomain(saved));
    }

    @Transactional
    public TaskResponseDTO atualizar(
        Long id,
        TaskRequestDTO dto,
        Long userId,
        String token
    ) {
        var entity = taskRepository
            .findByIdAndOwnerId(id, userId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Tarefa não encontrada")
            );

        if (dto.getProjectId() != null) {
            projectClient.validateProject(dto.getProjectId(), userId, token);
        }

        TaskDomain domain = taskMapper.toDomain(entity);
        domain.setTitle(dto.getTitle());
        domain.setDescription(dto.getDescription());
        domain.setPriority(dto.getPriority());
        domain.setDue(dto.getDue());
        domain.setProjectId(dto.getProjectId());
        domain.validate();

        entity = taskMapper.toEntity(domain);
        entity.setId(id); // Mantém o ID
        var saved = taskRepository.save(entity);

        return toResponseDTO(taskMapper.toDomain(saved));
    }

    @Transactional
    public void excluir(Long id, Long userId) {
        var entity = taskRepository
            .findByIdAndOwnerId(id, userId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Tarefa não encontrada")
            );
        taskRepository.delete(entity);
    }

    public TaskResponseDTO buscarPorId(Long id, Long userId) {
        var entity = taskRepository
            .findByIdAndOwnerId(id, userId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Tarefa não encontrada")
            );
        return toResponseDTO(taskMapper.toDomain(entity));
    }

    private TaskResponseDTO toResponseDTO(TaskDomain domain) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(domain.getId());
        dto.setTitle(domain.getTitle());
        dto.setDescription(domain.getDescription());
        dto.setPriority(domain.getPriority());
        dto.setDue(domain.getDue());
        dto.setProjectId(domain.getProjectId());
        dto.setOwnerId(domain.getOwnerId());
        return dto;
    }
}
