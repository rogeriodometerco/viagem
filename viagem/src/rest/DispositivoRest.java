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

import dao.DispositivoDao;
import modelo.Dispositivo;
import util.Ejb;

@Path("/dispositivo")
public class DispositivoRest {

	//private DispositivoService dispositivoService;
	private DispositivoDao dispositivoDao; // para testar funcionalidades que não estarão no service.

	public DispositivoRest() {
		//dispositivoService = Ejb.lookup(DispositivoService.class);
		dispositivoDao = Ejb.lookup(DispositivoDao.class);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrar(@Context HttpServletRequest httpServletRequest, 
			Dispositivo dispositivo) throws Exception {
		return Response.ok()
				.entity(
						dispositivoDao
						.salvar(dispositivo))
				.build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar()  throws Exception {
		return Response.ok()
				.entity(
						dispositivoDao.listar())
				.build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recuperar(@PathParam("id") Long id) throws Exception{
		return Response.ok()
				.entity(
						dispositivoDao.recuperar(id))
				.build();
	}

	@DELETE
	@Path("/{id}")
	public Response excluir(@PathParam("id") Long id) throws Exception {
		dispositivoDao.excluir(
				dispositivoDao.recuperar(id));
		return Response.ok()
				.build();
	}

}