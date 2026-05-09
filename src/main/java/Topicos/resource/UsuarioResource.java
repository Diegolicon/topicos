package Topicos.resource;

import Topicos.dto.UsuarioRequestDTO;
import Topicos.dto.UsuarioResponseDTO;
import Topicos.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @POST
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
    public Response obterTodos() {
        List<UsuarioResponseDTO> usuarios = usuarioService.obterTodos();
        return Response.ok(usuarios).build();
    }

    @GET
    @Path("/{id}")
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
    public Response buscarPorNome(@QueryParam("nome") String nome) {
        List<UsuarioResponseDTO> usuarios = usuarioService.buscarPorNome(nome);
        return Response.ok(usuarios).build();
    }

    @PUT
    @Path("/{id}")
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
    public Response deletar(@PathParam("id") Long id) {
        try {
            usuarioService.deletar(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
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

