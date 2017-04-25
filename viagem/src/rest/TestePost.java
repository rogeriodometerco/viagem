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

import dao.OperacaoViagemLista;
import dto.OperacaoViagemRequest;
import enums.StatusOperacaoViagem;
import enums.TipoOperacaoViagem;
import modelo.Conta;
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

	public TestePost() {
		operacaoViagemLista= Ejb.lookup(OperacaoViagemLista.class);

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
			
			return Response.ok()
					//.entity(operacaoViagemLista.listar(request).size() + " - " + operacaoViagemLista.contar(request))
					.entity(operacaoViagemLista.agrupado(request).size())
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}


	

}
