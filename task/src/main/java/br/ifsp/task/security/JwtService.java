package br.ifsp.task.security;

import br.ifsp.task.model.UserAccount;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    private SecretKey key;

    @PostConstruct
    public void init() {
        // usa a secret como bytes; em prod prefira usar key store ou RS256
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(UserAccount user) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
            .setSubject(user.getUsername())
            .claim("userId", user.getId())
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }
}
