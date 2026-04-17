package Topicos.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ARMA_AIRSOFT")
public class ArmaAirsoft extends Produto {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPropulsao tipoPropulsao;

    @Column(length = 50)
    private String modelo;

    @Column
    private Double velocidadeEscopeta;

    @Column
    private Double alcanceEfetivo;

    public ArmaAirsoft() {
    }

    public ArmaAirsoft(String nome, String descricao, Double preco, Integer estoque, String marca,
                       TipoPropulsao tipoPropulsao, String modelo, Double velocidadeEscopeta, Double alcanceEfetivo) {
        super(nome, descricao, preco, estoque, marca);
        this.tipoPropulsao = tipoPropulsao;
        this.modelo = modelo;
        this.velocidadeEscopeta = velocidadeEscopeta;
        this.alcanceEfetivo = alcanceEfetivo;
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
}

