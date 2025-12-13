package br.ifsp.task.controller;

import br.ifsp.task.dto.ProjectDTO;
import br.ifsp.task.model.Project;
import br.ifsp.task.model.Task;
import br.ifsp.task.service.ProjectService;
import br.ifsp.task.service.adapter.TaskServiceAdapter;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {

    private final ProjectService service;
    private final TaskServiceAdapter taskAdapter;

    public ProjectController(ProjectService service, TaskServiceAdapter taskAdapter) {
        this.service = service;
        this.taskAdapter = taskAdapter;
    }

    @GetMapping
    public List<Project> listar(@AuthenticationPrincipal Jwt jwt) {
        return service.listar(jwt.getClaim("userId"));
    }

    @GetMapping("/{id}")
    public Project buscarPorId(
        @PathVariable Long id,
        @AuthenticationPrincipal Jwt jwt
    ) {
        return service.buscarPorId(id, jwt.getClaim("userId"));
    }

    @PostMapping
    public Project criar(
        @Valid @RequestBody ProjectDTO dto,
        @AuthenticationPrincipal Jwt jwt
    ) {
        return service.criar(dto, jwt.getClaim("userId"));
    }

    @PutMapping("/{id}")
    public Project atualizar(
        @PathVariable Long id,
        @RequestBody ProjectDTO dto,
        @AuthenticationPrincipal Jwt jwt
    ) {
        return service.atualizar(id, dto, jwt.getClaim("userId"));
    }

    @DeleteMapping("/{id}")
    public void excluir(
        @PathVariable Long id,
        @AuthenticationPrincipal Jwt jwt
    ) {
        service.excluir(id, jwt.getClaim("userId"));
    }

    @GetMapping("/{id}/tasks")
    public List<Task> listarTarefasDoProjeto(
        @PathVariable Long id,
        @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");
        String token = jwt.getTokenValue();
        return taskAdapter.listarPorProjeto(id, userId, token);
    }
}
