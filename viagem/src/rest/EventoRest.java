package rest;

import javax.annotation.security.PermitAll;
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

import dao.EventoDao;
import modelo.Evento;
import modelo.Usuario;
import servico.EventoService;
import util.Ejb;

@Path("/evento")
public class EventoRest {

	private EventoService eventoService;
	private EventoDao eventoDao; // para testar funcionalidades que não terão no serviço.

	public EventoRest() {
		eventoService = Ejb.lookup(EventoService.class);
		eventoDao = Ejb.lookup(EventoDao.class);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrar(@Context HttpServletRequest httpServletRequest, 
			Evento evento) throws Exception {
		return Response.ok()
				.entity(
						eventoService
						.registrar(evento))
				.build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar()  throws Exception {
		return Response.ok()
				.entity(
						eventoDao.listar())
				.build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recuperar(@PathParam("id") Long id) throws Exception{
		return Response.ok()
				.entity(
						eventoDao.recuperar(id))
				.build();
	}

	@DELETE
	@Path("/{id}")
	public Response excluir(@PathParam("id") Long id) throws Exception {
		eventoDao.excluir(
				eventoDao.recuperar(id));
		return Response.ok()
				.build();
	}

}