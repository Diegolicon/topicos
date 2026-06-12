package Topicos.repository;

import java.util.List;

import Topicos.model.Venda;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VendaRepository implements PanacheRepository<Venda> {

    public List<Venda> findByUsuario(Long usuarioId) {
        return find("usuario.id = ?1 and ativo = true", usuarioId).list();
    }

    public List<Venda> findByStatus(Venda.StatusVenda status) {
        return find("status = ?1 and ativo = true", status).list();
    }
}

