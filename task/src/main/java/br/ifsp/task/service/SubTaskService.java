package br.ifsp.task.service;

import br.ifsp.task.exception.ResourceNotFoundException;
import br.ifsp.task.model.SubTask;
import br.ifsp.task.model.Task;
import br.ifsp.task.repository.SubTaskRepository;
import br.ifsp.task.service.adapter.TaskServiceAdapter;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubTaskService {

    private final SubTaskRepository subTaskRepository;
    private final TaskServiceAdapter taskAdapter;
    private final UserService userService;

    public SubTaskService(
        SubTaskRepository subTaskRepository,
        TaskServiceAdapter taskAdapter,
        UserService userService
    ) {
        this.subTaskRepository = subTaskRepository;
        this.taskAdapter = taskAdapter;
        this.userService = userService;
    }

    public List<SubTask> getSubTasksByTask(Long taskId, Long userId) {
        return subTaskRepository.findByTaskIdAndOwnerId(taskId, userId);
    }

    public SubTask createSubTask(Long taskId, SubTask subTask, Long userId) {
        // Validação da task será feita no microserviço
        // Por enquanto, apenas validamos que o usuário existe
        subTask.setOwner(userService.findById(userId));
        // Task será validada quando SubTask for migrado para o microserviço também
        // Por enquanto, mantemos a estrutura local

        return subTaskRepository.save(subTask);
    }

    public SubTask updateSubTask(Long subTaskId, SubTask updated, Long userId) {
        SubTask subTask = subTaskRepository
            .findByIdAndOwnerId(subTaskId, userId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Subtarefa não encontrada")
            );

        subTask.setTitle(updated.getTitle());
        subTask.setDescription(updated.getDescription());
        subTask.setDueDate(updated.getDueDate());

        return subTaskRepository.save(subTask);
    }

    public void deleteSubTask(Long subTaskId, Long userId) {
        SubTask sub = subTaskRepository
            .findByIdAndOwnerId(subTaskId, userId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Subtarefa não encontrado")
            );

        subTaskRepository.delete(sub);
    }
}
