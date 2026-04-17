package Topicos.service;

import Topicos.dto.ProdutoRequestDTO;
import Topicos.dto.ProdutoResponseDTO;
import Topicos.model.Produto;
import java.util.List;

public interface ProdutoService {
    ProdutoResponseDTO criar(ProdutoRequestDTO dto);
    ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto);
    ProdutoResponseDTO obterPorId(Long id);
    List<ProdutoResponseDTO> obterTodos();
    List<ProdutoResponseDTO> buscarPorNome(String nome);
    List<ProdutoResponseDTO> obterComEstoque();
    void deletar(Long id);
}

