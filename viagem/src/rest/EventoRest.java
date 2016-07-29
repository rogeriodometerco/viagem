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

import dao.EventoDaoGeral;
import modelo.EventoGeral;
import modelo.Usuario;
import servico.EventoServiceGeral;
import util.Ejb;

@Path("/evento")
public class EventoRest {

	private EventoServiceGeral eventoServiceGeral;
	private EventoDaoGeral eventoDaoGeral; // para testar funcionalidades que não terão no serviço.

	public EventoRest() {
		eventoServiceGeral = Ejb.lookup(EventoServiceGeral.class);
		eventoDaoGeral = Ejb.lookup(EventoDaoGeral.class);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrar(@Context HttpServletRequest httpServletRequest, 
			EventoGeral eventoGeral) throws Exception {
		return Response.ok()
				.entity(
						eventoServiceGeral
						.registrar(eventoGeral))
				.build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar()  throws Exception {
		return Response.ok()
				.entity(
						eventoDaoGeral.listar())
				.build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recuperar(@PathParam("id") Long id) throws Exception{
		return Response.ok()
				.entity(
						eventoDaoGeral.recuperar(id))
				.build();
	}

	@DELETE
	@Path("/{id}")
	public Response excluir(@PathParam("id") Long id) throws Exception {
		eventoDaoGeral.excluir(
				eventoDaoGeral.recuperar(id));
		return Response.ok()
				.build();
	}

}