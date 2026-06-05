package Topicos.service;

import Topicos.dto.UsuarioRequestDTO;
import Topicos.dto.UsuarioResponseDTO;
import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO criar(UsuarioRequestDTO dto);
    UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto);
    UsuarioResponseDTO obterPorId(Long id);
    List<UsuarioResponseDTO> obterTodos();
    List<UsuarioResponseDTO> buscarPorNome(String nome);
    void deletar(Long id);
    UsuarioResponseDTO alterarSenha(Long id, String senhaAtual, String novaSenha);
    void enviarRecuperacaoSenha(String email);
    void recuperarSenha(String token, String novaSenha);
}

