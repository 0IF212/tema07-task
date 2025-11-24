package br.ifsp.task.service;

import br.ifsp.task.exception.ResourceNotFoundException;
import br.ifsp.task.model.UserAccount;
import br.ifsp.task.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
        UserRepository repository,
        PasswordEncoder passwordEncoder
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserAccount register(String username, String rawPassword) {
        if (repository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Usuário já existe");
        }
        UserAccount u = new UserAccount();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(rawPassword));
        return repository.save(u);
    }

    public UserAccount findByUsername(String username) {
        return repository.findByUsername(username).orElse(null);
    }

    public UserAccount findById(long Id) {
        return repository
            .findById(Id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Usuario não encontrado")
            );
    }
}
