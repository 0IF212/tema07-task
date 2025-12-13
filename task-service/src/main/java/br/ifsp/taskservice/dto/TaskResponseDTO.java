package br.ifsp.taskservice.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String priority;
    private LocalDate due;
    private Long projectId;
    private Long ownerId;
    private List<SubTaskResponseDTO> subtasks = new ArrayList<>();

    public TaskResponseDTO() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getDue() {
        return due;
    }

    public void setDue(LocalDate due) {
        this.due = due;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public List<SubTaskResponseDTO> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<SubTaskResponseDTO> subtasks) {
        this.subtasks = subtasks;
    }
}

