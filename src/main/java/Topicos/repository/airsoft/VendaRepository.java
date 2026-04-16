package Topicos.repository.airsoft;

import Topicos.model.airsoft.Venda;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class VendaRepository implements PanacheRepository<Venda> {

    public List<Venda> findByUsuario(Long usuarioId) {
        return find("usuario.id", usuarioId).list();
    }

    public List<Venda> findByStatus(Venda.StatusVenda status) {
        return find("status", status).list();
    }
}
