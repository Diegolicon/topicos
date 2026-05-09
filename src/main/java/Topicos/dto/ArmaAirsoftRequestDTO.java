package Topicos.dto;

import Topicos.model.TipoPropulsao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ArmaAirsoftRequestDTO {

    @NotBlank(message = "O nome não pode ser vazio.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    public String nome;

    @NotBlank(message = "A descrição não pode ser vazia.")
    public String descricao;

    @NotNull(message = "O preço deve ser informado.")
    @Positive(message = "O preço deve ser maior que zero.")
    public Double preco;

    @NotNull(message = "O estoque deve ser informado.")
    @Positive(message = "O estoque deve ser um número positivo.")
    public Integer estoque;

    @NotBlank(message = "A marca deve ser informada.")
    public String marca;

    @NotNull(message = "O tipo de propulsão (AEG, GBB, etc) é obrigatório.")
    public TipoPropulsao tipoPropulsao;

    @NotBlank(message = "O modelo deve ser informado.")
    public String modelo;

    @NotNull(message = "A velocidade da escopeta deve ser informada.")
    @Positive(message = "A velocidade deve ser maior que zero.")
    public Double velocidadeEscopeta;

    @NotNull(message = "O alcance efetivo deve ser informado.")
    @Positive(message = "O alcance deve ser maior que zero.")
    public Double alcanceEfetivo;

    public ArmaAirsoftRequestDTO() {
    }

    // Mantendo os getters e setters para compatibilidade com JSON-B/Jackson
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
    public TipoPropulsao getTipoPropulsao() { return tipoPropulsao; }
    public void setTipoPropulsao(TipoPropulsao tipoPropulsao) { this.tipoPropulsao = tipoPropulsao; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public Double getVelocidadeEscopeta() { return velocidadeEscopeta; }
    public void setVelocidadeEscopeta(Double velocidadeEscopeta) { this.velocidadeEscopeta = velocidadeEscopeta; }
    public Double getAlcanceEfetivo() { return alcanceEfetivo; }
    public void setAlcanceEfetivo(Double alcanceEfetivo) { this.alcanceEfetivo = alcanceEfetivo; }
}