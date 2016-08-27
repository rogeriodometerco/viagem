package rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.DispositivoDao;
import dao.LocalizacaoDao;
import dao.MotoristaDao;
import modelo.Localizacao;
import servico.LocalizacaoService;
import util.Ejb;

@Path("/mobile")
public class MobileRest {

	private LocalizacaoService localizacaoService;
	private LocalizacaoDao localizacaoDao; // para testar funcionalidades que não estarão no service.
	private MotoristaDao motoristaDao; // para testar funcionalidades que não estarão no service.
	private DispositivoDao dispositivoDao; // para testar funcionalidades que não estarão no service.

	public MobileRest() {
		localizacaoService = Ejb.lookup(LocalizacaoService.class);
		localizacaoDao = Ejb.lookup(LocalizacaoDao.class);
		motoristaDao = Ejb.lookup(MotoristaDao.class);
		dispositivoDao = Ejb.lookup(DispositivoDao.class);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response testar(@Context HttpServletRequest httpServletRequest, 
			Localizacao localizacao) throws Exception {
		return Response.ok()
				.entity(
						localizacaoService
						.registrar(localizacao))
				.build();
	}
	
	@POST
	@Path("/evento")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrar(@Context HttpServletRequest httpServletRequest, 
			Map<String, Object> request) throws Exception {

/*
 * {
 * 	"events":[{
 * 		"type":"LocaLizacaoEventoMobile",
 * 		"altitude":10,
 * 		"precisao":10,
 * 		"provedor":"GPS",
 * 		"velocidade":0,
 * 		"dhEvento":"Aug 26, 2016 9:29:29 PM",
 * 		"id":1,
 * 		"localizacao":{
 * 			"latitude":"29031920",
 * 			"longitude":"-213123"}
 * 		}
 * 	]
 * }
 */
		List<Map<String, Object>> eventos = (List<Map<String, Object>>)request.get("events");
		Localizacao localizacao = null;
		DateFormat format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a", Locale.US);

		for (Map<String, Object> evento: eventos) {
			Map<String, Object> localizacaoRequest = (Map<String, Object>)evento.get("localizacao");
			localizacao = new Localizacao();
			localizacao.setData(format.parse((String)evento.get("dhEvento")));
			localizacao.setLat((Double)localizacaoRequest.get("lat"));
			localizacao.setLng((Double)localizacaoRequest.get("lng"));
			localizacao.setVelocidade(Double.valueOf((Integer)evento.get("velocidade")));
			
			localizacao.setMotorista(motoristaDao.listar().get(0));
			localizacao.setDispositivo(dispositivoDao.listar().get(0));
			
			localizacaoService.registrar(localizacao);
		}
		Map<String, String> result = new HashMap<String, String>();
		result.put("Status", "OK");
		return Response.ok()
				.entity(result)
				.build();
	}


	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar()  throws Exception {
		return Response.ok()
				.entity(
						localizacaoDao.listar())
				.build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recuperar(@PathParam("id") Long id) throws Exception{
		return Response.ok()
				.entity(
						localizacaoDao.recuperar(id))
				.build();
	}

	@DELETE
	@Path("/{id}")
	public Response excluir(@PathParam("id") Long id) throws Exception {
		localizacaoDao.excluir(
				localizacaoDao.recuperar(id));
		return Response.ok()
				.build();
	}

}