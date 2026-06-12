package Topicos.dto;

public class CarrinhoDesejosResponseDTO {
    public Long id;
    public UsuarioSimplicadoResponseDTO usuario;
    public ProdutoSimplicadoResponseDTO produto;
    public Integer quantidade;
    public Double subtotal;
    public Long criadoEm;
    public Long atualizadoEm;

    public CarrinhoDesejosResponseDTO() {
    }

    public CarrinhoDesejosResponseDTO(Long id,
            UsuarioSimplicadoResponseDTO usuario,
            ProdutoSimplicadoResponseDTO produto,
            Integer quantidade,
            Double subtotal,
            Long criadoEm,
            Long atualizadoEm) {
        this.id = id;
        this.usuario = usuario;
        this.produto = produto;
        this.quantidade = quantidade;
        this.subtotal = subtotal;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }
}
