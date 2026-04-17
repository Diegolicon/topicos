package Topicos.dto;

import Topicos.dto.ProdutoSimplicadoResponseDTO;

public class ItemVendaResponseDTO {
    public Long id;
    public Long vendaId;
    public ProdutoSimplicadoResponseDTO produto;
    public Integer quantidade;
    public Double precoUnitario;
    public Double subtotal;
    public Long criadoEm;
    public Long atualizadoEm;

    public ItemVendaResponseDTO() {
    }

    public ItemVendaResponseDTO(Long id, Long vendaId, ProdutoSimplicadoResponseDTO produto, Integer quantidade,
                                Double precoUnitario, Double subtotal, Long criadoEm, Long atualizadoEm) {
        this.id = id;
        this.vendaId = vendaId;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subtotal = subtotal;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVendaId() {
        return vendaId;
    }

    public void setVendaId(Long vendaId) {
        this.vendaId = vendaId;
    }

    public ProdutoSimplicadoResponseDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoSimplicadoResponseDTO produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Long getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Long criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Long getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(Long atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}

