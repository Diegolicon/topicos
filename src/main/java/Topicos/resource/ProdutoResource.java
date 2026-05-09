package Topicos.resource;

import Topicos.dto.ProdutoRequestDTO;
import Topicos.dto.ProdutoResponseDTO;
import Topicos.service.ProdutoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @Inject
    ProdutoService produtoService;

    @POST
    public Response criar(@Valid ProdutoRequestDTO dto) {
        try {
            ProdutoResponseDTO response = produtoService.criar(dto);
            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @GET
    public Response obterTodos() {
        List<ProdutoResponseDTO> produtos = produtoService.obterTodos();
        return Response.ok(produtos).build();
    }

    @GET
    @Path("/{id}")
    public Response obterPorId(@PathParam("id") Long id) {
        try {
            ProdutoResponseDTO produto = produtoService.obterPorId(id);
            return Response.ok(produto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @GET
    @Path("/buscar/nome")
    public Response buscarPorNome(@QueryParam("nome") String nome) {
        List<ProdutoResponseDTO> produtos = produtoService.buscarPorNome(nome);
        return Response.ok(produtos).build();
    }

    @GET
    @Path("/com-estoque")
    public Response obterComEstoque() {
        List<ProdutoResponseDTO> produtos = produtoService.obterComEstoque();
        return Response.ok(produtos).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid ProdutoRequestDTO dto) {
        try {
            ProdutoResponseDTO response = produtoService.atualizar(id, dto);
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
            produtoService.deletar(id);
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

