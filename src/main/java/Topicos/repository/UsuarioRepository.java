package Topicos.repository;

import Topicos.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public Optional<Usuario> findByEmail(String email) {
        return find("email = ?1 and ativo = true", email).firstResultOptional();
    }

    public List<Usuario> findByNome(String nome) {
        return find("nome like ?1 and ativo = true", "%" + nome + "%").list();
    }
}

