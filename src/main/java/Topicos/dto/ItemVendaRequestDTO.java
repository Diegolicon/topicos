package Topicos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ItemVendaRequestDTO {
    @NotNull(message = "ID do produto é obrigatório")
    public Long produtoId;
    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser maior que 0")
    public Integer quantidade;
    @NotNull(message = "Preço unitário é obrigatório")
    @Min(value = 0, message = "Preço unitário deve ser maior ou igual a 0")
    public Double precoUnitario;

    public ItemVendaRequestDTO() {
    }

    public ItemVendaRequestDTO(Long produtoId, Integer quantidade, Double precoUnitario) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
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
}

