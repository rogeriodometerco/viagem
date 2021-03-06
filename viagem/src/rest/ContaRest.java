package rest;

import java.util.ArrayList;
import java.util.List;

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

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dto.Listagem;
import enums.Perfil;
import json.JsonModelo;
import modelo.AdminConta;
import modelo.Conta;
import modelo.PerfilConta;
import modelo.Usuario;
import servico.ContaService;
import util.Ejb;

@Path("/conta")
public class ContaRest {

	private ContaService contaService;

	public ContaRest() {
		contaService = Ejb.lookup(ContaService.class);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvar(@Context HttpServletRequest httpServletRequest, 
			Conta conta) throws Exception {

		try {
			/*
			Gson g = new GsonBuilder()
					.registerTypeAdapter(Perfil.class, new PerfilJsonAdapter())
					.registerTypeAdapter(PerfilConta.class, new PerfilContaJsonAdapter())
					.registerTypeAdapter(Long.class, new LongTypeAdapter())
					.create();

			Conta conta = g.fromJson(json, Conta.class);
			 */
			//Conta conta = new Conta();
			//conta.setNome(contaDto.getNome());

			Conta result = null; 
			if (conta.getId() != null) {
				result = contaService.recuperar(conta.getId());
				result.setNome(conta.getNome());
				result.setPerfis(conta.getPerfis());
			} else {
				result = conta;
			}

			// Seta o atributo "conta" das entidades em "perfis" porque a página submete esta coleção
			// sem este atributo.
			if (result.getPerfis() != null) {
				for (PerfilConta p: result.getPerfis()) {
					p.setConta(result);
				}
			}
			return Response.ok()
					.entity(toJson(
							contaService.salvar(result)))
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
			@QueryParam("t") @DefaultValue("10") int tamanhoPagina, 
			@QueryParam("q") String nomeContendo,
			@QueryParam("perfil") Perfil perfil)  throws Exception {

		try {
			Listagem<Conta> listagem = null;
			if (nomeContendo == null || nomeContendo.trim().equals("")) {
				if (perfil == null) {
					listagem = contaService.listarOrdenadoPorNome(pagina, tamanhoPagina);
				} else {
					listagem = contaService.listarPorPerfilOrdenadoPorNome(pagina, tamanhoPagina, perfil);
				}
			} else {
				if (perfil == null) {
					listagem = contaService.listarPorNomeOrdenadoPorNome(pagina, tamanhoPagina, nomeContendo);
				} else {
					listagem = contaService.listarPorNomePerfilOrdenadoPorNome(pagina, tamanhoPagina, nomeContendo, perfil);
				}
			}
			return Response.ok().entity(
					toJson(listagem))
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
							toJson(
							//new JsonModelo().toJson(
									contaService.recuperar(id)))
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}

	@DELETE
	@Path("/{id}")
	public Response inativar(@PathParam("id") Long id) throws Exception {
		try {
			contaService.inativar(id);
			return Response.ok()
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}

	@POST
	@Path("/{id}/administradores")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	/*
	[
		{
			"usuario" : {
				"id": 123
			}
		},
		{
			"usuario" : {
				"id": 456
			}
		}
	 ]
	 */
	public Response adicionarAdministradores(@PathParam("id") Long id, List<AdminConta> administradores) 
			throws Exception {

		try {
			List<Usuario> usuarios = new ArrayList<Usuario>();
			for (AdminConta admin: administradores) {
				usuarios.add(admin.getUsuario());
			}
			return Response.ok()
					.entity(
							toJson(
									contaService.adicionarAdministradores(id, usuarios)))
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}


	@GET
	@Path("/{id}/administradores")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response recuperarAdministradores(@PathParam("id") Long id) throws Exception {

		try {
			return Response.ok()
					.entity(
							toJson(
									contaService.recuperar(id).getAdministradores()))
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}


	@DELETE
	@Path("/{id}/administradores")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response removerAdministradores(@PathParam("id") Long id, List<AdminConta> administradores) throws Exception {

		try {
			contaService.removerAdministradores(id, administradores);
			return Response.ok()
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}

	private String toJson(Object source) {
		Gson g = new GsonBuilder()
				//.registerTypeAdapter(Perfil.class, new PerfilJsonAdapter())
				//.registerTypeAdapter(PerfilConta.class, new PerfilContaJsonAdapter())
				.setExclusionStrategies(new ExclusionStrategy() {

					@Override
					public boolean shouldSkipField(FieldAttributes field) {
						boolean serializar =
								field.getDeclaringClass().equals(Listagem.class)
								||
								field.getDeclaringClass().equals(Conta.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("nome")
										|| field.getName().equals("ativa")
										|| field.getName().equals("perfis")
										)
								|| field.getDeclaringClass().equals(PerfilConta.class)
								&& (
										field.getName().equals("perfil")
										)
								|| field.getDeclaringClass().equals(AdminConta.class)
								&& (
										field.getName().equals("usuario")
										)
								|| field.getDeclaringClass().equals(Usuario.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("login")
										|| field.getName().equals("nome")
										);
						return !serializar;

					}

					@Override
					public boolean shouldSkipClass(Class<?> clazz) {
						// TODO Auto-generated method stub
						return false;
					}
				})
				.create();
		return g.toJson(source);
	}

}