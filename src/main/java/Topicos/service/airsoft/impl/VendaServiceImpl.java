package Topicos.service.airsoft.impl;

import Topicos.dto.ProdutoSimplicadoResponseDTO;
import Topicos.dto.UsuarioSimplicadoResponseDTO;
import Topicos.dto.airsoft.ItemVendaRequestDTO;
import Topicos.dto.airsoft.ItemVendaResponseDTO;
import Topicos.dto.airsoft.VendaRequestDTO;
import Topicos.dto.airsoft.VendaResponseDTO;
import Topicos.model.Usuario;
import Topicos.model.airsoft.ItemVenda;
import Topicos.model.airsoft.Produto;
import Topicos.model.airsoft.Venda;
import Topicos.repository.UsuarioRepository;
import Topicos.repository.airsoft.ItemVendaRepository;
import Topicos.repository.airsoft.ProdutoRepository;
import Topicos.repository.airsoft.VendaRepository;
import Topicos.service.airsoft.VendaService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class VendaServiceImpl implements VendaService {

    @Inject
    VendaRepository vendaRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    ItemVendaRepository itemVendaRepository;

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
        Venda venda = vendaRepository.findById(id);
        if (venda == null) {
            throw new EntityNotFoundException("Venda não encontrada com ID: " + id);
        }
        return toResponseDTO(venda);
    }

    @Override
    public List<VendaResponseDTO> obterTodos() {
        return vendaRepository.listAll().stream()
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
        vendaRepository.delete(venda);
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
