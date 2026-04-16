package Topicos.dto.airsoft;

import Topicos.dto.UsuarioSimplicadoResponseDTO;
import Topicos.model.airsoft.Venda;
import java.util.List;

public class VendaRequestDTO {
    public Long usuarioId;
    public List<ItemVendaRequestDTO> itens;
    public String observacoes;

    public VendaRequestDTO() {
    }

    public VendaRequestDTO(Long usuarioId, List<ItemVendaRequestDTO> itens, String observacoes) {
        this.usuarioId = usuarioId;
        this.itens = itens;
        this.observacoes = observacoes;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<ItemVendaRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendaRequestDTO> itens) {
        this.itens = itens;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
