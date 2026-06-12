package Topicos.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import Topicos.dto.CarrinhoDesejosResponseDTO;
import Topicos.dto.EnderecoResponseDTO;
import Topicos.dto.ItemVendaResponseDTO;
import Topicos.dto.ProdutoSimplicadoResponseDTO;
import Topicos.dto.UsuarioSimplicadoResponseDTO;
import Topicos.dto.VendaResponseDTO;
import Topicos.model.CarrinhoDesejos;
import Topicos.model.Endereco;
import Topicos.model.ItemVenda;
import Topicos.model.Produto;
import Topicos.model.Usuario;
import Topicos.model.Venda;
import Topicos.repository.CarrinhoDesejosRepository;
import Topicos.repository.EnderecoRepository;
import Topicos.repository.ProdutoRepository;
import Topicos.repository.UsuarioRepository;
import Topicos.repository.VendaRepository;
import Topicos.service.CarrinhoDesejosService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;

@ApplicationScoped
@Transactional
public class CarrinhoDesejosServiceImpl implements CarrinhoDesejosService {

    private static final String CHAVE_PIX = "63984352575";

    @Inject
    CarrinhoDesejosRepository carrinhoDesejosRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    VendaRepository vendaRepository;

    @Override
    public CarrinhoDesejosResponseDTO adicionar(String emailUsuario, Long produtoId, Integer quantidade) {
        Usuario usuario = obterUsuarioPorEmail(emailUsuario);
        Produto produto = obterProdutoAtivo(produtoId);
        int quantidadeFinal = validarQuantidade(quantidade);

        CarrinhoDesejos item = carrinhoDesejosRepository
                .findByUsuarioAndProduto(usuario.id, produtoId)
                .orElse(null);

        if (item != null) {
            item.setQuantidade(item.getQuantidade() + quantidadeFinal);
            item.setAtivo(true);
            carrinhoDesejosRepository.persist(item);
            return toResponseDTO(item);
        }

        item = new CarrinhoDesejos(usuario, produto, quantidadeFinal);
        carrinhoDesejosRepository.persist(item);
        return toResponseDTO(item);
    }

    @Override
    public CarrinhoDesejosResponseDTO atualizarQuantidade(String emailUsuario, Long produtoId, Integer quantidade) {
        Usuario usuario = obterUsuarioPorEmail(emailUsuario);
        CarrinhoDesejos item = obterItemDoUsuario(usuario.id, produtoId);
        item.setQuantidade(validarQuantidade(quantidade));
        carrinhoDesejosRepository.persist(item);
        return toResponseDTO(item);
    }

