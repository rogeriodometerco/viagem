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

import dao.LocalizacaoMotoristaDao;
import modelo.LocalizacaoMotorista;
import servico.LocalizacaoMotoristaService;
import util.Ejb;

@Path("/localizacaoMotorista")
public class LocalizacaoMotoristaRest {

	private LocalizacaoMotoristaService localizacaoMotoristaService;
	private LocalizacaoMotoristaDao localizacaoMotoristaDao; // para testar funcionalidades que não estarão no service.

	public LocalizacaoMotoristaRest() {
		localizacaoMotoristaService = Ejb.lookup(LocalizacaoMotoristaService.class);
		localizacaoMotoristaDao = Ejb.lookup(LocalizacaoMotoristaDao.class);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvar(@Context HttpServletRequest httpServletRequest, 
			LocalizacaoMotorista localizacaoMotorista) throws Exception {
		return Response.ok()
				.entity(
						localizacaoMotoristaDao
						.salvar(localizacaoMotorista))
				.build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar()  throws Exception {
		return Response.ok()
				.entity(
						localizacaoMotoristaDao.listar())
				.build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recuperar(@PathParam("id") Long id) throws Exception{
		return Response.ok()
				.entity(
						localizacaoMotoristaDao.recuperar(id))
				.build();
	}

	@DELETE
	@Path("/{id}")
	public Response excluir(@PathParam("id") Long id) throws Exception {
		localizacaoMotoristaDao.excluir(
				localizacaoMotoristaDao.recuperar(id));
		return Response.ok()
				.build();
	}

}