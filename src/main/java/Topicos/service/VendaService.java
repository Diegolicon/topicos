package Topicos.service;

import Topicos.dto.VendaRequestDTO;
import Topicos.dto.VendaResponseDTO;
import Topicos.model.Venda;
import java.util.List;

public interface VendaService {
    VendaResponseDTO criar(VendaRequestDTO dto);
    VendaResponseDTO atualizar(Long id, VendaRequestDTO dto);
    VendaResponseDTO obterPorId(Long id);
    List<VendaResponseDTO> obterTodos();
    List<VendaResponseDTO> obterPorUsuario(Long usuarioId);
    List<VendaResponseDTO> obterPorStatus(Venda.StatusVenda status);
    VendaResponseDTO atualizarStatus(Long id, Venda.StatusVenda novoStatus);
    void deletar(Long id);
}

