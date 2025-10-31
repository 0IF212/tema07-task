package br.ifsp.task.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private String prioridade; // ALTA, MEDIA, BAIXA
    private LocalDate prazo;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    // ---------- Getters e Setters ----------
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
