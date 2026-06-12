package Topicos.dto;

public class RecuperarSenhaRequestDTO {
    public String token;
    public String novaSenha;

    public RecuperarSenhaRequestDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}
