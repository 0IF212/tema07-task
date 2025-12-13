package br.ifsp.task.controller;

import br.ifsp.task.dto.TaskDTO;
import br.ifsp.task.model.Task;
import br.ifsp.task.service.adapter.TaskServiceAdapter;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    private final TaskServiceAdapter adapter;

    public TaskController(TaskServiceAdapter adapter) {
        this.adapter = adapter;
    }

    @GetMapping
    public List<Task> listar(@AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        String token = jwt.getTokenValue();
        return adapter.listar(userId, token);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> buscarPorId(
        @PathVariable Long id,
        @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");
        String token = jwt.getTokenValue();
        try {
            return ResponseEntity.ok(adapter.buscarPorId(id, userId, token));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Task criar(
        @RequestBody @Valid TaskDTO dto,
        @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");
        String token = jwt.getTokenValue();
        return adapter.criar(dto, userId, token);
    }

    @PutMapping("/{id}")
    public Task atualizar(
        @PathVariable Long id,
        @RequestBody @Valid TaskDTO dto,
        @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");
        String token = jwt.getTokenValue();
        return adapter.atualizar(id, dto, userId, token);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
        @PathVariable Long id,
        @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");
        String token = jwt.getTokenValue();
        adapter.excluir(id, userId, token);
        return ResponseEntity.noContent().build();
    }
}
