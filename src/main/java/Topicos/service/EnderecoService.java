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
    EnderecoResponseDTO criarParaUsuario(String emailUsuario, EnderecoRequestDTO dto);
    List<EnderecoResponseDTO> obterPorUsuario(String emailUsuario);
    EnderecoResponseDTO atualizarDoUsuario(String emailUsuario, Long enderecoId, EnderecoRequestDTO dto);
    void deletarDoUsuario(String emailUsuario, Long enderecoId);
    void deletar(Long id);
}

