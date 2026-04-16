package Topicos.dto.airsoft;

import Topicos.model.airsoft.TipoPropulsao;

public class ArmaAirsoftResponseDTO {
    public Long id;
    public String nome;
    public String descricao;
    public Double preco;
    public Integer estoque;
    public String marca;
    public TipoPropulsao tipoPropulsao;
    public String modelo;
    public Double velocidadeEscopeta;
    public Double alcanceEfetivo;
    public Long criadoEm;
    public Long atualizadoEm;

    public ArmaAirsoftResponseDTO() {
    }

    public ArmaAirsoftResponseDTO(Long id, String nome, String descricao, Double preco, Integer estoque, String marca,
                                  TipoPropulsao tipoPropulsao, String modelo, Double velocidadeEscopeta, 
                                  Double alcanceEfetivo, Long criadoEm, Long atualizadoEm) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.marca = marca;
        this.tipoPropulsao = tipoPropulsao;
        this.modelo = modelo;
        this.velocidadeEscopeta = velocidadeEscopeta;
        this.alcanceEfetivo = alcanceEfetivo;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public TipoPropulsao getTipoPropulsao() {
        return tipoPropulsao;
    }

    public void setTipoPropulsao(TipoPropulsao tipoPropulsao) {
        this.tipoPropulsao = tipoPropulsao;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getVelocidadeEscopeta() {
        return velocidadeEscopeta;
    }

    public void setVelocidadeEscopeta(Double velocidadeEscopeta) {
        this.velocidadeEscopeta = velocidadeEscopeta;
    }

    public Double getAlcanceEfetivo() {
        return alcanceEfetivo;
    }

    public void setAlcanceEfetivo(Double alcanceEfetivo) {
        this.alcanceEfetivo = alcanceEfetivo;
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
