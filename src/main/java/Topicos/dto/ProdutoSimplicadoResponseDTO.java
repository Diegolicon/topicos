package Topicos.dto;

public class ProdutoSimplicadoResponseDTO {
    public Long id;
    public String nome;
    public Double preco;
    public Integer estoque;
    public String marca;
    public String foto;
    public String categoria;

    public ProdutoSimplicadoResponseDTO() {
    }

    public ProdutoSimplicadoResponseDTO(Long id, String nome, Double preco, Integer estoque, String marca) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.marca = marca;
    }

    public ProdutoSimplicadoResponseDTO(Long id, String nome, Double preco, Integer estoque, String marca, String foto, String categoria) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.marca = marca;
        this.foto = foto;
        this.categoria = categoria;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}

