package Topicos.repository;

import java.util.List;
import java.util.Optional;

import Topicos.model.CarrinhoDesejos;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CarrinhoDesejosRepository implements PanacheRepository<CarrinhoDesejos> {

    public List<CarrinhoDesejos> findByUsuario(Long usuarioId) {
        return find("usuario.id = ?1 and ativo = true", usuarioId).list();
    }

    public Optional<CarrinhoDesejos> findByUsuarioAndProduto(Long usuarioId, Long produtoId) {
        return find("usuario.id = ?1 and produto.id = ?2", usuarioId, produtoId).firstResultOptional();
    }
}
