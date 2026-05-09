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

import Topicos.dto.ArmaAirsoftRequestDTO;
import Topicos.dto.ArmaAirsoftResponseDTO;
import Topicos.model.TipoPropulsao;
import Topicos.service.ArmaAirsoftService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
class ArmaAirsoftTest {

    private static final String BASE_URL = "/api/armas-airsoft";

    @InjectMock
    ArmaAirsoftService armaAirsoftService;

    @BeforeEach
    void setUp() {
        reset(armaAirsoftService);
    }

    @Test
    void deveListarArmasComStatus200() {
        when(armaAirsoftService.obterTodos()).thenReturn(List.of(
            arma(1L, "Arma1", "Descrição1", 100.0, 10, "Marca1", TipoPropulsao.AEG, "Modelo1", 100.0, 50.0, 123456L, 123456L),
            arma(2L, "Arma2", "Descrição2", 200.0, 20, "Marca2", TipoPropulsao.GBB, "Modelo2", 200.0, 100.0, 123456L, 123456L)
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
            .body("[0].nome", equalTo("Arma1"))
            .body("[0].descricao", equalTo("Descrição1"));
    }

    @Test
    void deveBuscarPorIdComStatus200() {
        when(armaAirsoftService.obterPorId(1L)).thenReturn(arma(1L, "Arma1", "Descrição1", 100.0, 10, "Marca1", TipoPropulsao.AEG, "Modelo1", 100.0, 50.0, 123456L, 123456L));

        given()
            .accept(ContentType.JSON)
        .when()
            .get(BASE_URL + "/1")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", equalTo(1))
            .body("nome", equalTo("Arma1"))
            .body("descricao", equalTo("Descrição1"));
    }

    @Test
    void deveRetornar404QuandoBuscarPorIdInexistente() {
        when(armaAirsoftService.obterPorId(999L)).thenThrow(new RuntimeException("Arma não encontrada"));

        given()
            .accept(ContentType.JSON)
        .when()
            .get(BASE_URL + "/999")
        .then()
            .statusCode(404);
    }

    @Test
    void deveCriarArmaComStatus201() {
        when(armaAirsoftService.criar(any(ArmaAirsoftRequestDTO.class)))
            .thenReturn(arma(10L, "Nova Arma", "Descrição nova", 150.0, 15, "Nova Marca", TipoPropulsao.SPRING, "Novo Modelo", 150.0, 75.0, 123456L, 123456L));

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"nome\":\"Nova Arma\",\"descricao\":\"Descrição nova\",\"preco\":150.0,\"estoque\":15,\"marca\":\"Nova Marca\",\"tipoPropulsao\":\"SPRING\",\"modelo\":\"Novo Modelo\",\"velocidadeEscopeta\":150.0,\"alcanceEfetivo\":75.0}")
        .when()
            .post(BASE_URL)
        .then()
            .statusCode(201)
            .contentType(ContentType.JSON)
            .body("id", equalTo(10))
            .body("nome", equalTo("Nova Arma"))
            .body("descricao", equalTo("Descrição nova"));
    }

    @Test
    void deveAtualizarArmaComStatus200() {
        when(armaAirsoftService.atualizar(any(Long.class), any(ArmaAirsoftRequestDTO.class)))
            .thenReturn(arma(1L, "Arma Atualizada", "Descrição atualizada", 120.0, 12, "Marca Atualizada", TipoPropulsao.AEG, "Modelo Atualizado", 120.0, 60.0, 123456L, 123456L));

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"nome\":\"Arma Atualizada\",\"descricao\":\"Descrição atualizada\",\"preco\":120.0,\"estoque\":12,\"marca\":\"Marca Atualizada\",\"tipoPropulsao\":\"AEG\",\"modelo\":\"Modelo Atualizado\",\"velocidadeEscopeta\":120.0,\"alcanceEfetivo\":60.0}")
        .when()
            .put(BASE_URL + "/1")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", equalTo(1))
            .body("nome", equalTo("Arma Atualizada"))
            .body("tipoPropulsao", equalTo("AEG"));
    }

    @Test
    void deveRemoverArmaComStatus204() {
        doNothing().when(armaAirsoftService).deletar(1L);

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
            .body("{\"nome\":\"\",\"descricao\":\"\",\"preco\":null,\"estoque\":null,\"marca\":\"\",\"tipoPropulsao\":null,\"modelo\":\"\",\"velocidadeEscopeta\":null,\"alcanceEfetivo\":null}")
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
            .body("{\"nome\":\"Arma\",\"descricao\":\"Desc\",\"preco\":100.0,\"estoque\":10,\"marca\":\"Marca\",\"tipoPropulsao\":\"AEG\",\"modelo\":\"Modelo\",\"velocidadeEscopeta\":100.0,\"alcanceEfetivo\":50.0")
        .when()
            .post(BASE_URL)
        .then()
            .statusCode(400);
    }

    @Test
    void deveRespeitarHeadersAcceptEContentType() {
        when(armaAirsoftService.obterTodos()).thenReturn(List.of());

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
            .body("nome=Arma&descricao=Desc&preco=100.0&estoque=10&marca=Marca&tipoPropulsao=AEG&modelo=Modelo&velocidadeEscopeta=100.0&alcanceEfetivo=50.0")
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

        when(armaAirsoftService.obterTodos()).thenReturn(List.of());

        given()
            .accept(ContentType.JSON)
            .queryParam("nome", "teste")
        .when()
            .get(BASE_URL)
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    private ArmaAirsoftResponseDTO arma(Long id, String nome, String descricao, Double preco, Integer estoque, String marca,
                                        TipoPropulsao tipoPropulsao, String modelo, Double velocidadeEscopeta, Double alcanceEfetivo,
                                        Long criadoEm, Long atualizadoEm) {
        return new ArmaAirsoftResponseDTO(id, nome, descricao, preco, estoque, marca, tipoPropulsao, modelo, velocidadeEscopeta, alcanceEfetivo, criadoEm, atualizadoEm);
    }
}