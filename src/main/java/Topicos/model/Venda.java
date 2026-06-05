package Topicos.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendas")
public class Venda extends DefaultEntity {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ItemVenda> itens = new ArrayList<>();

    @Column(name = "total_venda", nullable = false)
    private Double totalVenda;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private StatusVenda status;

    @Column(length = 200)
    private String observacoes;

    public enum StatusVenda {
        PENDENTE, PROCESSANDO, CONCLUÍDA, CANCELADA
    }

    public Venda() {
        this.status = StatusVenda.PENDENTE;
        this.totalVenda = 0.0;
    }

    public Venda(Usuario usuario) {
        this();
        this.usuario = usuario;
    }

    public void adicionarItem(ItemVenda item) {
        itens.add(item);
        item.setVenda(this);
        atualizarTotal();
    }

    public void removerItem(ItemVenda item) {
        itens.remove(item);
        atualizarTotal();
    }

    public void atualizarTotal() {
        this.totalVenda = itens.stream()
                .mapToDouble(ItemVenda::getSubtotal)
                .sum();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    public Double getTotalVenda() {
        return totalVenda;
    }

    public void setTotalVenda(Double totalVenda) {
        this.totalVenda = totalVenda;
    }

    public StatusVenda getStatus() {
        return status;
    }

    public void setStatus(StatusVenda status) {
        this.status = status;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}

