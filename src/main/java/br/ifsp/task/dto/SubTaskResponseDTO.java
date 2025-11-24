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
}
