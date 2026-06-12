package Topicos.resource;

import java.util.List;

import Topicos.dto.UsuarioRequestDTO;
import Topicos.dto.UsuarioResponseDTO;
import Topicos.service.UsuarioService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @POST
    @PermitAll
    public Response criar(@Valid UsuarioRequestDTO dto) {
        try {
            UsuarioResponseDTO response = usuarioService.criar(dto);
            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @GET
    @RolesAllowed("ADMIN")
    public Response obterTodos() {
        List<UsuarioResponseDTO> usuarios = usuarioService.obterTodos();
        return Response.ok(usuarios).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response obterPorId(@PathParam("id") Long id) {
        try {
            UsuarioResponseDTO usuario = usuarioService.obterPorId(id);
            return Response.ok(usuario).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @GET
    @Path("/buscar/nome")
    @RolesAllowed("ADMIN")
    public Response buscarPorNome(@QueryParam("nome") String nome) {
        List<UsuarioResponseDTO> usuarios = usuarioService.buscarPorNome(nome);
        return Response.ok(usuarios).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response atualizar(@PathParam("id") Long id, @Valid UsuarioRequestDTO dto) {
        try {
            UsuarioResponseDTO response = usuarioService.atualizar(id, dto);
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response deletar(@PathParam("id") Long id) {
        try {
            usuarioService.deletar(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @PUT
    @Path("/{id}/alterar-senha")
    @Authenticated
    public Response alterarSenha(@PathParam("id") Long id, 
            @QueryParam("senhaAtual") String senhaAtual,
            @QueryParam("novaSenha") String novaSenha) {
        try {
            // Validar se o usuário está tentando alterar sua própria senha
            UsuarioResponseDTO response = usuarioService.alterarSenha(id, senhaAtual, novaSenha);
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @POST
    @Path("/esqueceu-senha")
    @PermitAll
    public Response esqueceuSenha(@QueryParam("email") String email) {
        try {
            usuarioService.enviarRecuperacaoSenha(email);
            return Response.ok(new ErrorResponse("Email de recuperação enviado")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @POST
    @Path("/alterar-senha")
    @PermitAll
    public Response recuperarSenha(@QueryParam("token") String token, @QueryParam("novaSenha") String novaSenha) {
        try {
            usuarioService.recuperarSenha(token, novaSenha);
            return Response.ok(new ErrorResponse("Senha alterada com sucesso")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    public static class ErrorResponse {
        public String mensagem;

        public ErrorResponse(String mensagem) {
            this.mensagem = mensagem;
        }

        public String getMensagem() {
            return mensagem;
        }

        public void setMensagem(String mensagem) {
            this.mensagem = mensagem;
        }
    }
}

