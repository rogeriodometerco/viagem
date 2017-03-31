package rest;

import java.util.Map;

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

import dto.Listagem;
import modelo.Conta;
import modelo.Usuario;
import servico.ContaService;
import servico.UsuarioService;
import util.Ejb;

@Path("/usuario")
public class UsuarioRest {

	private UsuarioService usuarioService;
	private ContaService contaService;

	public UsuarioRest() {
		usuarioService = Ejb.lookup(UsuarioService.class);
		contaService = Ejb.lookup(ContaService.class);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrar(@Context HttpServletRequest httpServletRequest, 
			Map<String, Object> usuarioMap) throws Exception {
		
		Usuario usuarioRequest = new Usuario();
		usuarioRequest.setId((Long)usuarioMap.get("id"));
		usuarioRequest.setLogin((String)usuarioMap.get("login"));
		usuarioRequest.setNome((String)usuarioMap.get("nome"));
		usuarioRequest.setSenha((String)usuarioMap.get("senha"));
		String confirmacaoSenha = (String)usuarioMap.get("confirmacaoSenha");
		Long contaId = (Long)usuarioMap.get("contaId");
		Conta conta = contaService.recuperar(contaId);
		
		return Response.ok()
				.entity(
						usuarioService.criar(usuarioRequest, confirmacaoSenha, conta))
				.build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(
			@QueryParam("p") @DefaultValue("1") int pagina, 
			@QueryParam("t") @DefaultValue("10") int tamanhoPagina, 
			@QueryParam("q") String nomeContendo
			)  throws Exception {

		Listagem<Usuario> listagem = null;

		if (nomeContendo == null || nomeContendo.trim().equals("")) {
			listagem = usuarioService.listarOrdenadoPorNome(pagina, tamanhoPagina);
		} else {
			listagem = usuarioService.listarPorNomeOrdenadoPorNome(pagina, tamanhoPagina, nomeContendo);
		}
		return Response.ok()
				.entity(
						listagem)
				.build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recuperar(@PathParam("id") Long id) throws Exception{
		return Response.ok()
				.entity(
						usuarioService.recuperar(id))
				.build();
	}
	
	/*

	@DELETE
	@Path("/{id}")
	public Response inativar(@PathParam("id") Long id) throws Exception {
		usuarioDao.excluir(
				usuarioDao.recuperar(id));
		return Response.ok()
				.build();
	}
	*/

}