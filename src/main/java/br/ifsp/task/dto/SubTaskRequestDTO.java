package br.ifsp.task.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class SubTaskRequestDTO {

    @NotBlank(message = "O título da subtarefa é obrigatório.")
    @Size(
        max = 100,
        message = "O título da subtarefa deve ter no máximo 100 caracteres."
    )
    private String title;

    @Size(
        max = 255,
        message = "A descrição da subtarefa deve ter no máximo 255 caracteres."
    )
    private String description;

    @FutureOrPresent(
        message = "A data de prazo deve ser hoje ou uma data futura."
    )
    private LocalDate dueDate;

    public SubTaskRequestDTO() {}

    public SubTaskRequestDTO(
        String title,
        String description,
        LocalDate dueDate
    ) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
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
}
