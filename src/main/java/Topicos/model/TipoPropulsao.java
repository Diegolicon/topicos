package Topicos.model;

public enum TipoPropulsao {
    AEG("AEG - Automatic Electric Gun"),
    GBB("GBB - Gas Blow Back"),
    SPRING("SPRING - Mola");

    private final String descricao;

    TipoPropulsao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

