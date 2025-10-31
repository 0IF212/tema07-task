package br.ifsp.task.controller;

import br.ifsp.task.dto.ProjectDTO;
import br.ifsp.task.model.Project;
import br.ifsp.task.model.Task;
import br.ifsp.task.service.ProjectService;
import br.ifsp.task.service.TaskService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {

    private final ProjectService service;
    private final TaskService taskService;

    public ProjectController(ProjectService service, TaskService taskService) {
        this.service = service;
        this.taskService = taskService;
    }

    @GetMapping
    public List<Project> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Project buscarPorId(@Valid @PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public Project criar(@Valid @RequestBody ProjectDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    public Project atualizar(
        @Valid @PathVariable Long id,
        @RequestBody ProjectDTO dto
    ) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void excluir(@Valid @PathVariable Long id) {
        service.excluir(id);
    }

    @GetMapping("/{id}/tasks")
    public List<Task> listarTarefasDoProjeto(@Valid @PathVariable Long id) {
        return taskService.listarPorProjeto(id);
    }
}
