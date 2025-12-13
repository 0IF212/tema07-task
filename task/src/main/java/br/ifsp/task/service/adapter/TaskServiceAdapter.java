package br.ifsp.task.service.adapter;

import br.ifsp.task.client.TaskServiceClient;
import br.ifsp.task.dto.TaskDTO;
import br.ifsp.task.model.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskServiceAdapter {
    private final TaskServiceClient client;

    public TaskServiceAdapter(TaskServiceClient client) {
        this.client = client;
    }

    public List<Task> listar(Long userId, String token) {
        List<Map<String, Object>> responses = client.listTasks(userId, token);
        return responses.stream()
            .map(this::mapToTask)
            .collect(Collectors.toList());
    }

    public Task buscarPorId(Long id, Long userId, String token) {
        Map<String, Object> response = client.getTask(id, userId, token);
        return mapToTask(response);
    }

    public Task criar(TaskDTO dto, Long userId, String token) {
        Map<String, Object> response = client.createTask(dto, userId, token);
        return mapToTask(response);
    }

    public Task atualizar(Long id, TaskDTO dto, Long userId, String token) {
        Map<String, Object> response = client.updateTask(id, dto, userId, token);
        return mapToTask(response);
    }

    public void excluir(Long id, Long userId, String token) {
        client.deleteTask(id, userId, token);
    }

    public List<Task> listarPorProjeto(Long projectId, Long userId, String token) {
        List<Map<String, Object>> responses = client.listTasks(userId, token);
        return responses.stream()
            .filter(response -> {
                Object projId = response.get("projectId");
                return projId != null && projId.equals(projectId);
            })
            .map(this::mapToTask)
            .collect(Collectors.toList());
    }

    public Task buscarPorIdParaValidacao(Long id, Long userId, String token) {
        try {
            return buscarPorId(id, userId, token);
        } catch (Exception e) {
            throw new RuntimeException("Tarefa não encontrada");
        }
    }

    private Task mapToTask(Map<String, Object> map) {
        Task task = new Task();
        if (map.get("id") != null) {
            task.setId(Long.valueOf(map.get("id").toString()));
        }
        if (map.get("title") != null) {
            task.setTitle(map.get("title").toString());
        }
        if (map.get("description") != null) {
            task.setDescription(map.get("description").toString());
        }
        if (map.get("priority") != null) {
            task.setPriority(map.get("priority").toString());
        }
        if (map.get("due") != null) {
            task.setDue(LocalDate.parse(map.get("due").toString()));
        }
        // Project e Owner não são setados pois são apenas IDs no microserviço
        return task;
    }
}

