package Topicos.repository.airsoft;

import Topicos.model.airsoft.ArmaAirsoft;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ArmaAirsoftRepository implements PanacheRepository<ArmaAirsoft> {

    public List<ArmaAirsoft> findByNome(String nome) {
        return find("nome like ?1", "%" + nome + "%").list();
    }

    public List<ArmaAirsoft> findByTipoPropulsao(String tipoPropulsao) {
        return find("tipoPropulsao", tipoPropulsao).list();
    }

    public List<ArmaAirsoft> findByModelo(String modelo) {
        return find("modelo", modelo).list();
    }
}
