package Topicos.repository;

import java.util.List;

import Topicos.model.Endereco;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {

    public List<Endereco> findByCep(String cep) {
        return find("cep like ?1", "%" + cep + "%").list();
    }
}

