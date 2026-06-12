package Topicos.service;

import java.util.List;

import Topicos.dto.CarrinhoDesejosResponseDTO;

public interface CarrinhoDesejosService {
    CarrinhoDesejosResponseDTO adicionar(Long usuarioId, Long produtoId);
    List<CarrinhoDesejosResponseDTO> obterPorUsuario(Long usuarioId);
    void remover(Long id);
    void removerPorUsuarioEProduto(Long usuarioId, Long produtoId);
    void limpar(Long usuarioId);
}
