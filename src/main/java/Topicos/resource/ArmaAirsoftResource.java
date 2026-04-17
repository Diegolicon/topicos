package Topicos.resource;

import Topicos.dto.ArmaAirsoftRequestDTO;
import Topicos.dto.ArmaAirsoftResponseDTO;
import Topicos.service.ArmaAirsoftService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/armas-airsoft")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArmaAirsoftResource {

    @Inject
    ArmaAirsoftService armaAirsoftService;

    @POST
    public Response criar(ArmaAirsoftRequestDTO dto) {
        try {
            ArmaAirsoftResponseDTO response = armaAirsoftService.criar(dto);
            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @GET
    public Response obterTodos() {
        List<ArmaAirsoftResponseDTO> armas = armaAirsoftService.obterTodos();
        return Response.ok(armas).build();
    }

    @GET
    @Path("/{id}")
    public Response obterPorId(@PathParam("id") Long id) {
        try {
            ArmaAirsoftResponseDTO arma = armaAirsoftService.obterPorId(id);
            return Response.ok(arma).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @GET
    @Path("/buscar/nome")
    public Response buscarPorNome(@QueryParam("nome") String nome) {
        List<ArmaAirsoftResponseDTO> armas = armaAirsoftService.buscarPorNome(nome);
        return Response.ok(armas).build();
    }

    @GET
    @Path("/buscar/modelo")
    public Response buscarPorModelo(@QueryParam("modelo") String modelo) {
        List<ArmaAirsoftResponseDTO> armas = armaAirsoftService.buscarPorModelo(modelo);
        return Response.ok(armas).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, ArmaAirsoftRequestDTO dto) {
        try {
            ArmaAirsoftResponseDTO response = armaAirsoftService.atualizar(id, dto);
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
            armaAirsoftService.deletar(id);
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

