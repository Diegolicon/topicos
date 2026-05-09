package Topicos.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioRequestDTO {
    @NotBlank(message = "Nome é obrigatório")
    public String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    public String email;

    @NotBlank(message = "Telefone é obrigatório")
    public String telefone;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 3, message = "A senha deve ter pelo menos 3 caracteres")
    public String senha; // ADICIONADO

    @NotNull(message = "Endereço é obrigatório")
    @Valid
    public EnderecoRequestDTO endereco;

    public UsuarioRequestDTO() {
    }

    public UsuarioRequestDTO(String nome, String email, String telefone, String senha, EnderecoRequestDTO endereco) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha; // ADICIONADO
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() { // ADICIONADO
        return senha;
    }

    public void setSenha(String senha) { // ADICIONADO
        this.senha = senha;
    }

    public EnderecoRequestDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoRequestDTO endereco) {
        this.endereco = endereco;
    }
}