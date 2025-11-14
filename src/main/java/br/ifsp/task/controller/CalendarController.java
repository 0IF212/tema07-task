package br.ifsp.task.controller;

import br.ifsp.task.dto.CalendarEventDTO;
import br.ifsp.task.service.CalendarService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping("/events")
    public ResponseEntity<List<CalendarEventDTO>> getAllEvents() {
        List<CalendarEventDTO> events = calendarService.getAllEvents();
        return ResponseEntity.ok(events);
    }
}
