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

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.DispositivoDao;
import dao.LocalizacaoDao;
import dao.MotoristaDao;
import dto.Listagem;
import modelo.Dispositivo;
import modelo.Evento;
import modelo.Localizacao;
import modelo.Motorista;
import modelo.Viagem;
import servico.EventoService;
import util.Ejb;

@Path("/evento")
public class EventoRest {

	//private LocalizacaoService localizacaoService;
	private LocalizacaoDao localizacaoDao; // para testar funcionalidades que n�o estar�o no service.
	private MotoristaDao motoristaDao; // para testar funcionalidades que n�o estar�o no service.
	private DispositivoDao dispositivoDao; // para testar funcionalidades que n�o estar�o no service.
	private EventoService eventoService;

	public EventoRest() {
		//localizacaoService = Ejb.lookup(LocalizacaoService.class);
		localizacaoDao = Ejb.lookup(LocalizacaoDao.class);
		motoristaDao = Ejb.lookup(MotoristaDao.class);
		dispositivoDao = Ejb.lookup(DispositivoDao.class);
		eventoService = Ejb.lookup(EventoService.class);
	}

	/*
	{
		eventos: [
			{
				"tipo" : "LOCALIZACAO",
				"dados" : {
					"dataHoraRegistro": "21/03/2017 17:22",
					"localizacao": {
						"dispositivo": {
							"id": "9944903"
						},
						"motorista": {
							"id": "3339"
						},
						"dataHora": "21/03/2017 17:22",
						"latitude": "-24.5050",
						"longitude": "-52.230291",
						"velocidade": "63"
					}
				}
			},
			{
				"tipo" : "ACEITE_VIAGEM",
				"dados" : {
					"dataHoraRegistro": "21/03/2017 17:22",
					"localizacao": {
						"dispositivo": {
							"id": "9944903"
						},
						"motorista": {
							"id": "3339"
						},
						"dataHora": "21/03/2017 17:22",
						"latitude": "-24.5050",
						"longitude": "-52.230291",
						"velocidade": "63"
					},
					"viagem": {
						"id": "9494"
					},
					"dataHoraAceite": "31/03/2017 07:22"
					
				}
			}
		]
	
	}
	 */

	
	@POST
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
			localizacao.setDataHora(format.parse((String)evento.get("dhEvento")));
			localizacao.setLatitude(Double.valueOf((String)localizacaoRequest.get("lat")));
			localizacao.setLongitude(Double.valueOf((String)localizacaoRequest.get("lng")));
			localizacao.setVelocidade(Double.valueOf((Integer)evento.get("velocidade")));
			
			// Recupera um dispositivo ou cria se n�o existir, apenas para salvar o evento.
			Dispositivo dispositivo = null;
			List<Dispositivo> dispositivos = dispositivoDao.listar();
			if (dispositivos != null) {
				dispositivo = dispositivos.get(0);
			} else {
				dispositivo = new Dispositivo();
				dispositivo.setIdentificacao("9943-5091");
				dispositivo = dispositivoDao.salvar(dispositivo);
			}
			// Recupera um motorista ou cria se n�o existir, apenas para salvar o evento.
			Motorista motorista = null;
			List<Motorista> motoristas = motoristaDao.listar();
			if (motoristas != null) {
				motorista = motoristas.get(0);
			} else {
				motorista = new Motorista();
				motorista.setNome("Rogerio");
				motorista = motoristaDao.salvar(motorista);
			}
			
		//	localizacao.setMotorista(motorista);
			//localizacao.setDispositivo(dispositivoDao.listar().get(0));
			
			//localizacaoService.registrar(localizacao);
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
						toJson(
								eventoService.listar()))
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

	private String toJson(Object source) {
		
		Gson g = new GsonBuilder()
				.serializeNulls()
				.setExclusionStrategies(new ExclusionStrategy() {

					@Override
					public boolean shouldSkipField(FieldAttributes field) {
						boolean serializar =
								field.getDeclaringClass().equals(Listagem.class)
								||
								field.getDeclaringClass().equals(Evento.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("dataHoraRegistro")
										|| field.getName().equals("localizacao")
										|| field.getName().equals("dispositivo")
										)
								|| field.getDeclaringClass().equals(Localizacao.class)
								&& (
										field.getName().equals("latitude")
										|| field.getName().equals("longitude")
										)
								|| field.getDeclaringClass().equals(Viagem.class)
								&& (
										field.getName().equals("id")
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