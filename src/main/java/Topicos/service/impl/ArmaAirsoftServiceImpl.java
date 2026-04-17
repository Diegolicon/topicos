package Topicos.service.impl;

import Topicos.dto.ArmaAirsoftRequestDTO;
import Topicos.dto.ArmaAirsoftResponseDTO;
import Topicos.model.ArmaAirsoft;
import Topicos.repository.ArmaAirsoftRepository;
import Topicos.service.ArmaAirsoftService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ArmaAirsoftServiceImpl implements ArmaAirsoftService {

    @Inject
    ArmaAirsoftRepository armaAirsoftRepository;

    @Override
    public ArmaAirsoftResponseDTO criar(ArmaAirsoftRequestDTO dto) {
        ArmaAirsoft arma = new ArmaAirsoft(dto.getNome(), dto.getDescricao(), dto.getPreco(),
                                          dto.getEstoque(), dto.getMarca(), dto.getTipoPropulsao(),
                                          dto.getModelo(), dto.getVelocidadeEscopeta(), dto.getAlcanceEfetivo());
        armaAirsoftRepository.persist(arma);
        return toResponseDTO(arma);
    }

    @Override
    public ArmaAirsoftResponseDTO atualizar(Long id, ArmaAirsoftRequestDTO dto) {
        ArmaAirsoft arma = armaAirsoftRepository.findById(id);
        if (arma == null) {
            throw new EntityNotFoundException("Arma Airsoft não encontrada com ID: " + id);
        }
        arma.setNome(dto.getNome());
        arma.setDescricao(dto.getDescricao());
        arma.setPreco(dto.getPreco());
        arma.setEstoque(dto.getEstoque());
        arma.setMarca(dto.getMarca());
        arma.setTipoPropulsao(dto.getTipoPropulsao());
        arma.setModelo(dto.getModelo());
        arma.setVelocidadeEscopeta(dto.getVelocidadeEscopeta());
        arma.setAlcanceEfetivo(dto.getAlcanceEfetivo());
        armaAirsoftRepository.persist(arma);
        return toResponseDTO(arma);
    }

    @Override
    public ArmaAirsoftResponseDTO obterPorId(Long id) {
        ArmaAirsoft arma = armaAirsoftRepository.findById(id);
        if (arma == null) {
            throw new EntityNotFoundException("Arma Airsoft não encontrada com ID: " + id);
        }
        return toResponseDTO(arma);
    }

    @Override
    public List<ArmaAirsoftResponseDTO> obterTodos() {
        return armaAirsoftRepository.listAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArmaAirsoftResponseDTO> buscarPorNome(String nome) {
        return armaAirsoftRepository.findByNome(nome).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArmaAirsoftResponseDTO> buscarPorModelo(String modelo) {
        return armaAirsoftRepository.findByModelo(modelo).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(Long id) {
        ArmaAirsoft arma = armaAirsoftRepository.findById(id);
        if (arma == null) {
            throw new EntityNotFoundException("Arma Airsoft não encontrada com ID: " + id);
        }
        armaAirsoftRepository.delete(arma);
    }

    private ArmaAirsoftResponseDTO toResponseDTO(ArmaAirsoft arma) {
        return new ArmaAirsoftResponseDTO(
                arma.id,
                arma.getNome(),
                arma.getDescricao(),
                arma.getPreco(),
                arma.getEstoque(),
                arma.getMarca(),
                arma.getTipoPropulsao(),
                arma.getModelo(),
                arma.getVelocidadeEscopeta(),
                arma.getAlcanceEfetivo(),
                arma.getCriadoEm(),
                arma.getAtualizadoEm()
        );
    }
}

