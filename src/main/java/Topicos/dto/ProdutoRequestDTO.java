package Topicos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class ProdutoRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    public String nome;

    @NotBlank(message = "A descrição é obrigatória")
    public String descricao;

    @NotNull(message = "O preço é obrigatório")
    @PositiveOrZero(message = "O preço deve ser maior ou igual a zero")
    public Double preco;

    @NotNull(message = "O estoque é obrigatório")
    @PositiveOrZero(message = "O estoque não pode ser negativo")
    public Integer estoque;

    @NotBlank(message = "A marca é obrigatória")
    public String marca;

    public ProdutoRequestDTO() {
    }

    public ProdutoRequestDTO(String nome, String descricao, Double preco, Integer estoque, String marca) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.marca = marca;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }
    public Integer getEstoque() { return estoque; }
    public void setEstoque(Integer estoque) { this.estoque = estoque; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
}