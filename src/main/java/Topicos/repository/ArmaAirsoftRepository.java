package Topicos.repository;

import java.util.List;

import Topicos.model.ArmaAirsoft;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ArmaAirsoftRepository implements PanacheRepository<ArmaAirsoft> {

    public List<ArmaAirsoft> findByNome(String nome) {
        return find("nome like ?1 and ativo = true", "%" + nome + "%").list();
    }

    public List<ArmaAirsoft> findByTipoPropulsao(String tipoPropulsao) {
        return find("tipoPropulsao = ?1 and ativo = true", tipoPropulsao).list();
    }

    public List<ArmaAirsoft> findByModelo(String modelo) {
        return find("modelo = ?1 and ativo = true", modelo).list();
    }
}

