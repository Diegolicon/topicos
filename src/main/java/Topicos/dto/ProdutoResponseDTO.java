package Topicos.dto;

public class ProdutoResponseDTO {
    public Long id;
    public String nome;
    public String descricao;
    public Double preco;
    public Integer estoque;
    public String marca;
    public Long criadoEm;
    public Long atualizadoEm;

    public ProdutoResponseDTO() {
    }

    public ProdutoResponseDTO(Long id, String nome, String descricao, Double preco, Integer estoque, 
                             String marca, Long criadoEm, Long atualizadoEm) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.marca = marca;
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

