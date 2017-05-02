package rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.DemandaTomadorViewCatalogo;
import dao.DemandaTransportadorViewCatalogo;
import dao.OperacaoViagemLista;
import dto.Filtro;
import dto.Listagem;
import dto.OperacaoViagemRequest;
import enums.StatusOperacaoViagem;
import modelo.Conta;
import modelo.DemandaTomadorView;
import modelo.DemandaTransportadorView;
import modelo.Estabelecimento;
import util.Ejb;

@Path("/teste")
public class TestePost {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response testar() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("Status", "OK");
		return Response.ok()
				.entity(result)
				.build();
	}
	

	private OperacaoViagemLista operacaoViagemLista;
	private DemandaTomadorViewCatalogo demandaTomadorViewCatalogo;
	private DemandaTransportadorViewCatalogo demandaTransportadorViewCatalogo;

	public TestePost() {
		operacaoViagemLista= Ejb.lookup(OperacaoViagemLista.class);
		demandaTomadorViewCatalogo = Ejb.lookup(DemandaTomadorViewCatalogo.class);
		demandaTransportadorViewCatalogo = Ejb.lookup(DemandaTransportadorViewCatalogo.class);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar()  throws Exception {

		Estabelecimento estabelecimento = new Estabelecimento();
		estabelecimento.setId(94l);
		Conta transportador = new Conta();
		transportador.setId(71l);
		
		Conta motorista = new Conta();
		motorista.setId(415l);

		OperacaoViagemRequest request = new OperacaoViagemRequest();
		//request.setTipoOperacao(TipoOperacaoViagem.COLETA);
		//request.setEstabelecimento(estabelecimento);
		//request.setTransportador(transportador);
		//request.setMotorista(motorista);
		request.setStatusOperacaoViagem(StatusOperacaoViagem.REALIZADA);
		request.agruparEstabelecimento();
		request.setAgrupar(true);
		//request.cargasPendentes();
		
		try {
			//operacaoViagemLista.testar();
			return Response.ok()
					//.entity(operacaoViagemLista.listar(request).size() + " - " + operacaoViagemLista.contar(request))
					//.entity("ok")
					.entity(operacaoViagemLista.agrupado(request))
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}

	
	@GET
	@Path("/demandaTomador")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarDemandasDoTomador()  throws Exception {
		Filtro filtro = new Filtro();
		filtro.igual(DemandaTomadorViewCatalogo.CAMPO_TOMADOR_ID, 103l);
		
		try {
			Listagem<DemandaTomadorView> result = demandaTomadorViewCatalogo.listar(filtro);
			return Response.ok()
					.entity(result)
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}

	@GET
	@Path("/demandaTransportador")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarDemandasDoTransportador()  throws Exception {
		Filtro filtro = new Filtro();
		filtro.igual(DemandaTransportadorViewCatalogo.CAMPO_TRANSPORTADOR_ID, 71l);
		
		try {
			Listagem<DemandaTransportadorView> result = demandaTransportadorViewCatalogo.listar(filtro);
			return Response.ok()
					.entity(result)
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}

}
