package Topicos.resource;

import Topicos.dto.UsuarioLoginDTO;
import Topicos.model.Usuario;
import Topicos.service.TokenService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    TokenService tokenService;

    @POST
    @Path("/login")
    public Response login(UsuarioLoginDTO login) {
        // Busca o usuário no banco pelo email
        // Se você usa Panache, pode ser: Usuario.find("email", login.email).firstResult();
        Usuario usuario = Usuario.find("email", login.email).firstResult();

        if (usuario != null && usuario.getSenha().equals(login.senha)) {
            String token = tokenService.generateToken(usuario);
            // Retornamos um JSON com o token para o seu "frontend" (Postman) ler
            return Response.ok("{\"token\":\"" + token + "\"}").build();
        }

        return Response.status(Response.Status.UNAUTHORIZED)
                       .entity("{\"mensagem\":\"Credenciais inválidas\"}").build();
    }
}