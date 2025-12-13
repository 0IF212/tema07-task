package br.ifsp.task.controller;

import br.ifsp.task.dto.SubTaskRequestDTO;
import br.ifsp.task.dto.SubTaskResponseDTO;
import br.ifsp.task.service.adapter.SubTaskServiceAdapter;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subtasks")
public class SubTaskController {

    private final SubTaskServiceAdapter adapter;

    public SubTaskController(SubTaskServiceAdapter adapter) {
        this.adapter = adapter;
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<SubTaskResponseDTO>> getSubTasksByTask(
        @PathVariable Long taskId,
        @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");
        String token = jwt.getTokenValue();
        List<SubTaskResponseDTO> response = adapter.getSubTasksByTask(taskId, userId, token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/task/{taskId}")
    public ResponseEntity<SubTaskResponseDTO> createSubTask(
        @PathVariable Long taskId,
        @Valid @RequestBody SubTaskRequestDTO dto,
        @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");
        String token = jwt.getTokenValue();
        SubTaskResponseDTO response = adapter.criar(taskId, dto, userId, token);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubTaskResponseDTO> update(
        @PathVariable Long id,
        @Valid @RequestBody SubTaskRequestDTO dto,
        @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");
        String token = jwt.getTokenValue();
        SubTaskResponseDTO result = adapter.atualizar(id, dto, userId, token);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{subTaskId}")
    public ResponseEntity<Void> deleteSubTask(
        @PathVariable Long subTaskId,
        @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");
        String token = jwt.getTokenValue();
        adapter.excluir(subTaskId, userId, token);
        return ResponseEntity.noContent().build();
    }
}
