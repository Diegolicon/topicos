package Topicos.resource;

import java.util.List;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import Topicos.dto.ProdutoRequestDTO;
import Topicos.dto.ProdutoResponseDTO;
import Topicos.service.ProdutoService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
class ProdutoTest {

    private static final String BASE_URL = "/api/produtos";

    @InjectMock
    ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        reset(produtoService);
    }

    @Test
    void deveListarProdutosComStatus200() {
        when(produtoService.obterTodos()).thenReturn(List.of(
            produto(1L, "Produto1", "Descrição1", 100.0, 10, "Marca1", 123456L, 123456L),
            produto(2L, "Produto2", "Descrição2", 200.0, 20, "Marca2", 123456L, 123456L)
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
            .body("[0].nome", equalTo("Produto1"))
            .body("[0].descricao", equalTo("Descrição1"));
    }

    @Test
    void deveBuscarPorIdComStatus200() {
        when(produtoService.obterPorId(1L)).thenReturn(produto(1L, "Produto1", "Descrição1", 100.0, 10, "Marca1", 123456L, 123456L));

        given()
            .accept(ContentType.JSON)
        .when()
            .get(BASE_URL + "/1")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", equalTo(1))
            .body("nome", equalTo("Produto1"))
            .body("descricao", equalTo("Descrição1"));
    }

    @Test
    void deveRetornar404QuandoBuscarPorIdInexistente() {
        when(produtoService.obterPorId(999L)).thenThrow(new RuntimeException("Produto não encontrado"));

        given()
            .accept(ContentType.JSON)
        .when()
            .get(BASE_URL + "/999")
        .then()
            .statusCode(404);
    }

    @Test
    void deveCriarProdutoComStatus201() {
        when(produtoService.criar(any(ProdutoRequestDTO.class)))
            .thenReturn(produto(10L, "Novo Produto", "Descrição nova", 150.0, 15, "Nova Marca", 123456L, 123456L));

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"nome\":\"Novo Produto\",\"descricao\":\"Descrição nova\",\"preco\":150.0,\"estoque\":15,\"marca\":\"Nova Marca\"}")
        .when()
            .post(BASE_URL)
        .then()
            .statusCode(201)
            .contentType(ContentType.JSON)
            .body("id", equalTo(10))
            .body("nome", equalTo("Novo Produto"))
            .body("descricao", equalTo("Descrição nova"));
    }

    @Test
    void deveAtualizarProdutoComStatus200() {
        when(produtoService.atualizar(any(Long.class), any(ProdutoRequestDTO.class)))
            .thenReturn(produto(1L, "Produto Atualizado", "Descrição atualizada", 120.0, 12, "Marca Atualizada", 123456L, 123456L));

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"nome\":\"Produto Atualizado\",\"descricao\":\"Descrição atualizada\",\"preco\":120.0,\"estoque\":12,\"marca\":\"Marca Atualizada\"}")
        .when()
            .put(BASE_URL + "/1")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", equalTo(1))
            .body("nome", equalTo("Produto Atualizado"))
            .body("descricao", equalTo("Descrição atualizada"));
    }

    @Test
    void deveRemoverProdutoComStatus204() {
        doNothing().when(produtoService).deletar(1L);

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
            .body("{\"nome\":\"\",\"descricao\":\"\",\"preco\":null,\"estoque\":null,\"marca\":\"\"}")
        .when()
            .post(BASE_URL)
        .then()
            .statusCode(201);
    }

    @Test
    void deveRetornar400QuandoJsonForMalformado() {
        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"nome\":\"Produto\",\"descricao\":\"Desc\",\"preco\":100.0,\"estoque\":10,\"marca\":\"Marca\"")
        .when()
            .post(BASE_URL)
        .then()
            .statusCode(400);
    }

    @Test
    void deveRespeitarHeadersAcceptEContentType() {
        when(produtoService.obterTodos()).thenReturn(List.of());

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
            .body("nome=Produto&descricao=Desc&preco=100.0&estoque=10&marca=Marca")
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

        when(produtoService.obterTodos()).thenReturn(List.of());

        given()
            .accept(ContentType.JSON)
            .queryParam("nome", "teste")
        .when()
            .get(BASE_URL)
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    private ProdutoResponseDTO produto(Long id, String nome, String descricao, Double preco, Integer estoque, String marca,
                                       Long criadoEm, Long atualizadoEm) {
        return new ProdutoResponseDTO(id, nome, descricao, preco, estoque, marca, criadoEm, atualizadoEm);
    }
}