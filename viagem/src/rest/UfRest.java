package rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import modelo.UF;
import servico.UfService;
import util.Ejb;

@Path("/uf")
public class UfRest {

	private UfService ufService;

	public UfRest() {
		ufService = Ejb.lookup(UfService.class);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvar(@Context HttpServletRequest httpServletRequest, 
			UF uf) throws Exception {
		return Response.ok()
				.entity(
						ufService.salvar(uf))
				.build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(@QueryParam("p") int pagina, 
				@QueryParam("t") int tamanhoPagina)  throws Exception {
		if (pagina == 0) {
			pagina = 1;
		}
		if (tamanhoPagina == 0) {
			tamanhoPagina = 10;
		}
		return Response.ok()
				.entity(
						ufService.listarOrdenadoPorAbreviatura(pagina, tamanhoPagina))
				.build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recuperar(@PathParam("id") Long id) throws Exception{
		return Response.ok()
				.entity(
						ufService.recuperar(id))
				.build();
	}

	@DELETE
	@Path("/{id}")
	public Response excluir(@PathParam("id") Long id) throws Exception {
		ufService.excluir(id);
		return Response.ok()
				.build();
	}

}