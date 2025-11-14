package br.ifsp.task.service;

import br.ifsp.task.exception.ResourceNotFoundException;
import br.ifsp.task.model.SubTask;
import br.ifsp.task.model.Task;
import br.ifsp.task.repository.SubTaskRepository;
import br.ifsp.task.repository.TaskRepository;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubTaskService {

    @Autowired
    private SubTaskRepository subTaskRepository;

    @Autowired
    private TaskRepository taskRepository;

    public List<SubTask> getSubTasksByTask(Long taskId) {
        return subTaskRepository.findByTaskId(taskId);
    }

    public SubTask createSubTask(Long taskId, SubTask subTask) {
        Task task = taskRepository
            .findById(taskId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Tarefa principal não encontrada")
            );
        subTask.setTask(task);
        return subTaskRepository.save(subTask);
    }

    public SubTask updateSubTask(Long subTaskId, SubTask updatedSubTask) {
        SubTask subTask = subTaskRepository
            .findById(subTaskId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Subtarefa não encontrada")
            );

        subTask.setTitle(updatedSubTask.getTitle());
        subTask.setDescription(updatedSubTask.getDescription());
        subTask.setDueDate(updatedSubTask.getDueDate());
        return subTaskRepository.save(subTask);
    }

    public void deleteSubTask(Long subTaskId) {
        if (!subTaskRepository.existsById(subTaskId)) {
            throw new ResourceNotFoundException("Subtarefa não encontrada");
        }
        subTaskRepository.deleteById(subTaskId);
    }
}
