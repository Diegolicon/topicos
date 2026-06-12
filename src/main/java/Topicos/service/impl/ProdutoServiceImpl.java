package Topicos.service.impl;

import Topicos.dto.ProdutoRequestDTO;
import Topicos.dto.ProdutoResponseDTO;
import Topicos.model.Endereco;
import Topicos.model.Produto;
import Topicos.repository.ProdutoRepository;
import Topicos.service.ProdutoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class ProdutoServiceImpl implements ProdutoService {

    @Inject
    ProdutoRepository produtoRepository;

    @Override
    public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {
        Produto produto = new Produto(dto.getNome(), dto.getDescricao(), dto.getPreco(), 
                                     dto.getEstoque(), dto.getMarca());
        produtoRepository.persist(produto);
        return toResponseDTO(produto);
    }

    @Override
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        Produto produto = produtoRepository.findById(id);
        if (produto == null) {
            throw new EntityNotFoundException("Produto não encontrado com ID: " + id);
        }
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setEstoque(dto.getEstoque());
        produto.setMarca(dto.getMarca());
        produtoRepository.persist(produto);
        return toResponseDTO(produto);
    }

    @Override
    public ProdutoResponseDTO obterPorId(Long id) {
        Produto entity = Produto.find("id = ?1 and ativo = true", id).firstResult();
        if (entity == null) {
            throw new EntityNotFoundException("Produto não encontrado ou inativo com ID: " + id);
        }
        return toResponseDTO(entity);
    }

    @Override
    public List<ProdutoResponseDTO> obterTodos() {
        return produtoRepository.list("ativo", true).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProdutoResponseDTO> buscarPorNome(String nome) {
        return produtoRepository.findByNome(nome).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProdutoResponseDTO> obterComEstoque() {
        return produtoRepository.findComEstoque().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(Long id) {
        Produto produto = produtoRepository.findById(id);
        if (produto == null) {
            throw new EntityNotFoundException("Produto não encontrado com ID: " + id);
        }
        produto.setAtivo(false);
        produtoRepository.persist(produto);
    }

    private ProdutoResponseDTO toResponseDTO(Produto produto) {
        return new ProdutoResponseDTO(
                produto.id,
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getEstoque(),
                produto.getMarca(),
                produto.getCriadoEm(),
                produto.getAtualizadoEm()
        );
    }
}

