package Topicos.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import Topicos.dto.EnderecoRequestDTO;
import Topicos.dto.EnderecoResponseDTO;
import Topicos.model.Endereco;
import Topicos.repository.EnderecoRepository;
import Topicos.service.EnderecoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {

    @Inject
    EnderecoRepository enderecoRepository;

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
        Endereco endereco = enderecoRepository.findById(id);
        if (endereco == null) {
            throw new EntityNotFoundException("Endereço não encontrado com ID: " + id);
        }
        return toResponseDTO(endereco);
    }

    @Override
    public List<EnderecoResponseDTO> obterTodos() {
        return enderecoRepository.listAll().stream()
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
    public void deletar(Long id) {
        Endereco endereco = enderecoRepository.findById(id);
        if (endereco == null) {
            throw new EntityNotFoundException("Endereço não encontrado com ID: " + id);
        }
        enderecoRepository.delete(endereco);
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
}

