package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import enums.Perfil;

@Path("/perfil")
public class PerfilRest {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar() throws Exception {
		try {
			return Response.ok()
					.entity(
							Perfil.values())
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}

}