package br.ifsp.task.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.ifsp.task.dto.SubTaskRequestDTO;
import br.ifsp.task.model.SubTask;
import br.ifsp.task.service.SubTaskService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SubTaskController.class)
class SubTaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SubTaskService subTaskService;

    @MockBean
    org.springframework.security.core.userdetails.UserDetailsService appUserDetailsService;

    @MockBean
    org.springframework.security.oauth2.jwt.JwtDecoder jwtDecoder;

    @Test
    void getSubTasksByTask_ok() throws Exception {
        Mockito.when(subTaskService.getSubTasksByTask(10L, 1L)).thenReturn(
            List.of(new SubTask())
        );

        mockMvc
            .perform(
                get("/api/subtasks/task/10").with(
                    jwt().jwt(j -> j.claim("userId", 1L))
                )
            )
            .andExpect(status().isOk());
    }

    @Test
    void createSubTask_ok() throws Exception {
        SubTask saved = new SubTask();
        saved.setId(1L);
        Mockito.when(
            subTaskService.createSubTask(
                eq(10L),
                Mockito.any(SubTask.class),
                eq(1L)
            )
        ).thenReturn(saved);

        mockMvc
            .perform(
                post("/api/subtasks/task/10")
                    .with(jwt().jwt(j -> j.claim("userId", 1L)))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        "{\"title\":\"t1\",\"description\":\"d1\",\"dueDate\":\"2026-12-30\"}"
                    )
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void createSubTask_validation_400() throws Exception {
        mockMvc
            .perform(
                post("/api/subtasks/task/10")
                    .with(jwt().jwt(j -> j.claim("userId", 1L)))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}")
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    void deleteSubTask_no_content() throws Exception {
        mockMvc
            .perform(
                delete("/api/subtasks/5").with(
                    jwt().jwt(j -> j.claim("userId", 1L))
                )
            )
            .andExpect(status().isNoContent());
    }

    @Test
    void service_error_500() throws Exception {
        Mockito.doThrow(new RuntimeException("err"))
            .when(subTaskService)
            .deleteSubTask(5L, 1L);

        mockMvc
            .perform(
                delete("/api/subtasks/5").with(
                    jwt().jwt(j -> j.claim("userId", 1L))
                )
            )
            .andExpect(status().isInternalServerError());
    }
}
