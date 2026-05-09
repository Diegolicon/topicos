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

import Topicos.dto.EnderecoResponseDTO;
import Topicos.dto.UsuarioRequestDTO;
import Topicos.dto.UsuarioResponseDTO;
import Topicos.service.UsuarioService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
class UsuarioTest {

    private static final String BASE_URL = "/api/usuarios";

    @InjectMock
    UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        reset(usuarioService);
    }

    @Test
    void deveListarUsuariosComStatus200() {
        when(usuarioService.obterTodos()).thenReturn(List.of(
            usuario(1L, "Usuario1", "email1@test.com", "123456789", endereco(1L, "Rua A", "123", "Cidade A", "Estado A", "12345-678", "Compl A", 123456L, 123456L), 123456L, 123456L),
            usuario(2L, "Usuario2", "email2@test.com", "987654321", endereco(2L, "Rua B", "456", "Cidade B", "Estado B", "98765-432", "Compl B", 123456L, 123456L), 123456L, 123456L)
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
            .body("[0].nome", equalTo("Usuario1"))
            .body("[0].email", equalTo("email1@test.com"));
    }

    @Test
    void deveBuscarPorIdComStatus200() {
        when(usuarioService.obterPorId(1L)).thenReturn(usuario(1L, "Usuario1", "email1@test.com", "123456789", endereco(1L, "Rua A", "123", "Cidade A", "Estado A", "12345-678", "Compl A", 123456L, 123456L), 123456L, 123456L));

        given()
            .accept(ContentType.JSON)
        .when()
            .get(BASE_URL + "/1")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", equalTo(1))
            .body("nome", equalTo("Usuario1"))
            .body("email", equalTo("email1@test.com"));
    }

    @Test
    void deveRetornar404QuandoBuscarPorIdInexistente() {
        when(usuarioService.obterPorId(999L)).thenThrow(new RuntimeException("Usuário não encontrado"));

        given()
            .accept(ContentType.JSON)
        .when()
            .get(BASE_URL + "/999")
        .then()
            .statusCode(404);
    }

    @Test
    void deveCriarUsuarioComStatus201() {
        when(usuarioService.criar(any(UsuarioRequestDTO.class)))
            .thenReturn(usuario(10L, "Novo Usuario", "novo@test.com", "111222333", endereco(10L, "Nova Rua", "789", "Nova Cidade", "Novo Estado", "11111-222", "Novo Compl", 123456L, 123456L), 123456L, 123456L));

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"nome\":\"Novo Usuario\",\"email\":\"novo@test.com\",\"telefone\":\"111222333\",\"endereco\":{\"rua\":\"Nova Rua\",\"numero\":\"789\",\"cidade\":\"Nova Cidade\",\"estado\":\"Novo Estado\",\"cep\":\"11111-222\",\"complemento\":\"Novo Compl\"}}")
        .when()
            .post(BASE_URL)
        .then()
            .statusCode(201)
            .contentType(ContentType.JSON)
            .body("id", equalTo(10))
            .body("nome", equalTo("Novo Usuario"))
            .body("email", equalTo("novo@test.com"));
    }

    @Test
    void deveAtualizarUsuarioComStatus200() {
        when(usuarioService.atualizar(any(Long.class), any(UsuarioRequestDTO.class)))
            .thenReturn(usuario(1L, "Usuario Atualizado", "atualizado@test.com", "444555666", endereco(1L, "Rua Atualizada", "999", "Cidade Atualizada", "Estado Atualizado", "22222-333", "Compl Atualizado", 123456L, 123456L), 123456L, 123456L));

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"nome\":\"Usuario Atualizado\",\"email\":\"atualizado@test.com\",\"telefone\":\"444555666\",\"endereco\":{\"rua\":\"Rua Atualizada\",\"numero\":\"999\",\"cidade\":\"Cidade Atualizada\",\"estado\":\"Estado Atualizado\",\"cep\":\"22222-333\",\"complemento\":\"Compl Atualizado\"}}")
        .when()
            .put(BASE_URL + "/1")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", equalTo(1))
            .body("nome", equalTo("Usuario Atualizado"))
            .body("email", equalTo("atualizado@test.com"));
    }

    @Test
    void deveRemoverUsuarioComStatus204() {
        doNothing().when(usuarioService).deletar(1L);

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
            .body("{\"nome\":\"\",\"email\":\"\",\"telefone\":\"\",\"endereco\":{\"rua\":\"\",\"numero\":\"\",\"cidade\":\"\",\"estado\":\"\",\"cep\":\"\",\"complemento\":\"\"}}")
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
            .body("{\"nome\":\"Usuario\",\"email\":\"email@test.com\",\"telefone\":\"123456789\",\"endereco\":{\"rua\":\"Rua\",\"numero\":\"123\",\"cidade\":\"Cidade\",\"estado\":\"Estado\",\"cep\":\"12345-678\",\"complemento\":\"Compl\"")
        .when()
            .post(BASE_URL)
        .then()
            .statusCode(400);
    }

    @Test
    void deveRespeitarHeadersAcceptEContentType() {
        when(usuarioService.obterTodos()).thenReturn(List.of());

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
            .body("nome=Usuario&email=email@test.com&telefone=123456789&endereco.rua=Rua&endereco.numero=123&endereco.cidade=Cidade&endereco.estado=Estado&endereco.cep=12345-678&endereco.complemento=Compl")
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

        when(usuarioService.obterTodos()).thenReturn(List.of());

        given()
            .accept(ContentType.JSON)
            .queryParam("nome", "teste")
        .when()
            .get(BASE_URL)
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    private UsuarioResponseDTO usuario(Long id, String nome, String email, String telefone, EnderecoResponseDTO endereco,
                                       Long criadoEm, Long atualizadoEm) {
        return new UsuarioResponseDTO(id, nome, email, telefone, endereco, criadoEm, atualizadoEm);
    }

    private EnderecoResponseDTO endereco(Long id, String rua, String numero, String cidade, String estado, String cep,
                                         String complemento, Long criadoEm, Long atualizadoEm) {
        return new EnderecoResponseDTO(id, rua, numero, cidade, estado, cep, complemento, criadoEm, atualizadoEm);
    }
}