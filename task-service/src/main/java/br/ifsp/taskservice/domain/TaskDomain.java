package br.ifsp.taskservice.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskDomain {
    private Long id;
    private String title;
    private String description;
    private String priority;
    private LocalDate due;
    private Long projectId;
    private Long ownerId;
    private Set<Long> tagIds = new HashSet<>();
    private List<SubTaskDomain> subtasks = new ArrayList<>();

    public TaskDomain() {}

    public TaskDomain(Long id, String title, String description, String priority, 
                     LocalDate due, Long projectId, Long ownerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.due = due;
        this.projectId = projectId;
        this.ownerId = ownerId;
    }

    public void validate() {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (priority == null || priority.isBlank()) {
            throw new IllegalArgumentException("Priority is required");
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

    public Set<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(Set<Long> tagIds) {
        this.tagIds = tagIds;
    }

    public List<SubTaskDomain> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<SubTaskDomain> subtasks) {
        this.subtasks = subtasks;
    }
}

