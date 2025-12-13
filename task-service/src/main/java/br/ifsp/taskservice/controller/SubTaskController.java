package br.ifsp.taskservice.controller;

import br.ifsp.taskservice.dto.SubTaskRequestDTO;
import br.ifsp.taskservice.dto.SubTaskResponseDTO;
import br.ifsp.taskservice.service.SubTaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subtasks")
@CrossOrigin(origins = "*")
public class SubTaskController {
    private final SubTaskService service;

    public SubTaskController(SubTaskService service) {
        this.service = service;
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<SubTaskResponseDTO>> getSubTasksByTask(
        @PathVariable Long taskId,
        @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");
        return ResponseEntity.ok(service.getSubTasksByTask(taskId, userId));
    }

    @PostMapping("/task/{taskId}")
    public ResponseEntity<SubTaskResponseDTO> createSubTask(
        @PathVariable Long taskId,
        @RequestBody @Valid SubTaskRequestDTO dto,
        @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");
        SubTaskResponseDTO response = service.createSubTask(taskId, dto, userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubTaskResponseDTO> update(
        @PathVariable Long id,
        @RequestBody @Valid SubTaskRequestDTO dto,
        @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");
        SubTaskResponseDTO result = service.updateSubTask(id, dto, userId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{subTaskId}")
    public ResponseEntity<Void> deleteSubTask(
        @PathVariable Long subTaskId,
        @AuthenticationPrincipal Jwt jwt
    ) {
        service.deleteSubTask(subTaskId, jwt.getClaim("userId"));
        return ResponseEntity.noContent().build();
    }
}

