package br.ifsp.taskservice.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class SubTaskRequestDTO {
    @NotBlank(message = "O título da subtarefa é obrigatório.")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres.")
    private String title;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
    private String description;

    private LocalDate dueDate;

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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}

