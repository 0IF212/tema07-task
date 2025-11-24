package br.ifsp.task.dto;

import java.time.LocalDate;

public class CalendarEventDTO {

    private Long id;
    private String title;
    private String type; // "TASK", "SUBTASK", "PROJECT"
    private LocalDate dueDate;

    public CalendarEventDTO(
        Long id,
        String title,
        String type,
        LocalDate dueDate
    ) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.dueDate = dueDate;
    }

    public CalendarEventDTO() {}

    // Getters e setters
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
