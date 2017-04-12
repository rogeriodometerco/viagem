package rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dto.Listagem;
import modelo.ComponenteVeiculo;
import modelo.Conta;
import modelo.DemandaTransporte;
import modelo.Endereco;
import modelo.Entrega;
import modelo.Estabelecimento;
import modelo.EtapaEntrega;
import modelo.Municipio;
import modelo.OperacaoViagem;
import modelo.PontoViagem;
import modelo.Produto;
import modelo.TipoCarroceria;
import modelo.TipoVeiculo;
import modelo.UF;
import modelo.UnidadeQuantidade;
import modelo.Veiculo;
import modelo.Viagem;
import servico.DemandaTransporteService;
import servico.MontadorViagem;
import servico.SessaoService;
import servico.ViagemService;
import util.Ejb;

@Path("/viagem")
public class ViagemRest {

	private ViagemService viagemService;
	private SessaoService sessaoService;
	private DemandaTransporteService demandaService;

	public ViagemRest() {
		viagemService = Ejb.lookup(ViagemService.class);
		sessaoService = Ejb.lookup(SessaoService.class);
		demandaService = Ejb.lookup(DemandaTransporteService.class);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response criarViagem(@Context HttpServletRequest httpServletRequest, 
			String json) throws Exception {

		try {
			JsonObject request = new Gson().fromJson(json, JsonObject.class);
			JsonObject motoristaRequest = request.get("motorista").getAsJsonObject();
			JsonObject veiculoRequest = request.get("veiculo").getAsJsonObject();
			JsonArray demandasRequest = request.get("demandas").getAsJsonArray();
			JsonArray pontosRequest = request.get("pontos").getAsJsonArray();

			Conta motorista = new Conta();
			motorista.setId(motoristaRequest.get("id").getAsLong());

			Veiculo veiculo = new Veiculo();
			veiculo.setId(veiculoRequest.get("id").getAsLong());

			MontadorViagem montadorViagem = new MontadorViagem();
			montadorViagem.setMotorista(motorista);
			montadorViagem.setVeiculo(veiculo);
			montadorViagem.setTransportador(sessaoService.getConta());

			for (JsonElement e: demandasRequest) {
				JsonObject object = e.getAsJsonObject();
				JsonObject demandaRequest = object.get("demanda").getAsJsonObject();

				Long demandaId = demandaRequest.get("id").getAsLong();
				DemandaTransporte demanda = demandaService.recuperar(demandaId);

				Integer quantidadeProgramada = object.get("quantidadeProgramada").getAsInt();

				montadorViagem.adicionarDemanda(demanda, quantidadeProgramada);
			}

			DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
			for (JsonElement e: pontosRequest) {
				JsonObject object = e.getAsJsonObject();
				JsonObject estabelecimentoRequest = object.get("estabelecimento").getAsJsonObject();

				Estabelecimento estabelecimento = new Estabelecimento();
				estabelecimento.setId(estabelecimentoRequest.get("id").getAsLong());

				Date dataChegadaAcordada = formatador.parse(object.get("dataChegadaAcordada").getAsString());

				Integer ordem = object.get("ordem").getAsInt();

				montadorViagem.adicionarPontoECriarOperacoes(estabelecimento, dataChegadaAcordada, ordem);
			}

			return Response.ok()
					.entity(
							toJson(
									viagemService.criar(
											montadorViagem.getViagem())))
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

		List<Viagem> listagem = null;
		try {
			// Sem critério de pesquisa.
			if (placaContendo == null || placaContendo.trim().equals("")) {
				listagem  = viagemService.listar();

				// Com critério de pesquisa.
			} else {
				listagem  = viagemService.listar();
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
									viagemService.recuperar(id)))
					.build();
		} catch (Exception e) {
			return Response.serverError()
					.entity(new RespostaErro(e.getMessage()))
					.build();
		}
	}

	private String toJson(Object source) {
		Gson g = new GsonBuilder()
				.setDateFormat("dd/MM/yyyy HH:mm:ss")
				.setExclusionStrategies(new ExclusionStrategy() {

					@Override
					public boolean shouldSkipField(FieldAttributes field) {
						boolean serializar =
								field.getDeclaringClass().equals(Listagem.class)
								||
								field.getDeclaringClass().equals(Viagem.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("transportador")
										|| field.getName().equals("motorista")
										|| field.getName().equals("veiculo")
										//|| field.getName().equals("etapas")
										|| field.getName().equals("pontos")
										|| field.getName().equals("status")
										|| field.getName().equals("dataHoraStatus")
										)
								||
								field.getDeclaringClass().equals(Conta.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("nome")
										)
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
										field.getName().equals("nome")
										)
								||
								field.getDeclaringClass().equals(ComponenteVeiculo.class)
								&& (
										field.getName().equals("placa")
										|| field.getName().equals("tipoCarroceria")
										|| field.getName().equals("posicaoNoVeiculo")
										)
								||
								field.getDeclaringClass().equals(TipoCarroceria.class)
								&& (
										field.getName().equals("nome")
										)
								||
								 field.getDeclaringClass().equals(EtapaEntrega.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("entrega")
										//|| field.getName().equals("origem")
										//|| field.getName().equals("destino")
										|| field.getName().equals("status")
										|| field.getName().equals("dataHoraStatus")
										)
								||
								field.getDeclaringClass().equals(Entrega.class)
								&& (
										field.getName().equals("demanda")
										|| field.getName().equals("produto")
										|| field.getName().equals("quantidade")
										|| field.getName().equals("unidadeQuantidade")
										|| field.getName().equals("status")
										|| field.getName().equals("dataHoraStatus")
										)
								||
								field.getDeclaringClass().equals(Estabelecimento.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("nome")
										|| field.getName().equals("endereco")
										)
								||
								field.getDeclaringClass().equals(Endereco.class)
								&& (
										field.getName().equals("municipio")
										)
								||
								field.getDeclaringClass().equals(Municipio.class)
								&& (
										field.getName().equals("nome")
										|| field.getName().equals("uf")
										)
								||
								field.getDeclaringClass().equals(UF.class)
								&& (
										field.getName().equals("abreviatura")
										)
								||
								field.getDeclaringClass().equals(DemandaTransporte.class)
								&& (
										field.getName().equals("id")
										)
								||
								field.getDeclaringClass().equals(Produto.class)
								&& (
										field.getName().equals("nome")
										)
								||
								field.getDeclaringClass().equals(UnidadeQuantidade.class)
								&& (
										field.getName().equals("abreviacao")
										)
								||
								field.getDeclaringClass().equals(PontoViagem.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("estabelecimento")
										|| field.getName().equals("dataChegadaAcordada")
										|| field.getName().equals("ordem")
										|| field.getName().equals("status")
										|| field.getName().equals("dataHoraStatus")
										|| field.getName().equals("dataHoraPrevistaChegada")
										|| field.getName().equals("dataHoraChegada")
										|| field.getName().equals("dataHoraSaida")
										|| field.getName().equals("operacoes")
										)
								||
								field.getDeclaringClass().equals(OperacaoViagem.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("etapaEntrega")
										|| field.getName().equals("tipo")
										|| field.getName().equals("status")
										|| field.getName().equals("dataHoraStatus")
										)
								||
								field.getDeclaringClass().equals(UnidadeQuantidade.class)
								&& (
										field.getName().equals("abreviacao")
										)
								||
								field.getDeclaringClass().equals(UnidadeQuantidade.class)
								&& (
										field.getName().equals("abreviacao")
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