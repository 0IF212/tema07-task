package br.ifsp.task.security;

import br.ifsp.task.model.UserAccount;
import br.ifsp.task.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    public AppUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
        UserAccount user = repo
            .findByUsername(username)
            .orElseThrow(() ->
                new UsernameNotFoundException("Usuário não encontrado")
            );

        return User.withUsername(user.getUsername())
            .password(user.getPassword())
            .authorities(java.util.Collections.emptyList()) // ← lista vazia de authorities
            .build();
    }
}
