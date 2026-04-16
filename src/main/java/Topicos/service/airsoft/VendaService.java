package Topicos.service.airsoft;

import Topicos.dto.airsoft.VendaRequestDTO;
import Topicos.dto.airsoft.VendaResponseDTO;
import Topicos.model.airsoft.Venda;
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
