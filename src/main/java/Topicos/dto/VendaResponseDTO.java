package Topicos.dto;

import Topicos.dto.UsuarioSimplicadoResponseDTO;
import Topicos.model.Venda;
import java.util.List;

public class VendaResponseDTO {
    public Long id;
    public UsuarioSimplicadoResponseDTO usuario;
    public List<ItemVendaResponseDTO> itens;
    public Double totalVenda;
    public Venda.StatusVenda status;
    public String observacoes;
    public Long criadoEm;
    public Long atualizadoEm;

    public VendaResponseDTO() {
    }

    public VendaResponseDTO(Long id, UsuarioSimplicadoResponseDTO usuario, List<ItemVendaResponseDTO> itens,
                            Double totalVenda, Venda.StatusVenda status, String observacoes, 
                            Long criadoEm, Long atualizadoEm) {
        this.id = id;
        this.usuario = usuario;
        this.itens = itens;
        this.totalVenda = totalVenda;
        this.status = status;
        this.observacoes = observacoes;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioSimplicadoResponseDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioSimplicadoResponseDTO usuario) {
        this.usuario = usuario;
    }

    public List<ItemVendaResponseDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendaResponseDTO> itens) {
        this.itens = itens;
    }

    public Double getTotalVenda() {
        return totalVenda;
    }

    public void setTotalVenda(Double totalVenda) {
        this.totalVenda = totalVenda;
    }

    public Venda.StatusVenda getStatus() {
        return status;
    }

    public void setStatus(Venda.StatusVenda status) {
        this.status = status;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Long getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Long criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Long getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(Long atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}

