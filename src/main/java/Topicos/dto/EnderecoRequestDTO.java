package Topicos.dto;

import jakarta.validation.constraints.NotBlank;

public class EnderecoRequestDTO {
    @NotBlank(message = "Rua é obrigatória")
    public String rua;
    @NotBlank(message = "Número é obrigatório")
    public String numero;
    @NotBlank(message = "Cidade é obrigatória")
    public String cidade;
    @NotBlank(message = "Estado é obrigatório")
    public String estado;
    @NotBlank(message = "CEP é obrigatório")
    public String cep;
    public String complemento;

    public EnderecoRequestDTO() {
    }

    public EnderecoRequestDTO(String rua, String numero, String cidade, String estado, String cep, String complemento) {
        this.rua = rua;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.complemento = complemento;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}

