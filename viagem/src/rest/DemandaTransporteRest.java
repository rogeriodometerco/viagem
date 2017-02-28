package rest;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.Consumes;
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

import exception.AppException;
import modelo.DemandaTransporte;
import modelo.TransportadorDemandaAutorizado;
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

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response alterar(@PathParam("id") Long id, Map<String, Object> dadosAlterados) throws Exception {

		try {
			Iterator<Entry<String, Object>> i = dadosAlterados.entrySet().iterator();
			while (i.hasNext()) {
				Entry e = i.next();
				System.out.println(e.getKey() + " - " + e.getValue() );
			}
			DemandaTransporte demanda = demandaTransporteService.recuperar(id);
			demanda.setQuantidade(Integer.valueOf((String)dadosAlterados.get("quantidade")));
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
									demandaTransporteService.recuperar(id)))
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
						//return false;
						return field.getDeclaringClass().equals(TransportadorDemandaAutorizado.class) && field.getName().equals("demanda")
								|| field.getName().equals("administradores")
								|| field.getName().equals("senha")
								|| field.getName().equals("perfis")
								;
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