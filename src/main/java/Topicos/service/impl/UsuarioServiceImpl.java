package Topicos.service.impl;

import java.util.List;
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

@ApplicationScoped
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

