package Topicos.service;

import Topicos.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.jwt.Claims;
import io.smallrye.jwt.build.Jwt;
import java.time.Duration;
import java.util.List;

@ApplicationScoped
public class TokenService {

    public String generateToken(Usuario usuario) {
        String role = usuario.getRole() != null ? usuario.getRole() : "USER";
        List<String> groups = "ADMIN".equalsIgnoreCase(role)
                ? List.of("ADMIN", "USER")
                : List.of("USER");

        return Jwt.issuer("https://example.com/issuer")
            .upn(usuario.getEmail())
            .claim(Claims.groups.name(), groups)
            .claim(Claims.full_name.name(), usuario.getNome())
            .expiresIn(Duration.ofHours(1))
            .sign(); 
    }
}