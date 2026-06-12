package Topicos.resource;

import java.util.List;

import Topicos.dto.CarrinhoDesejosResponseDTO;
import Topicos.dto.CarrinhoItemRequestDTO;
import Topicos.dto.CheckoutCarrinhoRequestDTO;
import Topicos.dto.VendaResponseDTO;
import Topicos.service.CarrinhoDesejosService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/api/carrinho")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class CarrinhoDesejosResource {

    @Inject
    CarrinhoDesejosService carrinhoDesejosService;

    @POST
    @Path("/itens")
    public Response adicionar(@Context SecurityContext securityContext, @Valid CarrinhoItemRequestDTO dto) {
        try {
            CarrinhoDesejosResponseDTO response = carrinhoDesejosService.adicionar(
                    securityContext.getUserPrincipal().getName(),
                    dto.getProdutoId(),
                    dto.getQuantidade());
            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @GET
    public Response obterMeuCarrinho(@Context SecurityContext securityContext) {
        List<CarrinhoDesejosResponseDTO> itens = carrinhoDesejosService.obterMeuCarrinho(
                securityContext.getUserPrincipal().getName());
        return Response.ok(itens).build();
    }

    @PUT
    @Path("/itens/{produtoId}")
    public Response atualizarQuantidade(@Context SecurityContext securityContext,
            @PathParam("produtoId") Long produtoId,
            @Valid CarrinhoItemRequestDTO dto) {
        try {
            CarrinhoDesejosResponseDTO response = carrinhoDesejosService.atualizarQuantidade(
                    securityContext.getUserPrincipal().getName(),
                    produtoId,
                    dto.getQuantidade());
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/itens/{produtoId}")
    public Response remover(@Context SecurityContext securityContext,
            @PathParam("produtoId") Long produtoId) {
        try {
            carrinhoDesejosService.remover(securityContext.getUserPrincipal().getName(), produtoId);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @DELETE
    public Response limpar(@Context SecurityContext securityContext) {
        carrinhoDesejosService.limpar(securityContext.getUserPrincipal().getName());
        return Response.noContent().build();
    }

    @POST
    @Path("/checkout")
    public Response finalizarCompra(@Context SecurityContext securityContext, CheckoutCarrinhoRequestDTO dto) {
        try {
            Long enderecoId = dto != null ? dto.getEnderecoId() : null;
            String observacoes = dto != null ? dto.getObservacoes() : null;
            VendaResponseDTO response = carrinhoDesejosService.finalizarCompra(
                    securityContext.getUserPrincipal().getName(),
                    enderecoId,
                    observacoes);
            return Response.status(Response.Status.CREATED).entity(response).build();
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
