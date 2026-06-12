package Topicos.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import Topicos.dto.EnderecoRequestDTO;
import Topicos.dto.EnderecoResponseDTO;
import Topicos.dto.UsuarioRequestDTO;
import Topicos.dto.UsuarioResponseDTO;
import Topicos.model.Endereco;
import Topicos.model.Usuario;
import Topicos.repository.UsuarioRepository;
import Topicos.service.UsuarioService;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    Mailer mailer;

    @Override
    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario(
                dto.getNome(),
                dto.getEmail(),
                dto.getTelefone(),
                dto.getSenha(),
                toEndereco(dto.getEndereco()),
                "USER"
        );
        usuarioRepository.persist(usuario);
        return toResponseDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new EntityNotFoundException("Usuario nao encontrado com ID: " + id);
        }
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        if (dto.getEndereco() != null) {
            usuario.setEndereco(toEndereco(dto.getEndereco()));
        }
        usuarioRepository.persist(usuario);
        return toResponseDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO obterPorId(Long id) {
        Usuario entity = Usuario.find("id = ?1 and ativo = true", id).firstResult();
        if (entity == null) {
            throw new NotFoundException("Usuario nao encontrado ou inativo com ID: " + id);
        }
        return toResponseDTO(entity);
    }

    @Override
    public List<UsuarioResponseDTO> obterTodos() {
        return usuarioRepository.list("ativo", true).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResponseDTO> buscarPorNome(String nome) {
        return usuarioRepository.findByNome(nome).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(Long id) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new EntityNotFoundException("Usuario nao encontrado com ID: " + id);
        }
        usuario.setAtivo(false);
        usuarioRepository.persist(usuario);
    }

    @Override
    public UsuarioResponseDTO alterarSenha(Long id, String senhaAtual, String novaSenha) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new EntityNotFoundException("Usuario nao encontrado com ID: " + id);
        }

        if (senhaAtual == null || senhaAtual.trim().isEmpty()) {
            throw new BadRequestException("Senha atual e obrigatoria");
        }

        if (novaSenha == null || novaSenha.trim().isEmpty()) {
            throw new BadRequestException("Nova senha nao pode estar vazia");
        }

        if (!usuario.getSenha().equals(senhaAtual)) {
            throw new BadRequestException("Senha atual incorreta");
        }

        usuario.setSenha(novaSenha.trim());
        usuarioRepository.persist(usuario);
        return toResponseDTO(usuario);
    }

    @Override
    public String enviarRecuperacaoSenha(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new BadRequestException("Email e obrigatorio");
        }

        String emailLimpo = email.trim();
        Usuario usuario = Usuario.find("email", emailLimpo).firstResult();
        if (usuario == null) {
            throw new NotFoundException("Usuario nao encontrado.");
        }

        String token = UUID.randomUUID().toString();
        usuario.setTokenRecuperacao(token);
        usuario.setDataExpiracaoToken(LocalDateTime.now().plusMinutes(15));
        usuarioRepository.persist(usuario);

        String link = "http://localhost:8080/api/usuarios/alterar-senha?token=" + token;
        String mensagem = "Use este token para recuperar sua senha: " + token
                + "\n\nEnvie uma requisicao POST para " + link
                + " informando a novaSenha.";

        mailer.send(Mail.withText(emailLimpo, "Recuperacao de Senha", mensagem));
        return token;
    }

    @Override
    public void recuperarSenha(String token, String novaSenha) {
        if (novaSenha == null || novaSenha.trim().isEmpty()) {
            throw new BadRequestException("Nova senha nao pode estar vazia");
        }

        if (token == null || token.trim().isEmpty()) {
            throw new BadRequestException("Token de recuperacao e obrigatorio");
        }

        Usuario usuario = Usuario.find("tokenRecuperacao = ?1", token.trim()).firstResult();
        if (usuario == null) {
            throw new BadRequestException("Token invalido ou nao encontrado");
        }

        if (usuario.getDataExpiracaoToken() == null
                || usuario.getDataExpiracaoToken().isBefore(LocalDateTime.now())) {
            usuario.setTokenRecuperacao(null);
            usuario.setDataExpiracaoToken(null);
            usuarioRepository.persist(usuario);
            throw new BadRequestException("Este token de recuperacao ja expirou");
        }

        usuario.setSenha(novaSenha.trim());
        usuario.setTokenRecuperacao(null);
        usuario.setDataExpiracaoToken(null);
        usuarioRepository.persist(usuario);
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.id,
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                toResponseEnderecoDTO(usuario.getEndereco()),
                usuario.getCriadoEm(),
                usuario.getAtualizadoEm()
        );
    }

    private Endereco toEndereco(EnderecoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Endereco(
                dto.getRua(),
                dto.getNumero(),
                dto.getCidade(),
                dto.getEstado(),
                dto.getCep(),
                dto.getComplemento()
        );
    }

    private EnderecoResponseDTO toResponseEnderecoDTO(Endereco endereco) {
        if (endereco == null) {
            return null;
        }
        return new EnderecoResponseDTO(
                endereco.id,
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep(),
                endereco.getComplemento(),
                endereco.getCriadoEm(),
                endereco.getAtualizadoEm()
        );
    }
}
