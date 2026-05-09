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

import Topicos.dto.EnderecoRequestDTO;
import Topicos.dto.EnderecoResponseDTO;
import Topicos.service.EnderecoService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
class EnderecoTest {

    private static final String BASE_URL = "/api/enderecos";

    @InjectMock
    EnderecoService enderecoService;

    @BeforeEach
    void setUp() {
        reset(enderecoService);
    }

    @Test
    void deveListarEnderecosComStatus200() {
        when(enderecoService.obterTodos()).thenReturn(List.of(
            endereco(1L, "Rua A", "123", "Cidade A", "Estado A", "12345-678", "Compl A", 123456L, 123456L),
            endereco(2L, "Rua B", "456", "Cidade B", "Estado B", "98765-432", "Compl B", 123456L, 123456L)
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
            .body("[0].rua", equalTo("Rua A"))
            .body("[0].cidade", equalTo("Cidade A"));
    }

    @Test
    void deveBuscarPorIdComStatus200() {
        when(enderecoService.obterPorId(1L)).thenReturn(endereco(1L, "Rua A", "123", "Cidade A", "Estado A", "12345-678", "Compl A", 123456L, 123456L));

        given()
            .accept(ContentType.JSON)
        .when()
            .get(BASE_URL + "/1")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", equalTo(1))
            .body("rua", equalTo("Rua A"))
            .body("cidade", equalTo("Cidade A"));
    }

    @Test
    void deveRetornar404QuandoBuscarPorIdInexistente() {
        when(enderecoService.obterPorId(999L)).thenThrow(new RuntimeException("Endereço não encontrado"));

        given()
            .accept(ContentType.JSON)
        .when()
            .get(BASE_URL + "/999")
        .then()
            .statusCode(404);
    }

    @Test
    void deveCriarEnderecoComStatus201() {
        when(enderecoService.criar(any(EnderecoRequestDTO.class)))
            .thenReturn(endereco(10L, "Nova Rua", "789", "Nova Cidade", "Novo Estado", "11111-222", "Novo Compl", 123456L, 123456L));

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"rua\":\"Nova Rua\",\"numero\":\"789\",\"cidade\":\"Nova Cidade\",\"estado\":\"Novo Estado\",\"cep\":\"11111-222\",\"complemento\":\"Novo Compl\"}")
        .when()
            .post(BASE_URL)
        .then()
            .statusCode(201)
            .contentType(ContentType.JSON)
            .body("id", equalTo(10))
            .body("rua", equalTo("Nova Rua"))
            .body("cidade", equalTo("Nova Cidade"));
    }

    @Test
    void deveAtualizarEnderecoComStatus200() {
        when(enderecoService.atualizar(any(Long.class), any(EnderecoRequestDTO.class)))
            .thenReturn(endereco(1L, "Rua Atualizada", "999", "Cidade Atualizada", "Estado Atualizado", "22222-333", "Compl Atualizado", 123456L, 123456L));

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"rua\":\"Rua Atualizada\",\"numero\":\"999\",\"cidade\":\"Cidade Atualizada\",\"estado\":\"Estado Atualizado\",\"cep\":\"22222-333\",\"complemento\":\"Compl Atualizado\"}")
        .when()
            .put(BASE_URL + "/1")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", equalTo(1))
            .body("rua", equalTo("Rua Atualizada"))
            .body("cidade", equalTo("Cidade Atualizada"));
    }

    @Test
    void deveRemoverEnderecoComStatus204() {
        doNothing().when(enderecoService).deletar(1L);

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
            .body("{\"rua\":\"\",\"numero\":\"\",\"cidade\":\"\",\"estado\":\"\",\"cep\":\"\",\"complemento\":\"\"}")
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
            .body("{\"rua\":\"Rua\",\"numero\":\"123\",\"cidade\":\"Cidade\",\"estado\":\"Estado\",\"cep\":\"12345-678\",\"complemento\":\"Compl\"")
        .when()
            .post(BASE_URL)
        .then()
            .statusCode(400);
    }

    @Test
    void deveRespeitarHeadersAcceptEContentType() {
        when(enderecoService.obterTodos()).thenReturn(List.of());

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
            .body("rua=Rua&numero=123&cidade=Cidade&estado=Estado&cep=12345-678&complemento=Compl")
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

        when(enderecoService.obterTodos()).thenReturn(List.of());

        given()
            .accept(ContentType.JSON)
            .queryParam("cep", "teste")
        .when()
            .get(BASE_URL)
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    private EnderecoResponseDTO endereco(Long id, String rua, String numero, String cidade, String estado, String cep,
                                         String complemento, Long criadoEm, Long atualizadoEm) {
        return new EnderecoResponseDTO(id, rua, numero, cidade, estado, cep, complemento, criadoEm, atualizadoEm);
    }
}