    @Override
    public List<CarrinhoDesejosResponseDTO> obterMeuCarrinho(String emailUsuario) {
        Usuario usuario = obterUsuarioPorEmail(emailUsuario);
        return carrinhoDesejosRepository.findByUsuario(usuario.id).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void remover(String emailUsuario, Long produtoId) {
        Usuario usuario = obterUsuarioPorEmail(emailUsuario);
        CarrinhoDesejos item = obterItemDoUsuario(usuario.id, produtoId);
        item.setAtivo(false);
        carrinhoDesejosRepository.persist(item);
    }

    @Override
    public void limpar(String emailUsuario) {
        Usuario usuario = obterUsuarioPorEmail(emailUsuario);
        carrinhoDesejosRepository.findByUsuario(usuario.id).forEach(item -> {
            item.setAtivo(false);
            carrinhoDesejosRepository.persist(item);
        });
    }

    @Override
    public VendaResponseDTO finalizarCompra(String emailUsuario, Long enderecoId, String observacoes) {
        Usuario usuario = obterUsuarioPorEmail(emailUsuario);
        List<CarrinhoDesejos> itensCarrinho = carrinhoDesejosRepository.findByUsuario(usuario.id);
        if (itensCarrinho.isEmpty()) {
            throw new BadRequestException("Carrinho vazio");
        }

        Endereco endereco = null;
        if (enderecoId != null) {
            endereco = enderecoRepository.findById(enderecoId);
            if (endereco == null || !Boolean.TRUE.equals(endereco.getAtivo())) {
                throw new EntityNotFoundException("Endereco nao encontrado com ID: " + enderecoId);
            }
            if (endereco.getUsuario() != null && !endereco.getUsuario().id.equals(usuario.id)) {
                throw new BadRequestException("Endereco nao pertence ao usuario autenticado");
            }
        }

        Venda venda = new Venda(usuario);
        venda.setEnderecoEntrega(endereco);
        venda.setFormaPagamento(Venda.FormaPagamento.PIX);
        venda.setChavePix(CHAVE_PIX);
        venda.setObservacoes(observacoes);

        for (CarrinhoDesejos itemCarrinho : itensCarrinho) {
            Produto produto = itemCarrinho.getProduto();
            ItemVenda itemVenda = new ItemVenda(
                    venda,
                    produto,
                    itemCarrinho.getQuantidade(),
                    produto.getPreco());
            venda.adicionarItem(itemVenda);
            itemCarrinho.setAtivo(false);
            carrinhoDesejosRepository.persist(itemCarrinho);
        }

        vendaRepository.persist(venda);
        return vendaToResponseDTO(venda);
    }

    private Usuario obterUsuarioPorEmail(String emailUsuario) {
        return usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuario autenticado nao encontrado"));
    }

    private Produto obterProdutoAtivo(Long produtoId) {
        Produto produto = produtoRepository.findById(produtoId);
        if (produto == null || !Boolean.TRUE.equals(produto.getAtivo())) {
            throw new EntityNotFoundException("Produto nao encontrado ou inativo com ID: " + produtoId);
        }
        return produto;
    }

    private CarrinhoDesejos obterItemDoUsuario(Long usuarioId, Long produtoId) {
        CarrinhoDesejos item = carrinhoDesejosRepository
                .findByUsuarioAndProduto(usuarioId, produtoId)
                .orElseThrow(() -> new EntityNotFoundException("Produto nao encontrado no carrinho"));
        if (!Boolean.TRUE.equals(item.getAtivo())) {
            throw new EntityNotFoundException("Produto nao encontrado no carrinho");
        }
        return item;
    }

    private int validarQuantidade(Integer quantidade) {
        if (quantidade == null || quantidade < 1) {
            throw new BadRequestException("Quantidade deve ser maior que 0");
        }
        return quantidade;
    }

    private CarrinhoDesejosResponseDTO toResponseDTO(CarrinhoDesejos item) {
        Usuario usuario = item.getUsuario();
        Produto produto = item.getProduto();
        Double subtotal = produto.getPreco() * item.getQuantidade();

        return new CarrinhoDesejosResponseDTO(
                item.id,
                new UsuarioSimplicadoResponseDTO(
                        usuario.id,
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getTelefone()),
                produtoToSimplicadoDTO(produto),
                item.getQuantidade(),
                subtotal,
                item.getCriadoEm(),
                item.getAtualizadoEm()
        );
    }

    private VendaResponseDTO vendaToResponseDTO(Venda venda) {
        List<ItemVendaResponseDTO> itens = venda.getItens().stream()
                .map(item -> new ItemVendaResponseDTO(
                        item.id,
                        item.getVenda().id,
                        produtoToSimplicadoDTO(item.getProduto()),
                        item.getQuantidade(),
                        item.getPrecoUnitario(),
                        item.getSubtotal(),
                        item.getCriadoEm(),
                        item.getAtualizadoEm()))
                .collect(Collectors.toList());

        VendaResponseDTO response = new VendaResponseDTO(
                venda.id,
                new UsuarioSimplicadoResponseDTO(
                        venda.getUsuario().id,
                        venda.getUsuario().getNome(),
                        venda.getUsuario().getEmail(),
                        venda.getUsuario().getTelefone()),
                itens,
                venda.getTotalVenda(),
                venda.getStatus(),
                venda.getObservacoes(),
                venda.getCriadoEm(),
                venda.getAtualizadoEm()
        );
        response.setEnderecoEntrega(enderecoToResponseDTO(venda.getEnderecoEntrega()));
        response.setFormaPagamento(venda.getFormaPagamento());
        response.setChavePix(venda.getChavePix());
        return response;
    }

    private ProdutoSimplicadoResponseDTO produtoToSimplicadoDTO(Produto produto) {
        return new ProdutoSimplicadoResponseDTO(
                produto.id,
                produto.getNome(),
                produto.getPreco(),
                produto.getEstoque(),
                produto.getMarca());
    }

    private EnderecoResponseDTO enderecoToResponseDTO(Endereco endereco) {
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
                endereco.getAtualizadoEm());
    }
}
