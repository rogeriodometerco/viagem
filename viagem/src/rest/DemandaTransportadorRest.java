package rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import dao.DemandaTransportadorViewCatalogo;
import dto.Filtro;
import dto.Listagem;
import modelo.Conta;
import modelo.DemandaTransporte;
import modelo.Endereco;
import modelo.Estabelecimento;
import modelo.Municipio;
import modelo.Produto;
import modelo.TransportadorDemandaAutorizado;
import modelo.UF;
import modelo.UnidadeQuantidade;
import servico.DemandaTransporteService;
import util.Ejb;

@Path("/demandaTransporte/transportador")
public class DemandaTransportadorRest {

	private DemandaTransporteService demandaTransporteService;
	private DemandaTransportadorViewCatalogo catalogo;
	
	public DemandaTransportadorRest() {
		demandaTransporteService = Ejb.lookup(DemandaTransporteService.class);
		catalogo = Ejb.lookup(DemandaTransportadorViewCatalogo.class);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response criar(DemandaTransporte demandaTransporte) throws Exception {
		try {
			// Seta a demanda de transporte nos transportadores porque a página não submete.
			for (TransportadorDemandaAutorizado t: demandaTransporte.getTransportadores()) {
				t.setDemanda(demandaTransporte);
			}
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
	//		String json = "{p:1, t:5, q:[{chave: nome, valor: campo, operador: '='}, {chave: uf, valor: PR, operador: '='}], ordenacao: [{chave: nome, ordem: A}]}";
	public Response listar(@QueryParam("params") String jsonRequest)  throws Exception {
		try {
			Filtro filtro = new Filtro();
			filtro.inicializar(jsonRequest);
			// TODO Acrescentar filtro por transportador
			
			return Response.ok()
					.entity(
							catalogo.listar(filtro))
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
	/*
	{
		"quantidade": 1500
	}
	 */
	public Response alterarQuantidade(@PathParam("id") Long id, Map<String, Object> dados) throws Exception {

		try {
			DemandaTransporte demanda = demandaTransporteService.recuperar(id);
			demanda.setQuantidade(Integer.valueOf(dados.get("quantidade").toString()));
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
	/*
	[
		{
			"transportador" : {
				"id": 123
			}
		},
		{
			"transportador" : {
				"id": 456
			}
		}
	 ]
	 */
	public Response adicionarTransportadores(@PathParam("id") Long id, List<TransportadorDemandaAutorizado> transportadores) throws Exception {

		try {
			List<Conta> contasTransportador = new ArrayList<Conta>();
			for (TransportadorDemandaAutorizado transportadorDemanda: transportadores) {
				contasTransportador.add(transportadorDemanda.getTransportador());
			}
			return Response.ok()
					.entity(
							toJson(
									demandaTransporteService.adicionarTransportadores(id, contasTransportador)))
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
			demandaTransporteService.inativarTransportadores(id, transportadores);
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
										|| field.getName().equals("transportadores")
										)
								|| field.getDeclaringClass().equals(Estabelecimento.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("nome")
										|| field.getName().equals("endereco")
										)
								|| field.getDeclaringClass().equals(Endereco.class)
								&& (
										field.getName().equals("municipio")
										|| field.getName().equals("latitude")
										|| field.getName().equals("longitude")
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
								|| field.getDeclaringClass().equals(Produto.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("nome")
										)
								|| field.getDeclaringClass().equals(UnidadeQuantidade.class)
								&& (
										field.getName().equals("abreviacao")
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
										|| field.getName().equals("ativo")
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