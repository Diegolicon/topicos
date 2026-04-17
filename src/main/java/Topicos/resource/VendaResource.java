package Topicos.resource;

import Topicos.dto.VendaRequestDTO;
import Topicos.dto.VendaResponseDTO;
import Topicos.model.Venda;
import Topicos.service.VendaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/vendas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VendaResource {

    @Inject
    VendaService vendaService;

    @POST
    public Response criar(VendaRequestDTO dto) {
        try {
            VendaResponseDTO response = vendaService.criar(dto);
            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @GET
    public Response obterTodos() {
        List<VendaResponseDTO> vendas = vendaService.obterTodos();
        return Response.ok(vendas).build();
    }

    @GET
    @Path("/{id}")
    public Response obterPorId(@PathParam("id") Long id) {
        try {
            VendaResponseDTO venda = vendaService.obterPorId(id);
            return Response.ok(venda).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @GET
    @Path("/usuario/{usuarioId}")
    public Response obterPorUsuario(@PathParam("usuarioId") Long usuarioId) {
        List<VendaResponseDTO> vendas = vendaService.obterPorUsuario(usuarioId);
        return Response.ok(vendas).build();
    }

    @GET
    @Path("/status/{status}")
    public Response obterPorStatus(@PathParam("status") Venda.StatusVenda status) {
        List<VendaResponseDTO> vendas = vendaService.obterPorStatus(status);
        return Response.ok(vendas).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, VendaRequestDTO dto) {
        try {
            VendaResponseDTO response = vendaService.atualizar(id, dto);
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @PUT
    @Path("/{id}/status")
    public Response atualizarStatus(@PathParam("id") Long id, @QueryParam("status") Venda.StatusVenda novoStatus) {
        try {
            VendaResponseDTO response = vendaService.atualizarStatus(id, novoStatus);
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
            vendaService.deletar(id);
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

