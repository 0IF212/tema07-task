package br.ifsp.task.controller;

import br.ifsp.task.dto.SubTaskRequestDTO;
import br.ifsp.task.dto.SubTaskResponseDTO;
import br.ifsp.task.model.SubTask;
import br.ifsp.task.service.SubTaskService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subtasks")
public class SubTaskController {

    @Autowired
    private SubTaskService subTaskService;

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<SubTaskResponseDTO>> getSubTasksByTask(
        @PathVariable Long taskId
    ) {
        List<SubTaskResponseDTO> response = subTaskService
            .getSubTasksByTask(taskId)
            .stream()
            .map(SubTaskResponseDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/task/{taskId}")
    public ResponseEntity<SubTaskResponseDTO> createSubTask(
        @PathVariable Long taskId,
        @Valid @RequestBody SubTaskRequestDTO dto
    ) {
        SubTask subTask = new SubTask(
            dto.getTitle(),
            dto.getDescription(),
            dto.getDueDate(),
            null
        );
        SubTask saved = subTaskService.createSubTask(taskId, subTask);
        return ResponseEntity.ok(new SubTaskResponseDTO(saved));
    }

    @PutMapping("/{subTaskId}")
    public ResponseEntity<SubTaskResponseDTO> updateSubTask(
        @PathVariable Long subTaskId,
        @Valid @RequestBody SubTaskRequestDTO dto
    ) {
        SubTask updated = new SubTask(
            dto.getTitle(),
            dto.getDescription(),
            dto.getDueDate(),
            null
        );
        SubTask saved = subTaskService.updateSubTask(subTaskId, updated);
        return ResponseEntity.ok(new SubTaskResponseDTO(saved));
    }

    @DeleteMapping("/{subTaskId}")
    public ResponseEntity<Void> deleteSubTask(@PathVariable Long subTaskId) {
        subTaskService.deleteSubTask(subTaskId);
        return ResponseEntity.noContent().build();
    }
}
