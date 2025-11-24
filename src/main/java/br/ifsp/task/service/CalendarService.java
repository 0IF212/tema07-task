package br.ifsp.task.service;

import br.ifsp.task.dto.CalendarEventDTO;
import br.ifsp.task.model.Project;
import br.ifsp.task.model.SubTask;
import br.ifsp.task.model.Task;
import br.ifsp.task.repository.ProjectRepository;
import br.ifsp.task.repository.SubTaskRepository;
import br.ifsp.task.repository.TaskRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CalendarService {

    private final TaskRepository taskRepository;
    private final SubTaskRepository subtaskRepository;
    private final ProjectRepository projectRepository;

    public CalendarService(
        TaskRepository taskRepository,
        SubTaskRepository subtaskRepository,
        ProjectRepository projectRepository
    ) {
        this.taskRepository = taskRepository;
        this.subtaskRepository = subtaskRepository;
        this.projectRepository = projectRepository;
    }

    public List<CalendarEventDTO> getAllEvents(Long userId) {
        List<CalendarEventDTO> events = new ArrayList<>();

        // Tasks
        for (Task task : taskRepository.findByOwnerId(userId)) {
            if (task.getDue() != null) {
                events.add(
                    new CalendarEventDTO(
                        task.getId(),
                        task.getTitle(),
                        "TASK",
                        task.getDue()
                    )
                );
            }
        }

        // Subtasks

        for (SubTask subtask : subtaskRepository.findByOwnerId(userId)) {
            if (subtask.getDueDate() != null) {
                events.add(
                    new CalendarEventDTO(
                        subtask.getId(),
                        subtask.getTitle(),
                        "SUBTASK",
                        subtask.getDueDate()
                    )
                );
            }
        }

        // Projects
        for (Project project : projectRepository.findByOwnerId(userId)) {
            if (project.getDateEnd() != null) {
                events.add(
                    new CalendarEventDTO(
                        project.getId(),
                        project.getTitle(),
                        "PROJECT",
                        project.getDateEnd()
                    )
                );
            }
        }

        return events;
    }
}
