package br.ifsp.task.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.ifsp.task.dto.*;
import br.ifsp.task.model.UserAccount;
import br.ifsp.task.security.JwtService;
import br.ifsp.task.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    JwtService jwtService;

    @Test
    void register_ok() throws Exception {
        UserAccount user = new UserAccount();
        user.setUsername("john");

        Mockito.when(userService.register("john", "123456")).thenReturn(user);

        mockMvc
            .perform(
                post("/api/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """
                        {"username":"john","password":"123456"}
                        """
                    )
            )
            .andExpect(status().isCreated())
            .andExpect(content().string("john"));
    }

    @Test
    void login_ok() throws Exception {
        Authentication auth = Mockito.mock(Authentication.class);
        Mockito.when(authenticationManager.authenticate(any())).thenReturn(
            auth
        );

        UserAccount user = new UserAccount();
        user.setUsername("john");
        Mockito.when(userService.findByUsername("john")).thenReturn(user);

        Mockito.when(jwtService.generateToken(user)).thenReturn("token123");

        mockMvc
            .perform(
                post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """
                        {"username":"john","password":"123456"}
                        """
                    )
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value("token123"));
    }
}
