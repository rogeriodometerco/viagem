package rest;

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

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dto.Listagem;
import modelo.Estabelecimento;
import modelo.Municipio;
import modelo.UF;
import servico.EstabelecimentoService;
import util.Ejb;

@Path("/estabelecimento")
public class EstabelecimentoRest {

	private EstabelecimentoService estabelecimentoService;

	public EstabelecimentoRest() {
		estabelecimentoService = Ejb.lookup(EstabelecimentoService.class);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvar(@Context HttpServletRequest httpServletRequest, 
			Estabelecimento estabelecimento) throws Exception {

		try {
			return Response.ok()
					.entity(
							toJson(
									//new Gson().toJson(toJsonObjectDetalhado(
									estabelecimentoService.salvar(estabelecimento)))
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

		Listagem<Estabelecimento> listagem = null;
		try {
			// Sem critério de pesquisa.
			if (iniciandoPor == null || iniciandoPor.trim().equals("")) {
				listagem  = estabelecimentoService.listarOrdenadoPorNome(pagina, tamanhoPagina);

				// Com critério de pesquisa.
			} else {
				listagem  = estabelecimentoService.listarPorNomeOrdenadoPorNome(pagina, tamanhoPagina, iniciandoPor);
			}
			return Response.ok()
					.entity(
							toJson(listagem))
					//new Gson().toJson(toJsonObject(listagem)))
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
									//new Gson().toJson(toJsonObjectDetalhado(
									estabelecimentoService.recuperar(id)))
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
								field.getDeclaringClass().equals(Estabelecimento.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("nome")
										|| field.getName().equals("municipio")
										)
								|| field.getDeclaringClass().equals(Municipio.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("nome")
										|| field.getName().equals("uf")
										)
								|| field.getDeclaringClass().equals(UF.class)
								&& (
										field.getName().equals("nome")
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