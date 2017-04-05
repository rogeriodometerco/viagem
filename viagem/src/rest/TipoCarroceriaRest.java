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
import modelo.TipoCarroceria;
import servico.TipoCarroceriaService;
import util.Ejb;

@Path("/tipoCarroceria")
public class TipoCarroceriaRest {

	private TipoCarroceriaService tipoCarroceriaService;

	public TipoCarroceriaRest() {
		tipoCarroceriaService = Ejb.lookup(TipoCarroceriaService.class);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvar(@Context HttpServletRequest httpServletRequest, 
			TipoCarroceria tipoCarroceria) throws Exception {

		try {
			return Response.ok()
					.entity(
							toJson(
									tipoCarroceriaService.salvar(tipoCarroceria)))
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

		Listagem<TipoCarroceria> listagem = null;
		try {
			// Sem critério de pesquisa.
			if (iniciandoPor == null || iniciandoPor.trim().equals("")) {
				listagem  = tipoCarroceriaService.listarOrdenadoPorNome(pagina, tamanhoPagina);

				// Com critério de pesquisa.
			} else {
				listagem  = tipoCarroceriaService.listarPorNomeOrdenadoPorNome(pagina, tamanhoPagina, iniciandoPor);
			}
			return Response.ok()
					.entity(
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
									tipoCarroceriaService.recuperar(id)))
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
								field.getDeclaringClass().equals(TipoCarroceria.class)
								&& (
										field.getName().equals("id")
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