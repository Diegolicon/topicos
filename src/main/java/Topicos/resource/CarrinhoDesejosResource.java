package Topicos.resource;

import java.util.List;

import Topicos.dto.CarrinhoDesejosResponseDTO;
import Topicos.service.CarrinhoDesejosService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/carrinho-desejos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class CarrinhoDesejosResource {

    @Inject
    CarrinhoDesejosService carrinhoDesejosService;

    @POST
    @Path("/usuario/{usuarioId}/produto/{produtoId}")
    public Response adicionar(@PathParam("usuarioId") Long usuarioId,
            @PathParam("produtoId") Long produtoId) {
        try {
            CarrinhoDesejosResponseDTO response = carrinhoDesejosService.adicionar(usuarioId, produtoId);
            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @GET
    @Path("/usuario/{usuarioId}")
    public Response obterPorUsuario(@PathParam("usuarioId") Long usuarioId) {
        List<CarrinhoDesejosResponseDTO> itens = carrinhoDesejosService.obterPorUsuario(usuarioId);
        return Response.ok(itens).build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") Long id) {
        try {
            carrinhoDesejosService.remover(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/usuario/{usuarioId}/produto/{produtoId}")
    public Response removerPorUsuarioEProduto(@PathParam("usuarioId") Long usuarioId,
            @PathParam("produtoId") Long produtoId) {
        try {
            carrinhoDesejosService.removerPorUsuarioEProduto(usuarioId, produtoId);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/usuario/{usuarioId}")
    public Response limpar(@PathParam("usuarioId") Long usuarioId) {
        carrinhoDesejosService.limpar(usuarioId);
        return Response.noContent().build();
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
