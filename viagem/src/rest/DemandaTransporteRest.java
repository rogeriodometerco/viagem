package rest;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dto.Listagem;
import modelo.Conta;
import modelo.DemandaTransporte;
import modelo.Estabelecimento;
import modelo.Municipio;
import modelo.TransportadorDemandaAutorizado;
import modelo.UF;
import servico.DemandaTransporteService;
import util.Ejb;

@Path("/demandaTransporte")
public class DemandaTransporteRest {

	private DemandaTransporteService demandaTransporteService;

	public DemandaTransporteRest() {
		demandaTransporteService = Ejb.lookup(DemandaTransporteService.class);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response criar(DemandaTransporte demandaTransporte) throws Exception {

		try {
			return Response.ok()
					.entity(
							toJson(
									demandaTransporteService.criar(demandaTransporte)))
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
			@QueryParam("t") @DefaultValue("10") int tamanhoPagina)  throws Exception {

		try {
			return Response.ok()
					.entity(toJson(
							demandaTransporteService.listarOrdenadoPorIdDescendente(pagina, tamanhoPagina)))
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
									demandaTransporteService.recuperar(id)))
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}

	@PUT
	@Path("/{id}/quantidade")
	@Produces(MediaType.APPLICATION_JSON)
	public Response alterarQuantidade(@PathParam("id") Long id, Integer quantidade) throws Exception {

		try {
			DemandaTransporte demanda = demandaTransporteService.recuperar(id);
			demanda.setQuantidade(quantidade);
			return Response.ok()
					.entity(
							toJson(
									demandaTransporteService.alterar(demanda)))
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}

	@POST
	@Path("/{id}/transportadores")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response adicionarTransportadores(@PathParam("id") Long id, List<Conta> transportadores) throws Exception {

		try {
			return Response.ok()
					.entity(
							toJson(
									demandaTransporteService.adicionarTransportadores(id, transportadores)))
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}

	@GET
	@Path("/{id}/transportadores")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response recuperarTransportadores(@PathParam("id") Long id) throws Exception {

		try {
			return Response.ok()
					.entity(
							toJson(
									demandaTransporteService.recuperar(id).getTransportadores()))
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}

	@DELETE
	@Path("/{id}/transportadores")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response inativarTransportadores(@PathParam("id") Long id, List<TransportadorDemandaAutorizado> transportadores) throws Exception {

		try {
			return Response.ok()
					.entity(
							toJson(
									demandaTransporteService.inativarTransportadores(id, transportadores)))
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
								field.getDeclaringClass().equals(DemandaTransporte.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("origem")
										|| field.getName().equals("destino")
										|| field.getName().equals("produto")
										|| field.getName().equals("quantidade")
										|| field.getName().equals("unidadeQuantidade")
										|| field.getName().equals("status")
										|| field.getName().equals("tomador")
										)
								|| field.getDeclaringClass().equals(Estabelecimento.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("nome")
										|| field.getName().equals("municipio")
										)
								|| field.getDeclaringClass().equals(Municipio.class)
								&& (
										field.getName().equals("nome")
										|| field.getName().equals("uf")
										)
								|| field.getDeclaringClass().equals(UF.class)
								&& (
										field.getName().equals("abreviatura")
										)

								|| field.getDeclaringClass().equals(Conta.class)
								&& (
										field.getName().equals("id") 
										|| field.getName().equals("nome")
										)
								|| field.getDeclaringClass().equals(TransportadorDemandaAutorizado.class)
								&& (
										field.getName().equals("id") 
										|| field.getName().equals("transportador")
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