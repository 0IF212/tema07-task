package br.ifsp.task.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private String cor;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> tarefas;

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

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public List<Task> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Task> tarefas) {
        this.tarefas = tarefas;
    }
}
