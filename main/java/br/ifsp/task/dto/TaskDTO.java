package br.ifsp.task.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class TaskDTO {

    private Long id;

    @NotBlank(message = "O título da tarefa é obrigatório.")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres.")
    private String titulo;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
    private String descricao;

    @NotBlank(message = "A prioridade é obrigatória (ex: BAIXA, MEDIA, ALTA).")
    private String prioridade;

    @NotNull(message = "A data de prazo é obrigatória.")
    @FutureOrPresent(message = "A data de prazo não pode estar no passado.")
    private LocalDate prazo;

    private Long projectId;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDate getPrazo() {
        return prazo;
    }

    public void setPrazo(LocalDate prazo) {
        this.prazo = prazo;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
