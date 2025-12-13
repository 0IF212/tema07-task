package br.ifsp.taskservice.persistence.mapper;

import br.ifsp.taskservice.domain.SubTaskDomain;
import br.ifsp.taskservice.domain.TaskDomain;
import br.ifsp.taskservice.persistence.entity.SubTaskEntity;
import br.ifsp.taskservice.persistence.entity.TaskEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TaskMapper {

    public TaskEntity toEntity(TaskDomain domain) {
        if (domain == null) return null;
        
        TaskEntity entity = new TaskEntity();
        entity.setId(domain.getId());
        entity.setTitle(domain.getTitle());
        entity.setDescription(domain.getDescription());
        entity.setPriority(domain.getPriority());
        entity.setDue(domain.getDue());
        entity.setProjectId(domain.getProjectId());
        entity.setOwnerId(domain.getOwnerId());
        
        if (domain.getSubtasks() != null) {
            entity.setSubtasks(domain.getSubtasks().stream()
                .map(this::subTaskToEntity)
                .collect(Collectors.toList()));
        }
        
        return entity;
    }

    public TaskDomain toDomain(TaskEntity entity) {
        if (entity == null) return null;
        
        TaskDomain domain = new TaskDomain();
        domain.setId(entity.getId());
        domain.setTitle(entity.getTitle());
        domain.setDescription(entity.getDescription());
        domain.setPriority(entity.getPriority());
        domain.setDue(entity.getDue());
        domain.setProjectId(entity.getProjectId());
        domain.setOwnerId(entity.getOwnerId());
        
        if (entity.getSubtasks() != null) {
            domain.setSubtasks(entity.getSubtasks().stream()
                .map(this::subTaskToDomain)
                .collect(Collectors.toList()));
        }
        
        return domain;
    }

    public SubTaskEntity subTaskToEntity(SubTaskDomain domain) {
        if (domain == null) return null;
        
        SubTaskEntity entity = new SubTaskEntity();
        entity.setId(domain.getId());
        entity.setTitle(domain.getTitle());
        entity.setDescription(domain.getDescription());
        entity.setDueDate(domain.getDueDate());
        entity.setTaskId(domain.getTaskId());
        entity.setOwnerId(domain.getOwnerId());
        
        return entity;
    }

    public SubTaskDomain subTaskToDomain(SubTaskEntity entity) {
        if (entity == null) return null;
        
        SubTaskDomain domain = new SubTaskDomain();
        domain.setId(entity.getId());
        domain.setTitle(entity.getTitle());
        domain.setDescription(entity.getDescription());
        domain.setDueDate(entity.getDueDate());
        domain.setTaskId(entity.getTaskId());
        domain.setOwnerId(entity.getOwnerId());
        
        return domain;
    }
}

