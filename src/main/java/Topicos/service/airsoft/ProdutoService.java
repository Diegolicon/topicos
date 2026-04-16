package Topicos.service.airsoft;

import Topicos.dto.airsoft.ProdutoRequestDTO;
import Topicos.dto.airsoft.ProdutoResponseDTO;
import Topicos.model.airsoft.Produto;
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
