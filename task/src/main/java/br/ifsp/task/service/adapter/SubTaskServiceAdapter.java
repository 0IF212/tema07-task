package br.ifsp.task.service.adapter;

import br.ifsp.task.client.SubTaskServiceClient;
import br.ifsp.task.dto.SubTaskRequestDTO;
import br.ifsp.task.dto.SubTaskResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SubTaskServiceAdapter {
    private final SubTaskServiceClient client;

    public SubTaskServiceAdapter(SubTaskServiceClient client) {
        this.client = client;
    }

    public List<SubTaskResponseDTO> getSubTasksByTask(Long taskId, Long userId, String token) {
        List<Map<String, Object>> responses = client.listSubTasksByTask(taskId, userId, token);
        return responses.stream()
            .map(this::mapToSubTaskResponseDTO)
            .collect(Collectors.toList());
    }

    public SubTaskResponseDTO criar(Long taskId, SubTaskRequestDTO dto, Long userId, String token) {
        Map<String, Object> response = client.createSubTask(taskId, dto, userId, token);
        return mapToSubTaskResponseDTO(response);
    }

    public SubTaskResponseDTO atualizar(Long id, SubTaskRequestDTO dto, Long userId, String token) {
        Map<String, Object> response = client.updateSubTask(id, dto, userId, token);
        return mapToSubTaskResponseDTO(response);
    }

    public void excluir(Long id, Long userId, String token) {
        client.deleteSubTask(id, userId, token);
    }

    private SubTaskResponseDTO mapToSubTaskResponseDTO(Map<String, Object> map) {
        SubTaskResponseDTO dto = new SubTaskResponseDTO();
        
        if (map.get("id") != null) {
            dto.setId(Long.valueOf(map.get("id").toString()));
        }
        if (map.get("title") != null) {
            dto.setTitle(map.get("title").toString());
        }
        if (map.get("description") != null) {
            dto.setDescription(map.get("description").toString());
        }
        if (map.get("dueDate") != null) {
            dto.setDueDate(LocalDate.parse(map.get("dueDate").toString()));
        }
        if (map.get("taskId") != null) {
            dto.setTaskId(Long.valueOf(map.get("taskId").toString()));
        }
        
        return dto;
    }
}

