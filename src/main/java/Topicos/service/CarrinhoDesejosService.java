package Topicos.service;

import java.util.List;

import Topicos.dto.CarrinhoDesejosResponseDTO;
import Topicos.dto.VendaResponseDTO;

public interface CarrinhoDesejosService {
    CarrinhoDesejosResponseDTO adicionar(String emailUsuario, Long produtoId, Integer quantidade);
    CarrinhoDesejosResponseDTO atualizarQuantidade(String emailUsuario, Long produtoId, Integer quantidade);
    List<CarrinhoDesejosResponseDTO> obterMeuCarrinho(String emailUsuario);
    void remover(String emailUsuario, Long produtoId);
    void limpar(String emailUsuario);
    VendaResponseDTO finalizarCompra(String emailUsuario, Long enderecoId, String observacoes);
}
