package Topicos.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import Topicos.dto.EnderecoRequestDTO;
import Topicos.dto.EnderecoResponseDTO;
import Topicos.dto.UsuarioRequestDTO;
import Topicos.dto.UsuarioResponseDTO;
import Topicos.model.Endereco;
import Topicos.model.Usuario;
import Topicos.repository.UsuarioRepository;
import Topicos.service.UsuarioService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Override
    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario(dto.getNome(), dto.getEmail(), dto.getTelefone(), dto.getSenha(), toEndereco(dto.getEndereco()));
        usuarioRepository.persist(usuario);
        return toResponseDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new EntityNotFoundException("Usuário não encontrado com ID: " + id);
        }
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        if (dto.getEndereco() != null) {
            usuario.setEndereco(toEndereco(dto.getEndereco()));
        }
        usuarioRepository.persist(usuario);
        return toResponseDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO obterPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new EntityNotFoundException("Usuário não encontrado com ID: " + id);
        }
        return toResponseDTO(usuario);
    }

    @Override
    public List<UsuarioResponseDTO> obterTodos() {
        return usuarioRepository.listAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResponseDTO> buscarPorNome(String nome) {
        return usuarioRepository.findByNome(nome).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(Long id) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new EntityNotFoundException("Usuário não encontrado com ID: " + id);
        }
        usuarioRepository.delete(usuario);
    }

    @Override
    public UsuarioResponseDTO alterarSenha(Long id, String senhaAtual, String novaSenha) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new EntityNotFoundException("Usuário não encontrado com ID: " + id);
        }
        
        // Validar senha atual (em produção, usar BCrypt ou similar)
        if (!usuario.getSenha().equals(senhaAtual)) {
            throw new RuntimeException("Senha atual incorreta");
        }
        
        if (novaSenha == null || novaSenha.trim().isEmpty()) {
            throw new RuntimeException("Nova senha não pode estar vazia");
        }
        
        usuario.setSenha(novaSenha);
        usuarioRepository.persist(usuario);
        return toResponseDTO(usuario);
    }

    @Override
    public void enviarRecuperacaoSenha(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            // Por segurança, não informar se o email existe ou não
            throw new RuntimeException("Email não encontrado ou inválido");
        }
        
        // Implementar lógica para enviar email com token
        // Exemplo: gerar token, salvar em banco/cache com expiração, enviar email
        System.out.println("Email de recuperação enviado para: " + email);
    }

    @Override
    public void recuperarSenha(String token, String novaSenha) {
        // Implementar lógica para validar token e recuperar usuário
        // Exemplo: buscar token no cache/banco, validar expiração, buscar usuário
        if (novaSenha == null || novaSenha.trim().isEmpty()) {
            throw new RuntimeException("Nova senha não pode estar vazia");
        }
        
        // Placeholder - substituir com lógica real
        throw new RuntimeException("Token inválido ou expirado");
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.id,
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                toResponseEnderecoDTO(usuario.getEndereco()),
                usuario.getCriadoEm(),
                usuario.getAtualizadoEm()
        );
    }

    private Endereco toEndereco(EnderecoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Endereco(
                dto.getRua(),
                dto.getNumero(),
                dto.getCidade(),
                dto.getEstado(),
                dto.getCep(),
                dto.getComplemento()
        );
    }

    private EnderecoResponseDTO toResponseEnderecoDTO(Endereco endereco) {
        if (endereco == null) {
            return null;
        }
        return new EnderecoResponseDTO(
                endereco.id,
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep(),
                endereco.getComplemento(),
                endereco.getCriadoEm(),
                endereco.getAtualizadoEm()
        );
    }
}

