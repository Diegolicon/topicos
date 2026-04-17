package Topicos.repository;

import java.util.List;

import Topicos.model.ArmaAirsoft;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

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

