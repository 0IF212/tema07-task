package br.ifsp.task.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.ifsp.task.model.Tag;
import br.ifsp.task.service.TagService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TagController.class)
class TagControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TagService tagService;

    @MockBean
    org.springframework.security.core.userdetails.UserDetailsService appUserDetailsService;

    @MockBean
    org.springframework.security.oauth2.jwt.JwtDecoder jwtDecoder;

    @Test
    void getAll_ok() throws Exception {
        Mockito.when(tagService.getAllTags(1L)).thenReturn(List.of(new Tag()));

        mockMvc
            .perform(
                get("/api/tags").with(jwt().jwt(j -> j.claim("userId", 1L)))
            )
            .andExpect(status().isOk());
    }

    @Test
    void getById_ok() throws Exception {
        Tag tag = new Tag();
        tag.setId(5L);
        tag.setName("x");
        Mockito.when(tagService.findById(5L, 1L)).thenReturn(tag);

        mockMvc
            .perform(
                get("/api/tags/5").with(jwt().jwt(j -> j.claim("userId", 1L)))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    void create_ok() throws Exception {
        Tag saved = new Tag();
        saved.setId(1L);
        saved.setName("work");
        Mockito.when(tagService.createTag(Mockito.any(), eq(1L))).thenReturn(
            saved
        );

        mockMvc
            .perform(
                post("/api/tags")
                    .with(jwt().jwt(j -> j.claim("userId", 1L)))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\":\"work\"}")
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void addTaskToTag_ok() throws Exception {
        mockMvc
            .perform(
                post("/api/tags/2/tasks/3").with(
                    jwt().jwt(j -> j.claim("userId", 1L))
                )
            )
            .andExpect(status().isOk());
    }

    @Test
    void removeTaskFromTag_no_content() throws Exception {
        mockMvc
            .perform(
                delete("/api/tags/2/tasks/3").with(
                    jwt().jwt(j -> j.claim("userId", 1L))
                )
            )
            .andExpect(status().isNoContent());
    }

    @Test
    void deleteTag_service_error_500() throws Exception {
        Mockito.doThrow(new RuntimeException("err"))
            .when(tagService)
            .deleteTag(9L, 1L);

        mockMvc
            .perform(
                delete("/api/tags/9").with(
                    jwt().jwt(j -> j.claim("userId", 1L))
                )
            )
            .andExpect(status().isInternalServerError());
    }
}
