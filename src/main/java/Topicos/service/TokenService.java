package Topicos.service;

import Topicos.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty; // Importe isso
import org.eclipse.microprofile.jwt.Claims;
import io.smallrye.jwt.build.Jwt;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;

@ApplicationScoped
public class TokenService {

    public String generateToken(Usuario usuario) {
        return Jwt.issuer("https://example.com/issuer")
            .upn(usuario.getEmail())
            .groups("USER")
            .claim(Claims.full_name.name(), usuario.getNome())
            .expiresIn(Duration.ofHours(1))
            .sign(); 
    }
}