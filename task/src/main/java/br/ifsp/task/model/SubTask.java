package br.ifsp.task.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "subtasks")
public class SubTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDate dueDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "task_id")
    @JsonBackReference
    private Task task;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserAccount owner;

    public SubTask() {}

    public SubTask(
        String title,
        String description,
        LocalDate dueDate,
        Task task,
        UserAccount owner
    ) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.task = task;
        this.owner = owner;
    }

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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public UserAccount getOwner() {
        return owner;
    }

    public void setOwner(UserAccount owner) {
        this.owner = owner;
    }
}
