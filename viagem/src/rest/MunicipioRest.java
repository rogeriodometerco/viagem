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

import modelo.Municipio;
import servico.MunicipioService;
import util.Ejb;

@Path("/municipio")
public class MunicipioRest {

	private MunicipioService municipioService;

	public MunicipioRest() {
		municipioService = Ejb.lookup(MunicipioService.class);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvar(@Context HttpServletRequest httpServletRequest, 
			Municipio municipio) throws Exception {

		try {
			return Response.ok()
					.entity(
							municipioService.salvar(municipio))
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(@QueryParam("q") String query, @QueryParam("p") int pagina, 
			@QueryParam("t") int tamanhoPagina)  throws Exception {
		if (pagina == 0) {
			pagina = 1;
		}
		if (tamanhoPagina == 0) {
			tamanhoPagina = 10;
		}
		try {
			// Sem critério de pesquisa.
			if (query == null || query.trim().equals("")) {
				return Response.ok()
						.entity(
								municipioService.listarOrdenadoPorNome(pagina, tamanhoPagina))
						.build();

			// Com critério de pesquisa.
			} else {
				return Response.ok()
						.entity(
								municipioService.listarPorNomeOrdenadoPorNome(query, pagina, tamanhoPagina))
						.build();
			}
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recuperar(@PathParam("id") Long id) throws Exception{
		try {
			return Response.ok()
					.entity(
							municipioService.recuperar(id))
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}

	@DELETE
	@Path("/{id}")
	public Response excluir(@PathParam("id") Long id) throws Exception {
		try {
			municipioService.excluir(id);
			return Response.ok()
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}

}