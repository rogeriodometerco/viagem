package rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.LocalizacaoDao;
import modelo.Localizacao;
import util.Ejb;

@Path("/localizacao")
public class LocalizacaoRest {

	//private LocalizacaoService localizacaoService;
	private LocalizacaoDao localizacaoDao; // para testar funcionalidades que n�o estar�o no service.

	public LocalizacaoRest() {
	//	localizacaoService = Ejb.lookup(LocalizacaoService.class);
		localizacaoDao = Ejb.lookup(LocalizacaoDao.class);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrar(@Context HttpServletRequest httpServletRequest, 
			Localizacao localizacao) throws Exception {
		return Response.ok()
				.entity(
						localizacao)
					//	localizacaoService
					//	.registrar(localizacao))
				.build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar()  throws Exception {
		return Response.ok()
				.entity(
						localizacaoDao.listar())
				.build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recuperar(@PathParam("id") Long id) throws Exception{
		return Response.ok()
				.entity(
						localizacaoDao.recuperar(id))
				.build();
	}

	@DELETE
	@Path("/{id}")
	public Response excluir(@PathParam("id") Long id) throws Exception {
		localizacaoDao.excluir(
				localizacaoDao.recuperar(id));
		return Response.ok()
				.build();
	}

}