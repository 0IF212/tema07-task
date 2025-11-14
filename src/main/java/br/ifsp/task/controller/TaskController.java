package br.ifsp.task.controller;

import br.ifsp.task.dto.TaskDTO;
import br.ifsp.task.model.Task;
import br.ifsp.task.service.TaskService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
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
    public List<Task> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> buscarPorId(@Valid @PathVariable Long id) {
        return service
            .buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Task criar(@Valid @RequestBody TaskDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    public Task atualizar(
        @Valid @PathVariable Long id,
        @RequestBody TaskDTO dto
    ) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@Valid @PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
