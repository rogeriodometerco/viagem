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

import dao.MotoristaDao;
import dao.UsuarioDao;
import modelo.Motorista;
import modelo.Usuario;
import util.Ejb;

@Path("/motorista")
public class MotoristaRest {

	//private MotoristaService motoristaService;
	private MotoristaDao motoristaDao; // para testar funcionalidades que não estarão no service.
	private UsuarioDao usuarioDao; // por enquanto vai criar um usuário aleatório

	public MotoristaRest() {
		//motoristaService = Ejb.lookup(MotoristaService.class);
		motoristaDao = Ejb.lookup(MotoristaDao.class);
		usuarioDao = Ejb.lookup(UsuarioDao.class);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrar(@Context HttpServletRequest httpServletRequest, 
			Motorista motorista) throws Exception {
		// TODO Criação do usuário é temporária, retirar este bloco.
		/*
		Usuario usuario = new Usuario();
		usuario.setLogin("1");
		usuario.setSenha("1");
		usuario.setAtivo(true);
		usuarioDao.salvar(usuario);
		motorista.setUsuario(usuario);
		*/
		return Response.ok()
				.entity(
						motoristaDao
						.salvar(motorista))
				.build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar()  throws Exception {
		return Response.ok()
				.entity(
						motoristaDao.listar())
				.build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recuperar(@PathParam("id") Long id) throws Exception{
		return Response.ok()
				.entity(
						motoristaDao.recuperar(id))
				.build();
	}

	@DELETE
	@Path("/{id}")
	public Response excluir(@PathParam("id") Long id) throws Exception {
		motoristaDao.excluir(
				motoristaDao.recuperar(id));
		return Response.ok()
				.build();
	}

}