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

import dao.UltimaLocalizacaoMotoristaDao;
import modelo.UltimaLocalizacaoMotorista;
import servico.UltimaLocalizacaoMotoristaService;
import util.Ejb;

@Path("/localizacaoMotorista")
public class LocalizacaoMotoristaRest {

	private UltimaLocalizacaoMotoristaService ultimaLocalizacaoMotoristaService;
	private UltimaLocalizacaoMotoristaDao ultimaLocalizacaoMotoristaDao; // para testar funcionalidades que n�o estar�o no service.

	public LocalizacaoMotoristaRest() {
		ultimaLocalizacaoMotoristaService = Ejb.lookup(UltimaLocalizacaoMotoristaService.class);
		ultimaLocalizacaoMotoristaDao = Ejb.lookup(UltimaLocalizacaoMotoristaDao.class);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvar(@Context HttpServletRequest httpServletRequest, 
			UltimaLocalizacaoMotorista ultimaLocalizacaoMotorista) throws Exception {
		return Response.ok()
				.entity(
						ultimaLocalizacaoMotoristaDao
						.salvar(ultimaLocalizacaoMotorista))
				.build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar()  throws Exception {
		return Response.ok()
				.entity(
						ultimaLocalizacaoMotoristaDao.listar())
				.build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recuperar(@PathParam("id") Long id) throws Exception{
		return Response.ok()
				.entity(
						ultimaLocalizacaoMotoristaDao.recuperar(id))
				.build();
	}

	@DELETE
	@Path("/{id}")
	public Response excluir(@PathParam("id") Long id) throws Exception {
		ultimaLocalizacaoMotoristaDao.excluir(
				ultimaLocalizacaoMotoristaDao.recuperar(id));
		return Response.ok()
				.build();
	}

}