package br.ifsp.task.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.ifsp.task.dto.ProjectDTO;
import br.ifsp.task.exception.ResourceNotFoundException;
import br.ifsp.task.model.Project;
import br.ifsp.task.model.Task;
import br.ifsp.task.service.ProjectService;
import br.ifsp.task.service.TaskService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProjectService projectService;

    @MockBean
    TaskService taskService;

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwt() {
        return SecurityMockMvcRequestPostProcessors.jwt().jwt(jwt ->
            jwt.claim("userId", 1L)
        );
    }

    @Test
    void listar_ok() throws Exception {
        Mockito.when(projectService.listar(1L)).thenReturn(
            List.of(new Project())
        );

        mockMvc
            .perform(get("/api/projects").with(jwt()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    void buscarPorId_ok() throws Exception {
        Mockito.when(projectService.buscarPorId(10L, 1L)).thenReturn(
            new Project()
        );

        mockMvc
            .perform(get("/api/projects/10").with(jwt()))
            .andExpect(status().isOk());
    }

    @Test
    void criar_ok() throws Exception {
        Mockito.when(projectService.criar(Mockito.any(), eq(1L))).thenReturn(
            new Project()
        );

        mockMvc
            .perform(
                post("/api/projects")
                    .with(jwt())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """
                        {"title":"p1", "dataInicio": "2025-12-31"}
                        """
                    )
            )
            .andExpect(status().isOk());
    }

    @Test
    void listarTarefas_ok() throws Exception {
        Mockito.when(taskService.listarPorProjeto(10L, 1L)).thenReturn(
            List.of(new Task())
        );

        mockMvc
            .perform(get("/api/projects/10/tasks").with(jwt()))
            .andExpect(status().isOk());
    }

    @Test
    void listar_semJwt_403() throws Exception {
        mockMvc
            .perform(get("/api/projects"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void buscarPorId_notFound() throws Exception {
        Mockito.when(projectService.buscarPorId(99L, 1L)).thenThrow(
            new ResourceNotFoundException("not found")
        );

        mockMvc
            .perform(get("/api/projects/99").with(jwt()))
            .andExpect(status().isNotFound());
    }

    @Test
    void criar_badRequest_jsonInvalido() throws Exception {
        mockMvc
            .perform(
                post("/api/projects")
                    .with(jwt())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """
                                {"title":"","dataInicio":""}
                        """
                    )
            )
            .andExpect(status().isBadRequest());
    }
}
