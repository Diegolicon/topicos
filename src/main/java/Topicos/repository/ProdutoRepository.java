package Topicos.repository;

import java.util.List;

import Topicos.model.Produto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {

    public List<Produto> findByNome(String nome) {
        return find("nome like ?1", "%" + nome + "%").list();
    }

    public List<Produto> findByMarca(String marca) {
        return find("marca", marca).list();
    }

    public List<Produto> findComEstoque() {
        return find("estoque > 0").list();
    }
}

