package br.ifsp.task.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthLoginDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
