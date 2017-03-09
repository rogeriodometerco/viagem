package rest;

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
import dto.ParametrosListagem;
import modelo.Estabelecimento;
import modelo.Municipio;
import modelo.UF;
import servico.MunicipioService;
import util.Ejb;

@Path("/municipio")
public class MunicipioRest {

	private MunicipioService municipioService;

	public MunicipioRest() {
		municipioService = Ejb.lookup(MunicipioService.class);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvar(@Context HttpServletRequest httpServletRequest, 
			Municipio municipio) throws Exception {

		try {
			return Response.ok()
					.entity(
							toJson(
									municipioService.salvar(municipio)))
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
			@QueryParam("q") String iniciandoPor)  throws Exception {

		try {
			Listagem<Municipio> result = null;
			if (iniciandoPor == null || iniciandoPor.trim().equals("")) {
				result = municipioService.listarOrdenadoPorNome(pagina, tamanhoPagina);

			} else {
				result = municipioService.listarPorNomeOrdenadoPorNome(pagina, tamanhoPagina, iniciandoPor);
			}
			return Response.ok()
					.entity(
							toJson(result))
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
									municipioService.recuperar(id)))
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}

	/*
	// apenas teste
	@GET
	@Path("/lista")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar2() throws Exception{
		System.out.println("listar2()");
		String json = "{p:1, t:5, filtros:[{chave: nome, valor: Campo, restricao: inicia}], ordenacao: [{chave: nome, ordem: A}]}";
		ParametrosListagem p = new Gson().fromJson(json, ParametrosListagem.class);
		try {
			return Response.ok()
					.entity(
							municipioService.listar(p))
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}
	 */

	@DELETE
	@Path("/{id}")
	public Response excluir(@PathParam("id") Long id) throws Exception {
		try {
			municipioService.excluir(id);
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
				.setExclusionStrategies(new ExclusionStrategy() {

					@Override
					public boolean shouldSkipField(FieldAttributes field) {
						boolean serializar =
								field.getDeclaringClass().equals(Listagem.class)
								||
								field.getDeclaringClass().equals(Municipio.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("nome")
										|| field.getName().equals("uf")
										)
								|| field.getDeclaringClass().equals(UF.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("nome")
										|| field.getName().equals("abreviatura")
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