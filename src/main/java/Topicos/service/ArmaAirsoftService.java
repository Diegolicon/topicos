package Topicos.service;

import Topicos.dto.ArmaAirsoftRequestDTO;
import Topicos.dto.ArmaAirsoftResponseDTO;
import Topicos.model.ArmaAirsoft;
import java.util.List;

public interface ArmaAirsoftService {
    ArmaAirsoftResponseDTO criar(ArmaAirsoftRequestDTO dto);
    ArmaAirsoftResponseDTO atualizar(Long id, ArmaAirsoftRequestDTO dto);
    ArmaAirsoftResponseDTO obterPorId(Long id);
    List<ArmaAirsoftResponseDTO> obterTodos();
    List<ArmaAirsoftResponseDTO> buscarPorNome(String nome);
    List<ArmaAirsoftResponseDTO> buscarPorModelo(String modelo);
    void deletar(Long id);
}

