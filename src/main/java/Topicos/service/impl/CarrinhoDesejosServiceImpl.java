package Topicos.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import Topicos.dto.CarrinhoDesejosResponseDTO;
import Topicos.dto.ProdutoSimplicadoResponseDTO;
import Topicos.dto.UsuarioSimplicadoResponseDTO;
import Topicos.model.CarrinhoDesejos;
import Topicos.model.Produto;
import Topicos.model.Usuario;
import Topicos.repository.CarrinhoDesejosRepository;
import Topicos.repository.ProdutoRepository;
import Topicos.repository.UsuarioRepository;
import Topicos.service.CarrinhoDesejosService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class CarrinhoDesejosServiceImpl implements CarrinhoDesejosService {

    @Inject
    CarrinhoDesejosRepository carrinhoDesejosRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    ProdutoRepository produtoRepository;

    @Override
    public CarrinhoDesejosResponseDTO adicionar(Long usuarioId, Long produtoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId);
        if (usuario == null || !Boolean.TRUE.equals(usuario.getAtivo())) {
            throw new EntityNotFoundException("Usuario nao encontrado ou inativo com ID: " + usuarioId);
        }

        Produto produto = produtoRepository.findById(produtoId);
        if (produto == null || !Boolean.TRUE.equals(produto.getAtivo())) {
            throw new EntityNotFoundException("Produto nao encontrado ou inativo com ID: " + produtoId);
        }

        CarrinhoDesejos existente = carrinhoDesejosRepository
                .findByUsuarioAndProduto(usuarioId, produtoId)
                .orElse(null);

        if (existente != null) {
            existente.setAtivo(true);
            carrinhoDesejosRepository.persist(existente);
            return toResponseDTO(existente);
        }

        CarrinhoDesejos item = new CarrinhoDesejos(usuario, produto);
        carrinhoDesejosRepository.persist(item);
        return toResponseDTO(item);
    }

    @Override
    public List<CarrinhoDesejosResponseDTO> obterPorUsuario(Long usuarioId) {
        return carrinhoDesejosRepository.findByUsuario(usuarioId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void remover(Long id) {
        CarrinhoDesejos item = carrinhoDesejosRepository.findById(id);
        if (item == null || !Boolean.TRUE.equals(item.getAtivo())) {
            throw new EntityNotFoundException("Item do carrinho de desejos nao encontrado com ID: " + id);
        }
        item.setAtivo(false);
        carrinhoDesejosRepository.persist(item);
    }

    @Override
    public void removerPorUsuarioEProduto(Long usuarioId, Long produtoId) {
        CarrinhoDesejos item = carrinhoDesejosRepository
                .findByUsuarioAndProduto(usuarioId, produtoId)
                .orElseThrow(() -> new EntityNotFoundException("Produto nao encontrado no carrinho de desejos"));
        item.setAtivo(false);
        carrinhoDesejosRepository.persist(item);
    }

    @Override
    public void limpar(Long usuarioId) {
        carrinhoDesejosRepository.findByUsuario(usuarioId).forEach(item -> {
            item.setAtivo(false);
            carrinhoDesejosRepository.persist(item);
        });
    }

    private CarrinhoDesejosResponseDTO toResponseDTO(CarrinhoDesejos item) {
        Usuario usuario = item.getUsuario();
        Produto produto = item.getProduto();

        return new CarrinhoDesejosResponseDTO(
                item.id,
                new UsuarioSimplicadoResponseDTO(
                        usuario.id,
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getTelefone()),
                new ProdutoSimplicadoResponseDTO(
                        produto.id,
                        produto.getNome(),
                        produto.getPreco(),
                        produto.getEstoque(),
                        produto.getMarca()),
                item.getCriadoEm(),
                item.getAtualizadoEm()
        );
    }
}
