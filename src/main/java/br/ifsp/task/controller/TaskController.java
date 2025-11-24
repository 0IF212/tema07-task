package br.ifsp.task.controller;

import br.ifsp.task.dto.TaskDTO;
import br.ifsp.task.model.Task;
import br.ifsp.task.service.TaskService;
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

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<Task> listar(@AuthenticationPrincipal Jwt jwt) {
        return service.listar(jwt.getClaim("userId"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> buscarPorId(
        @PathVariable Long id,
        @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");
        try {
            return ResponseEntity.ok(service.buscarPorId(id, userId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Task criar(
        @RequestBody TaskDTO dto,
        @AuthenticationPrincipal Jwt jwt
    ) {
        return service.criar(dto, jwt.getClaim("userId"));
    }

    @PutMapping("/{id}")
    public Task atualizar(
        @PathVariable Long id,
        @RequestBody TaskDTO dto,
        @AuthenticationPrincipal Jwt jwt
    ) {
        return service.atualizar(id, dto, jwt.getClaim("userId"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
        @PathVariable Long id,
        @AuthenticationPrincipal Jwt jwt
    ) {
        service.excluir(id, jwt.getClaim("userId"));
        return ResponseEntity.noContent().build();
    }
}
