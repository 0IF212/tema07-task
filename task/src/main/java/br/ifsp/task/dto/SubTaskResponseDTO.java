package br.ifsp.task.dto;

import br.ifsp.task.model.SubTask;
import java.time.LocalDate;

public class SubTaskResponseDTO {

    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Long taskId;
    private String taskTitle;

    public SubTaskResponseDTO(SubTask subTask) {
        this.id = subTask.getId();
        this.title = subTask.getTitle();
        this.description = subTask.getDescription();
        this.dueDate = subTask.getDueDate();
        if (subTask.getTask() != null) {
            this.taskId = subTask.getTask().getId();
            this.taskTitle = subTask.getTask().getTitle();
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Long getTaskId() {
        return taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    // Setters para o adapter
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    // Construtor vazio para o adapter
    public SubTaskResponseDTO() {}
}
