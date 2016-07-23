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

import dao.UsuarioDao;
import modelo.Usuario;
import util.Ejb;

@Path("/usuario")
public class UsuarioRest {

	private UsuarioDao usuarioDao;

	public UsuarioRest() {
		usuarioDao = Ejb.lookup(UsuarioDao.class);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrar(@Context HttpServletRequest httpServletRequest, 
			Usuario usuario) throws Exception {
		return Response.ok()
				.entity(
						usuarioDao
						.salvar(usuario))
				.build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar()  throws Exception {
		return Response.ok()
				.entity(
						usuarioDao.listar())
				.build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recuperar(@PathParam("id") Long id) throws Exception{
		return Response.ok()
				.entity(
						usuarioDao.recuperar(id))
				.build();
	}

	@DELETE
	@Path("/{id}")
	public Response excluir(@PathParam("id") Long id) throws Exception {
		usuarioDao.excluir(
				usuarioDao.recuperar(id));
		return Response.ok()
				.build();
	}

}