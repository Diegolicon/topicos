package Topicos.dto;

public class AlterarSenhaRequestDTO {
    public String senhaAtual;
    public String novaSenha;

    public AlterarSenhaRequestDTO() {
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}
