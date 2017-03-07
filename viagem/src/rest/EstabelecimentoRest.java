package rest;

import java.util.List;

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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import dto.Listagem;
import modelo.Estabelecimento;
import modelo.Municipio;
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
							new Gson().toJson(toJsonObjectDetalhado(
									estabelecimentoService.salvar(estabelecimento))))
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
							new Gson().toJson(toJsonObject(listagem)))
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
							new Gson().toJson(toJsonObjectDetalhado(
									estabelecimentoService.recuperar(id))))
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}
	
	private JsonObject toJsonObject(Listagem<Estabelecimento> listagem) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("count", listagem.getCount());
		jsonObject.addProperty("pagina", listagem.getPagina());
		jsonObject.add("lista", toJsonArrayResumido(listagem.getLista()));
		return jsonObject;
	}

	private JsonArray toJsonArrayResumido(List<Estabelecimento> lista) {
		JsonArray jsonArray = new JsonArray();
		for (Estabelecimento e: lista) {
			jsonArray.add(toJsonObjectResumido(e));
		}
		return jsonArray;
	}
	
	private JsonObject toJsonObjectResumido(Estabelecimento estabelecimento) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("id", estabelecimento.getId());
		jsonObject.addProperty("nome", estabelecimento.getNome());
		jsonObject.addProperty("municipio", estabelecimento.getMunicipio().getNome());
		jsonObject.addProperty("uf", estabelecimento.getMunicipio().getUf().getAbreviatura());
		return jsonObject;
	}
	
	private JsonObject toJsonObjectDetalhado(Estabelecimento estabelecimento) {
		JsonObject jsonObject = new JsonObject();
		JsonObject jsonMunicipio = new JsonObject();

		jsonObject.addProperty("id", estabelecimento.getId());
		jsonObject.addProperty("nome", estabelecimento.getNome());
		jsonObject.add("municipio", jsonMunicipio);

		Municipio municipio = estabelecimento.getMunicipio();
		jsonMunicipio.addProperty("id", municipio.getId());
		jsonMunicipio.addProperty("nome", municipio.getId());
		jsonMunicipio.addProperty("uf", municipio.getUf().getAbreviatura());
		
		return jsonObject;
	}
}