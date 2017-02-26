package rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import com.google.gson.Gson;

import dto.ParametrosListagem;
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

		try {
			return Response.ok()
					.entity(
							ufService.salvar(uf))
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
			@QueryParam("t") @DefaultValue("10") int tamanhoPagina) throws Exception {

		try {
			return Response.ok()
					.entity(
							ufService.listarOrdenadoPorAbreviatura(pagina, tamanhoPagina))
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}

	@Path("/lista")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar2(@QueryParam("parametros") String parametros)  throws Exception {
		String json = "{p:1, t:5, filtros:[{chave: nome, valor: campo, restricao: inicia}, {chave: uf, valor: PR, restricao: igual}], ordenacao: [{chave: nome, ordem: A}]}";
		ParametrosListagem p = new Gson().fromJson(json, ParametrosListagem.class);
		System.out.println("/lista: " + parametros);
		try {
			return Response.ok()
					.entity(
							new Gson().toJson(p))
					.build();
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
							ufService.recuperar(id))
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
			ufService.excluir(id);
			return Response.ok()
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}

	// TODO Tratar exception gerada pelo Servlet, nem chegou na aplica��o ainda. Ex.: query param errada.
	
}