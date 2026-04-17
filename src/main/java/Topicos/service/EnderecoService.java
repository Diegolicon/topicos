package Topicos.service;

import java.util.List;

import Topicos.dto.EnderecoRequestDTO;
import Topicos.dto.EnderecoResponseDTO;

public interface EnderecoService {
    EnderecoResponseDTO criar(EnderecoRequestDTO dto);
    EnderecoResponseDTO atualizar(Long id, EnderecoRequestDTO dto);
    EnderecoResponseDTO obterPorId(Long id);
    List<EnderecoResponseDTO> obterTodos();
    List<EnderecoResponseDTO> buscarPorCep(String cep);
    void deletar(Long id);
}

