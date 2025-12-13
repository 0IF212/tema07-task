package br.ifsp.taskservice.controller;

import br.ifsp.taskservice.dto.TaskRequestDTO;
import br.ifsp.taskservice.dto.TaskResponseDTO;
import br.ifsp.taskservice.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<TaskResponseDTO> listar(@AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        return service.listar(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> buscarPorId(
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
    public ResponseEntity<TaskResponseDTO> criar(
        @RequestBody @Valid TaskRequestDTO dto,
        @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");
        String token = jwt.getTokenValue();
        TaskResponseDTO response = service.criar(dto, userId, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public TaskResponseDTO atualizar(
        @PathVariable Long id,
        @RequestBody @Valid TaskRequestDTO dto,
        @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");
        String token = jwt.getTokenValue();
        return service.atualizar(id, dto, userId, token);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
        @PathVariable Long id,
        @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");
        service.excluir(id, userId);
        return ResponseEntity.noContent().build();
    }
}

