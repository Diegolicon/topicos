package Topicos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CarrinhoItemRequestDTO {
    @NotNull(message = "ID do produto e obrigatorio")
    public Long produtoId;

    @NotNull(message = "Quantidade e obrigatoria")
    @Min(value = 1, message = "Quantidade deve ser maior que 0")
    public Integer quantidade;

    public CarrinhoItemRequestDTO() {
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
}
