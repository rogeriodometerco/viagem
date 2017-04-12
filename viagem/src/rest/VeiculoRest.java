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
import modelo.ComponenteVeiculo;
import modelo.TipoCarroceria;
import modelo.TipoVeiculo;
import modelo.Veiculo;
import servico.VeiculoService;
import util.Ejb;

@Path("/veiculo")
public class VeiculoRest {

	private VeiculoService veiculoService;

	public VeiculoRest() {
		veiculoService = Ejb.lookup(VeiculoService.class);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvar(@Context HttpServletRequest httpServletRequest, 
			Veiculo veiculo) throws Exception {

		try {
			// Seta o veículo dos componentes, pois a página submete esta coleção sem este atributo por
			// já estar aninhado dentro do veículo.
			if (veiculo.getComponentes() != null) {
				for (ComponenteVeiculo c: veiculo.getComponentes()) {
					c.setVeiculo(veiculo);
				}
			}
			return Response.ok()
					.entity(
							toJson(
									veiculoService.salvar(veiculo)))
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
			@QueryParam("q") String placaContendo)  throws Exception {

		Listagem<Veiculo> listagem = null;
		try {
			// Sem critério de pesquisa.
			if (placaContendo == null || placaContendo.trim().equals("")) {
				listagem  = veiculoService.listarOrdenadoPorPlaca(pagina, tamanhoPagina);

				// Com critério de pesquisa.
			} else {
				listagem  = veiculoService.listarPorPlacaOrdenadoPorPlaca(pagina, tamanhoPagina, placaContendo);
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
									veiculoService.recuperar(id)))
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
								field.getDeclaringClass().equals(Veiculo.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("tipo")
										|| field.getName().equals("componentes")
										)
								||
								field.getDeclaringClass().equals(TipoVeiculo.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("nome")
										)
								||
								field.getDeclaringClass().equals(ComponenteVeiculo.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("placa")
										|| field.getName().equals("quantidadeEixos")
										|| field.getName().equals("tipoCarroceria")
										|| field.getName().equals("posicaoNoVeiculo")
										)
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