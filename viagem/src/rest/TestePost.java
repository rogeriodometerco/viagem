package rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/testePost")
public class TestePost {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response testar() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("Status", "OK");
		return Response.ok()
				.entity(result)
				.build();
	}

}
