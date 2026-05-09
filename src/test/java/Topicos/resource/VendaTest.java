package Topicos.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Topicos.dto.ItemVendaResponseDTO;
import Topicos.dto.ProdutoSimplicadoResponseDTO;
import Topicos.dto.UsuarioSimplicadoResponseDTO;
import Topicos.dto.VendaRequestDTO;
import Topicos.dto.VendaResponseDTO;
import Topicos.model.Venda;
import Topicos.service.VendaService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
class VendaTest {

    private static final String BASE_URL = "/api/vendas";

    @InjectMock
    VendaService vendaService;

    @BeforeEach
    void setUp() {
        reset(vendaService);
    }

    @Test
    void deveListarVendasComStatus200() {
        when(vendaService.obterTodos()).thenReturn(List.of(
            venda(1L, usuarioSimp(1L, "Usuario1", "email1@test.com", "123456789"), List.of(item(1L, 1L, produtoSimp(1L, "Produto1", 100.0, 10, "Marca1"), 2, 100.0, 200.0, 123456L, 123456L)), 200.0, Venda.StatusVenda.PENDENTE, "Obs1", 123456L, 123456L),
            venda(2L, usuarioSimp(2L, "Usuario2", "email2@test.com", "987654321"), List.of(), 0.0, Venda.StatusVenda.CONCLUÍDA, "Obs2", 123456L, 123456L)
        ));

        given()
            .accept(ContentType.JSON)
        .when()
            .get(BASE_URL)
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("size()", is(2))
            .body("[0].id", equalTo(1))
            .body("[0].totalVenda", equalTo(200.0f))
            .body("[0].status", equalTo("PENDENTE"));
    }

    @Test
    void deveBuscarPorIdComStatus200() {
        when(vendaService.obterPorId(1L)).thenReturn(venda(1L, usuarioSimp(1L, "Usuario1", "email1@test.com", "123456789"), List.of(item(1L, 1L, produtoSimp(1L, "Produto1", 100.0, 10, "Marca1"), 2, 100.0, 200.0, 123456L, 123456L)), 200.0, Venda.StatusVenda.PENDENTE, "Obs1", 123456L, 123456L));

        given()
            .accept(ContentType.JSON)
        .when()
            .get(BASE_URL + "/1")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", equalTo(1))
            .body("totalVenda", equalTo(200.0f))
            .body("status", equalTo("PENDENTE"));
    }

    @Test
    void deveRetornar404QuandoBuscarPorIdInexistente() {
        when(vendaService.obterPorId(999L)).thenThrow(new RuntimeException("Venda não encontrada"));

        given()
            .accept(ContentType.JSON)
        .when()
            .get(BASE_URL + "/999")
        .then()
            .statusCode(404);
    }

    @Test
    void deveCriarVendaComStatus201() {
        when(vendaService.criar(any(VendaRequestDTO.class)))
            .thenReturn(venda(10L, usuarioSimp(1L, "Usuario1", "email1@test.com", "123456789"), List.of(), 0.0, Venda.StatusVenda.PENDENTE, "Nova Obs", 123456L, 123456L));

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"usuarioId\":1,\"itens\":[],\"observacoes\":\"Nova Obs\"}")
        .when()
            .post(BASE_URL)
        .then()
            .statusCode(201)
            .contentType(ContentType.JSON)
            .body("id", equalTo(10))
            .body("status", equalTo("PENDENTE"))
            .body("observacoes", equalTo("Nova Obs"));
    }

    @Test
    void deveAtualizarVendaComStatus200() {
        when(vendaService.atualizar(any(Long.class), any(VendaRequestDTO.class)))
            .thenReturn(venda(1L, usuarioSimp(1L, "Usuario1", "email1@test.com", "123456789"), List.of(), 0.0, Venda.StatusVenda.PENDENTE, "Obs Atualizada", 123456L, 123456L));

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"usuarioId\":1,\"itens\":[],\"observacoes\":\"Obs Atualizada\"}")
        .when()
            .put(BASE_URL + "/1")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", equalTo(1))
            .body("observacoes", equalTo("Obs Atualizada"));
    }

    @Test
    void deveRemoverVendaComStatus204() {
        doNothing().when(vendaService).deletar(1L);

        given()
            .accept(ContentType.JSON)
        .when()
            .delete(BASE_URL + "/1")
        .then()
            .statusCode(204);
    }

    @Test
    void deveRetornar400QuandoPayloadForInvalido() {
        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"usuarioId\":null,\"itens\":null,\"observacoes\":\"\"}")
        .when()
            .post(BASE_URL)
        .then()
            .statusCode(400);
    }

    @Test
    void deveRetornar400QuandoJsonForMalformado() {
        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"usuarioId\":1,\"itens\":[],\"observacoes\":\"Obs\"")
        .when()
            .post(BASE_URL)
        .then()
            .statusCode(400);
    }

    @Test
    void deveRespeitarHeadersAcceptEContentType() {
        when(vendaService.obterTodos()).thenReturn(List.of());

        given()
            .accept(ContentType.JSON)
        .when()
            .get(BASE_URL)
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON);

        given()
            .accept(ContentType.XML)
        .when()
            .get(BASE_URL)
        .then()
            .statusCode(406);

        given()
            .contentType(ContentType.TEXT)
            .accept(ContentType.JSON)
            .body("usuarioId=1&itens=&observacoes=Obs")
        .when()
            .post(BASE_URL)
        .then()
            .statusCode(415);
    }

    @Test
    void deveTratarPathParamOuQueryParamInvalidos() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get(BASE_URL + "/abc")
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(404)));

        when(vendaService.obterTodos()).thenReturn(List.of());

        given()
            .accept(ContentType.JSON)
            .queryParam("usuarioId", "teste")
        .when()
            .get(BASE_URL)
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    private VendaResponseDTO venda(Long id, UsuarioSimplicadoResponseDTO usuario, List<ItemVendaResponseDTO> itens,
                                   Double totalVenda, Venda.StatusVenda status, String observacoes,
                                   Long criadoEm, Long atualizadoEm) {
        return new VendaResponseDTO(id, usuario, itens, totalVenda, status, observacoes, criadoEm, atualizadoEm);
    }

    private UsuarioSimplicadoResponseDTO usuarioSimp(Long id, String nome, String email, String telefone) {
        return new UsuarioSimplicadoResponseDTO(id, nome, email, telefone);
    }

    private ItemVendaResponseDTO item(Long id, Long vendaId, ProdutoSimplicadoResponseDTO produto, Integer quantidade,
                                      Double precoUnitario, Double subtotal, Long criadoEm, Long atualizadoEm) {
        return new ItemVendaResponseDTO(id, vendaId, produto, quantidade, precoUnitario, subtotal, criadoEm, atualizadoEm);
    }

    private ProdutoSimplicadoResponseDTO produtoSimp(Long id, String nome, Double preco, Integer estoque, String marca) {
        return new ProdutoSimplicadoResponseDTO(id, nome, preco, estoque, marca);
    }
}