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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subtasks")
public class SubTaskController {

    private final SubTaskService subTaskService;

    public SubTaskController(SubTaskService subTaskService) {
        this.subTaskService = subTaskService;
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<SubTaskResponseDTO>> getSubTasksByTask(
        @PathVariable Long taskId,
        @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");

        List<SubTaskResponseDTO> response = subTaskService
            .getSubTasksByTask(taskId, userId)
            .stream()
            .map(SubTaskResponseDTO::new)
            .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/task/{taskId}")
    public ResponseEntity<SubTaskResponseDTO> createSubTask(
        @PathVariable Long taskId,
        @Valid @RequestBody SubTaskRequestDTO dto,
        @AuthenticationPrincipal Jwt jwt
    ) {
        SubTask subTask = new SubTask();
        subTask.setTitle(dto.getTitle());
        subTask.setDescription(dto.getDescription());
        subTask.setDueDate(dto.getDueDate());
        Long userId = jwt.getClaim("userId");

        SubTask saved = subTaskService.createSubTask(taskId, subTask, userId);
        return ResponseEntity.ok(new SubTaskResponseDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubTask> update(
        @PathVariable Long id,
        @RequestBody SubTask updated,
        @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");
        SubTask result = subTaskService.updateSubTask(id, updated, userId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{subTaskId}")
    public ResponseEntity<Void> deleteSubTask(
        @PathVariable Long subTaskId,
        @AuthenticationPrincipal Jwt jwt
    ) {
        subTaskService.deleteSubTask(subTaskId, jwt.getClaim("userId"));
        return ResponseEntity.noContent().build();
    }
}
