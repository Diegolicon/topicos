package Topicos.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "endereco_entrega_id")
    private Endereco enderecoEntrega;

    @Column(name = "forma_pagamento", length = 20)
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @Column(name = "chave_pix", length = 100)
    private String chavePix;

    public enum StatusVenda {
        PENDENTE, PROCESSANDO, CONCLUIDA, CONCLUÍDA, CANCELADA
    }

    public enum FormaPagamento {
        PIX
    }

    public Venda() {
        this.status = StatusVenda.PENDENTE;
        this.totalVenda = 0.0;
        this.formaPagamento = FormaPagamento.PIX;
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

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getChavePix() {
        return chavePix;
    }

    public void setChavePix(String chavePix) {
        this.chavePix = chavePix;
    }
}
