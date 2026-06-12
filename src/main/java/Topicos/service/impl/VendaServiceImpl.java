package Topicos.service.impl;

import Topicos.dto.ProdutoSimplicadoResponseDTO;
import Topicos.dto.UsuarioSimplicadoResponseDTO;
import Topicos.dto.ItemVendaRequestDTO;
import Topicos.dto.ItemVendaResponseDTO;
import Topicos.dto.VendaRequestDTO;
import Topicos.dto.VendaResponseDTO;
import Topicos.model.Usuario;
import Topicos.model.ArmaAirsoft;
import Topicos.model.ItemVenda;
import Topicos.model.Produto;
import Topicos.model.Venda;
import Topicos.repository.UsuarioRepository;
import Topicos.repository.ProdutoRepository;
import Topicos.repository.VendaRepository;
import Topicos.service.VendaService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class VendaServiceImpl implements VendaService {

    @Inject
    VendaRepository vendaRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    ProdutoRepository produtoRepository;

    @Override
    public VendaResponseDTO criar(VendaRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId());
        if (usuario == null) {
            throw new EntityNotFoundException("Usuário não encontrado com ID: " + dto.getUsuarioId());
        }

        Venda venda = new Venda(usuario);
        venda.setObservacoes(dto.getObservacoes());
        
        for (ItemVendaRequestDTO itemDTO : dto.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId());
            if (produto == null) {
                throw new EntityNotFoundException("Produto não encontrado com ID: " + itemDTO.getProdutoId());
            }
            ItemVenda item = new ItemVenda(venda, produto, itemDTO.getQuantidade(), itemDTO.getPrecoUnitario());
            venda.adicionarItem(item);
        }

        vendaRepository.persist(venda);
        return toResponseDTO(venda);
    }

    @Override
    public VendaResponseDTO atualizar(Long id, VendaRequestDTO dto) {
        Venda venda = vendaRepository.findById(id);
        if (venda == null) {
            throw new EntityNotFoundException("Venda não encontrada com ID: " + id);
        }

        venda.setObservacoes(dto.getObservacoes());
        venda.getItens().clear();

        for (ItemVendaRequestDTO itemDTO : dto.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId());
            if (produto == null) {
                throw new EntityNotFoundException("Produto não encontrado com ID: " + itemDTO.getProdutoId());
            }
            ItemVenda item = new ItemVenda(venda, produto, itemDTO.getQuantidade(), itemDTO.getPrecoUnitario());
            venda.adicionarItem(item);
        }

        vendaRepository.persist(venda);
        return toResponseDTO(venda);
    }

    @Override
    public VendaResponseDTO obterPorId(Long id) {
        Venda entity = Venda.find("id = ?1 and ativo = true", id).firstResult();
        if (entity == null) {
            throw new EntityNotFoundException("Venda não encontrada com ID: " + id);
        }
        return toResponseDTO(entity);
    }

    @Override
    public List<VendaResponseDTO> obterTodos() {
        return vendaRepository.list("ativo", true).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VendaResponseDTO> obterPorUsuario(Long usuarioId) {
        return vendaRepository.findByUsuario(usuarioId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VendaResponseDTO> obterPorStatus(Venda.StatusVenda status) {
        return vendaRepository.findByStatus(status).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VendaResponseDTO atualizarStatus(Long id, Venda.StatusVenda novoStatus) {
        Venda venda = vendaRepository.findById(id);
        if (venda == null) {
            throw new EntityNotFoundException("Venda não encontrada com ID: " + id);
        }
        venda.setStatus(novoStatus);
        vendaRepository.persist(venda);
        return toResponseDTO(venda);
    }

    @Override
    public void deletar(Long id) {
        Venda venda = vendaRepository.findById(id);
        if (venda == null) {
            throw new EntityNotFoundException("Venda não encontrada com ID: " + id);
        }
        venda.setAtivo(false);
        vendaRepository.persist(venda);
    }

    private VendaResponseDTO toResponseDTO(Venda venda) {
        UsuarioSimplicadoResponseDTO usuarioDTO = new UsuarioSimplicadoResponseDTO(
                venda.getUsuario().id,
                venda.getUsuario().getNome(),
                venda.getUsuario().getEmail(),
                venda.getUsuario().getTelefone()
        );

        List<ItemVendaResponseDTO> itensDTO = venda.getItens().stream()
                .map(this::itemToResponseDTO)
                .collect(Collectors.toList());

        return new VendaResponseDTO(
                venda.id,
                usuarioDTO,
                itensDTO,
                venda.getTotalVenda(),
                venda.getStatus(),
                venda.getObservacoes(),
                venda.getCriadoEm(),
                venda.getAtualizadoEm()
        );
    }

    private ItemVendaResponseDTO itemToResponseDTO(ItemVenda item) {
        ProdutoSimplicadoResponseDTO produtoDTO = new ProdutoSimplicadoResponseDTO(
                item.getProduto().id,
                item.getProduto().getNome(),
                item.getProduto().getPreco(),
                item.getProduto().getEstoque(),
                item.getProduto().getMarca()
        );

        return new ItemVendaResponseDTO(
                item.id,
                item.getVenda().id,
                produtoDTO,
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getSubtotal(),
                item.getCriadoEm(),
                item.getAtualizadoEm()
        );
    }
}

