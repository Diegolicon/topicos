package Topicos.resource;

import java.util.List;

import Topicos.dto.EnderecoRequestDTO;
import Topicos.dto.EnderecoResponseDTO;
import Topicos.service.EnderecoService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/enderecos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class EnderecoResource {

    @Inject
    EnderecoService enderecoService;

    @POST
    public Response criar(@Valid EnderecoRequestDTO dto) {
        try {
            EnderecoResponseDTO response = enderecoService.criar(dto);
            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @GET
    public Response obterTodos() {
        List<EnderecoResponseDTO> enderecos = enderecoService.obterTodos();
        return Response.ok(enderecos).build();
    }

    @GET
    @Path("/{id}")
    public Response obterPorId(@PathParam("id") Long id) {
        try {
            EnderecoResponseDTO endereco = enderecoService.obterPorId(id);
            return Response.ok(endereco).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @GET
    @Path("/buscar/cep")
    public Response buscarPorCep(@QueryParam("cep") String cep) {
        List<EnderecoResponseDTO> enderecos = enderecoService.buscarPorCep(cep);
        return Response.ok(enderecos).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid EnderecoRequestDTO dto) {
        try {
            EnderecoResponseDTO response = enderecoService.atualizar(id, dto);
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
            enderecoService.deletar(id);
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

