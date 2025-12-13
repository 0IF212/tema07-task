package br.ifsp.taskservice.domain;

import java.time.LocalDate;

public class SubTaskDomain {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Long taskId;
    private Long ownerId;

    public SubTaskDomain() {}

    public SubTaskDomain(Long id, String title, String description, 
                        LocalDate dueDate, Long taskId, Long ownerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.taskId = taskId;
        this.ownerId = ownerId;
    }

    public void validate() {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (taskId == null) {
            throw new IllegalArgumentException("Task ID is required");
        }
        if (ownerId == null) {
            throw new IllegalArgumentException("Owner ID is required");
        }
    }

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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}

