// d:\Projetos\topicos\src\test\java\Topicos\resource\UsuarioResourceSecurityTest.java
package Topicos.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Classe de teste de integração para validar o controle de acesso (RBAC)
 * no endpoint GET /api/usuarios/{id}.
 *
 * Premissas para este teste:
 * 1. Existe um UsuarioResource com um método GET /api/usuarios/{id}
 *    que retorna os detalhes de um usuário.
 * 2. Este método está anotado com @RolesAllowed("ADMIN"), exigindo a role ADMIN.
 * 3. Os usuários com IDs 1 (USER) e 3 (ADMIN) existem no banco de dados
 *    conforme o import.sql fornecido.
 */
@QuarkusTest
public class UsuarioResourceSecurityTest {

    // IDs de usuários do seu import.sql
    private static final Long ADMIN_USER_ID = 3L; // diego@teste.com, role ADMIN
    private static final Long REGULAR_USER_ID = 1L; // carlos.silva@example.com, role USER

    /**
     * Cenário 1: Usuário com a role 'ADMIN' (Acesso liberado - 200 OK).
     * Simula um usuário com a role ADMIN tentando acessar um recurso restrito a ADMIN.
     */
    @Test
    @TestSecurity(user = "diego@teste.com", roles = {"ADMIN"})
    void shouldAllowAccessForAdminRole() {
        given()
            .when()
            .get("/api/usuarios/{id}", ADMIN_USER_ID) // Acessando o próprio usuário ADMIN
            .then()
            .statusCode(200) // Espera-se sucesso
            .body("id", is(ADMIN_USER_ID.intValue()))
            .body("nome", is("Diego Admin")); // Verifica se o conteúdo está correto
    }

    /**
     * Cenário 2: Usuário com a role 'USER' (Acesso negado - 403 Forbidden).
     * Simula um usuário com a role USER tentando acessar um recurso restrito a ADMIN.
     */
    @Test
    @TestSecurity(user = "carlos.silva@example.com", roles = {"USER"})
    void shouldDenyAccessForUserRole() {
        given()
            .when()
            .get("/api/usuarios/{id}", ADMIN_USER_ID) // Tentando acessar um recurso de ADMIN com role USER
            .then()
            .statusCode(403); // Espera-se Forbidden
    }

    /**
     * Cenário 3: Usuário anônimo / Sem token (Acesso negado - 401 Unauthorized).
     * Simula um usuário não autenticado tentando acessar um recurso protegido.
     */
    @Test
    void shouldDenyAccessForAnonymousUser() {
        given()
            .when()
            .get("/api/usuarios/{id}", ADMIN_USER_ID) // Tentando acessar um recurso protegido sem autenticação
            .then()
            .statusCode(401); // Espera-se Unauthorized
    }
}
