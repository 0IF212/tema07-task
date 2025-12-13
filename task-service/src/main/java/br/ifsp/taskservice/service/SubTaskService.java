package br.ifsp.taskservice.service;

import br.ifsp.taskservice.domain.SubTaskDomain;
import br.ifsp.taskservice.dto.SubTaskRequestDTO;
import br.ifsp.taskservice.dto.SubTaskResponseDTO;
import br.ifsp.taskservice.exception.ResourceNotFoundException;
import br.ifsp.taskservice.persistence.mapper.TaskMapper;
import br.ifsp.taskservice.persistence.repository.SubTaskRepository;
import br.ifsp.taskservice.persistence.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubTaskService {
    private final SubTaskRepository subTaskRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public SubTaskService(
        SubTaskRepository subTaskRepository,
        TaskRepository taskRepository,
        TaskMapper taskMapper
    ) {
        this.subTaskRepository = subTaskRepository;
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public List<SubTaskResponseDTO> getSubTasksByTask(Long taskId, Long userId) {
        // Valida que a task pertence ao usuário
        taskRepository.findByIdAndOwnerId(taskId, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada"));

        return subTaskRepository.findByTaskIdAndOwnerId(taskId, userId).stream()
            .map(taskMapper::subTaskToDomain)
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public SubTaskResponseDTO createSubTask(Long taskId, SubTaskRequestDTO dto, Long userId) {
        // Valida que a task pertence ao usuário
        var task = taskRepository.findByIdAndOwnerId(taskId, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada"));

        SubTaskDomain domain = new SubTaskDomain();
        domain.setTitle(dto.getTitle());
        domain.setDescription(dto.getDescription());
        domain.setDueDate(dto.getDueDate());
        domain.setTaskId(taskId);
        domain.setOwnerId(userId);
        domain.validate();

        var entity = taskMapper.subTaskToEntity(domain);
        var saved = subTaskRepository.save(entity);
        
        return toResponseDTO(taskMapper.subTaskToDomain(saved));
    }

    @Transactional
    public SubTaskResponseDTO updateSubTask(Long subTaskId, SubTaskRequestDTO dto, Long userId) {
        var entity = subTaskRepository.findByIdAndOwnerId(subTaskId, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Subtarefa não encontrada"));

        SubTaskDomain domain = taskMapper.subTaskToDomain(entity);
        domain.setTitle(dto.getTitle());
        domain.setDescription(dto.getDescription());
        domain.setDueDate(dto.getDueDate());
        domain.validate();

        entity = taskMapper.subTaskToEntity(domain);
        entity.setId(subTaskId);
        var saved = subTaskRepository.save(entity);
        
        return toResponseDTO(taskMapper.subTaskToDomain(saved));
    }

    @Transactional
    public void deleteSubTask(Long subTaskId, Long userId) {
        var entity = subTaskRepository.findByIdAndOwnerId(subTaskId, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Subtarefa não encontrada"));
        subTaskRepository.delete(entity);
    }

    private SubTaskResponseDTO toResponseDTO(SubTaskDomain domain) {
        SubTaskResponseDTO dto = new SubTaskResponseDTO();
        dto.setId(domain.getId());
        dto.setTitle(domain.getTitle());
        dto.setDescription(domain.getDescription());
        dto.setDueDate(domain.getDueDate());
        dto.setTaskId(domain.getTaskId());
        dto.setOwnerId(domain.getOwnerId());
        return dto;
    }
}

