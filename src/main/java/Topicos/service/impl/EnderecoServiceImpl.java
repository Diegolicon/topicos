package Topicos.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import Topicos.dto.EnderecoRequestDTO;
import Topicos.dto.EnderecoResponseDTO;
import Topicos.model.Endereco;
import Topicos.model.Usuario;
import Topicos.repository.EnderecoRepository;
import Topicos.repository.UsuarioRepository;
import Topicos.service.EnderecoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class EnderecoServiceImpl implements EnderecoService {

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Override
    public EnderecoResponseDTO criar(EnderecoRequestDTO dto) {
        Endereco endereco = new Endereco(
                dto.getRua(),
                dto.getNumero(),
                dto.getCidade(),
                dto.getEstado(),
                dto.getCep(),
                dto.getComplemento()
        );
        enderecoRepository.persist(endereco);
        return toResponseDTO(endereco);
    }

    @Override
    public EnderecoResponseDTO atualizar(Long id, EnderecoRequestDTO dto) {
        Endereco endereco = enderecoRepository.findById(id);
        if (endereco == null) {
            throw new EntityNotFoundException("Endereço não encontrado com ID: " + id);
        }
        endereco.setRua(dto.getRua());
        endereco.setNumero(dto.getNumero());
        endereco.setCidade(dto.getCidade());
        endereco.setEstado(dto.getEstado());
        endereco.setCep(dto.getCep());
        endereco.setComplemento(dto.getComplemento());
        enderecoRepository.persist(endereco);
        return toResponseDTO(endereco);
    }

    @Override
    public EnderecoResponseDTO obterPorId(Long id) {
        Endereco entity = Endereco.find("id = ?1 and ativo = true", id).firstResult();
        if (entity == null) {
            throw new EntityNotFoundException("Endereço não encontrado ou inativo com ID: " + id);
        }
        return toResponseDTO(entity);
    }

    @Override
    public List<EnderecoResponseDTO> obterTodos() {
        return enderecoRepository.list("ativo", true).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EnderecoResponseDTO> buscarPorCep(String cep) {
        return enderecoRepository.findByCep(cep).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EnderecoResponseDTO criarParaUsuario(String emailUsuario, EnderecoRequestDTO dto) {
        Usuario usuario = obterUsuarioPorEmail(emailUsuario);
        Endereco endereco = new Endereco(
                dto.getRua(),
                dto.getNumero(),
                dto.getCidade(),
                dto.getEstado(),
                dto.getCep(),
                dto.getComplemento()
        );
        endereco.setUsuario(usuario);
        enderecoRepository.persist(endereco);
        return toResponseDTO(endereco);
    }

    @Override
    public List<EnderecoResponseDTO> obterPorUsuario(String emailUsuario) {
        Usuario usuario = obterUsuarioPorEmail(emailUsuario);
        return enderecoRepository.findByUsuario(usuario.id).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EnderecoResponseDTO atualizarDoUsuario(String emailUsuario, Long enderecoId, EnderecoRequestDTO dto) {
        Usuario usuario = obterUsuarioPorEmail(emailUsuario);
        Endereco endereco = obterEnderecoDoUsuario(usuario.id, enderecoId);
        endereco.setRua(dto.getRua());
        endereco.setNumero(dto.getNumero());
        endereco.setCidade(dto.getCidade());
        endereco.setEstado(dto.getEstado());
        endereco.setCep(dto.getCep());
        endereco.setComplemento(dto.getComplemento());
        enderecoRepository.persist(endereco);
        return toResponseDTO(endereco);
    }

    @Override
    public void deletarDoUsuario(String emailUsuario, Long enderecoId) {
        Usuario usuario = obterUsuarioPorEmail(emailUsuario);
        Endereco endereco = obterEnderecoDoUsuario(usuario.id, enderecoId);
        endereco.setAtivo(false);
        enderecoRepository.persist(endereco);
    }

    @Override
    public void deletar(Long id) {
        Endereco endereco = enderecoRepository.findById(id);
        if (endereco == null) {
            throw new EntityNotFoundException("Endereço não encontrado com ID: " + id);
        }
        endereco.setAtivo(false);
        enderecoRepository.persist(endereco);
    }

    private EnderecoResponseDTO toResponseDTO(Endereco endereco) {
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

    private Usuario obterUsuarioPorEmail(String emailUsuario) {
        return usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuario autenticado nao encontrado"));
    }

    private Endereco obterEnderecoDoUsuario(Long usuarioId, Long enderecoId) {
        Endereco endereco = enderecoRepository.findById(enderecoId);
        if (endereco == null || !Boolean.TRUE.equals(endereco.getAtivo())) {
            throw new EntityNotFoundException("Endereco nao encontrado com ID: " + enderecoId);
        }
        if (endereco.getUsuario() == null || !endereco.getUsuario().id.equals(usuarioId)) {
            throw new EntityNotFoundException("Endereco nao encontrado para o usuario autenticado");
        }
        return endereco;
    }
}

