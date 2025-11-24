package br.ifsp.task.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt; // IMPORT ESSENCIAL
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.ifsp.task.dto.CalendarEventDTO;
import br.ifsp.task.service.CalendarService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CalendarController.class)
class CalendarControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CalendarService calendarService;

    @Test
    void getAllEvents_ok() throws Exception {
        Mockito.when(calendarService.getAllEvents(1L)).thenReturn(
            List.of(new CalendarEventDTO())
        );

        mockMvc
            .perform(
                get("/api/calendar/events").with(
                    jwt().jwt(jwt -> jwt.claim("userId", 1L))
                )
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }
}
