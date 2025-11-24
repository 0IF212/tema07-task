package br.ifsp.task.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.ifsp.task.dto.TaskDTO;
import br.ifsp.task.model.Task;
import br.ifsp.task.security.SecurityConfig;
import br.ifsp.task.service.TaskService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TaskService taskService;

    // Necessário porque SecurityConfig cria um AuthenticationProvider baseado nele.
    @MockBean
    org.springframework.security.core.userdetails.UserDetailsService appUserDetailsService;

    // Necessário porque o SecurityConfig registra JwtDecoder
    @MockBean
    org.springframework.security.oauth2.jwt.JwtDecoder jwtDecoder;

    @Test
    void listar_ok() throws Exception {
        Mockito.when(taskService.listar(1L)).thenReturn(List.of(new Task()));

        mockMvc
            .perform(
                get("/api/tasks").with(jwt().jwt(j -> j.claim("userId", 1L)))
            )
            .andExpect(status().isOk());
    }

    @Test
    void listar_unauthorized() throws Exception {
        mockMvc.perform(get("/api/tasks")).andExpect(status().isUnauthorized());
    }

    @Test
    void buscarPorId_ok() throws Exception {
        Mockito.when(taskService.buscarPorId(9L, 1L)).thenReturn(new Task());

        mockMvc
            .perform(
                get("/api/tasks/9").with(jwt().jwt(j -> j.claim("userId", 1L)))
            )
            .andExpect(status().isOk());
    }

    @Test
    void buscarPorId_notFound() throws Exception {
        Mockito.when(taskService.buscarPorId(9L, 1L)).thenThrow(
            new RuntimeException("notfound")
        );

        mockMvc
            .perform(
                get("/api/tasks/9").with(jwt().jwt(j -> j.claim("userId", 1L)))
            )
            .andExpect(status().isNotFound());
    }

    @Test
    void criar_ok() throws Exception {
        Mockito.when(
            taskService.criar(Mockito.any(TaskDTO.class), eq(1L))
        ).thenReturn(new Task());

        mockMvc
            .perform(
                post("/api/tasks")
                    .with(jwt().jwt(j -> j.claim("userId", 1L)))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"title\":\"t1\"}")
            )
            .andExpect(status().isOk());
    }

    @Test
    void excluir_ok() throws Exception {
        mockMvc
            .perform(
                delete("/api/tasks/5").with(
                    jwt().jwt(j -> j.claim("userId", 1L))
                )
            )
            .andExpect(status().isNoContent());
    }
}
