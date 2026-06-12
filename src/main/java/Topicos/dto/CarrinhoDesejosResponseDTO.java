package Topicos.dto;

public class CarrinhoDesejosResponseDTO {
    public Long id;
    public UsuarioSimplicadoResponseDTO usuario;
    public ProdutoSimplicadoResponseDTO produto;
    public Long criadoEm;
    public Long atualizadoEm;

    public CarrinhoDesejosResponseDTO() {
    }

    public CarrinhoDesejosResponseDTO(Long id,
            UsuarioSimplicadoResponseDTO usuario,
            ProdutoSimplicadoResponseDTO produto,
            Long criadoEm,
            Long atualizadoEm) {
        this.id = id;
        this.usuario = usuario;
        this.produto = produto;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }
}
