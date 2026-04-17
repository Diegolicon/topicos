package Topicos.service;

import Topicos.dto.UsuarioRequestDTO;
import Topicos.dto.UsuarioResponseDTO;
import Topicos.model.Usuario;
import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO criar(UsuarioRequestDTO dto);
    UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto);
    UsuarioResponseDTO obterPorId(Long id);
    List<UsuarioResponseDTO> obterTodos();
    List<UsuarioResponseDTO> buscarPorNome(String nome);
    void deletar(Long id);
}

