package rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import modelo.Estabelecimento;
import servico.EstabelecimentoService;
import util.Ejb;

@Path("/estabelecimento")
public class EstabelecimentoRest {

	private EstabelecimentoService estabelecimentoService;

	public EstabelecimentoRest() {
		estabelecimentoService = Ejb.lookup(EstabelecimentoService.class);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvar(@Context HttpServletRequest httpServletRequest, 
			Estabelecimento estabelecimento) throws Exception {

		try {
			return Response.ok()
					.entity(
							estabelecimentoService.salvar(estabelecimento))
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(
			@QueryParam("p") @DefaultValue("1") int pagina, 
			@QueryParam("t") @DefaultValue("10") int tamanhoPagina, 
			@QueryParam("q") String iniciandoPor)  throws Exception {

		try {
			// Sem critério de pesquisa.
			if (iniciandoPor == null || iniciandoPor.trim().equals("")) {
				return Response.ok()
						.entity(
								estabelecimentoService.listarOrdenadoPorNome(pagina, tamanhoPagina))
						.build();

			// Com critério de pesquisa.
			} else {
				return Response.ok()
						.entity(
								estabelecimentoService.listarPorNomeOrdenadoPorNome(pagina, tamanhoPagina, iniciandoPor))
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
							estabelecimentoService.recuperar(id))
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}

	
}