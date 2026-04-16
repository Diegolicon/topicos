package Topicos.repository.airsoft;

import Topicos.model.airsoft.ItemVenda;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ItemVendaRepository implements PanacheRepository<ItemVenda> {

    public List<ItemVenda> findByVenda(Long vendaId) {
        return find("venda.id", vendaId).list();
    }

    public List<ItemVenda> findByProduto(Long produtoId) {
        return find("produto.id", produtoId).list();
    }
}
