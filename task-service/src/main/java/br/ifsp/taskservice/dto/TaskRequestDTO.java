package br.ifsp.taskservice.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class TaskRequestDTO {
    @NotBlank(message = "O título da tarefa é obrigatório.")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres.")
    private String title;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
    private String description;

    @NotBlank(message = "A prioridade é obrigatória (ex: BAIXA, MEDIA, ALTA).")
    private String priority;

    @FutureOrPresent(message = "A data de prazo não pode estar no passado.")
    private LocalDate due;

    private Long projectId;

    // Getters and Setters
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
}

