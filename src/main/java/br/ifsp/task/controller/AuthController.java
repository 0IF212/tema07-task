package br.ifsp.task.controller;

import br.ifsp.task.dto.*;
import br.ifsp.task.model.UserAccount;
import br.ifsp.task.security.JwtService;
import br.ifsp.task.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(
        UserService userService,
        AuthenticationManager authenticationManager,
        JwtService jwtService
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthRegisterDTO dto) {
        UserAccount saved = userService.register(
            dto.getUsername(),
            dto.getPassword()
        );
        return ResponseEntity.status(201).body(saved.getUsername());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
        @Valid @RequestBody AuthLoginDTO dto
    ) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                dto.getUsername(),
                dto.getPassword()
            )
        );
        UserAccount user = userService.findByUsername(dto.getUsername());
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}
