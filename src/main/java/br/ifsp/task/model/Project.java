package br.ifsp.task.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String color;
    private LocalDate dateStart;
    private LocalDate dateEnd;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> tarefas;

    // ---------- Getters e Setters ----------
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

    public void setDescription(String descricao) {
        this.description = descricao;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String cor) {
        this.color = cor;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dataInicio) {
        this.dateStart = dataInicio;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dataFim) {
        this.dateEnd = dataFim;
    }

    public List<Task> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Task> tarefas) {
        this.tarefas = tarefas;
    }
}
