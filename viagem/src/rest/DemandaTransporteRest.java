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

import com.google.gson.Gson;

import modelo.DemandaTransporte;
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
	public Response salvar(@Context HttpServletRequest httpServletRequest, 
			DemandaTransporte demandaTransporte) throws Exception {

		try {
			return Response.ok()
					.entity(
							new Gson().toJson(
									demandaTransporteService.salvar(demandaTransporte)))
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
					.entity(
									demandaTransporteService.listarOrdenadoPorIdDescendente(pagina, tamanhoPagina))
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
							new Gson().toJson(
									demandaTransporteService.recuperar(id)))
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}


}