package rest;

import java.util.Date;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dto.Listagem;
import modelo.Endereco;
import modelo.Estabelecimento;
import modelo.Municipio;
import modelo.OperacaoViagem;
import modelo.PontoViagem;
import modelo.UF;
import servico.OperacaoViagemService;
import util.DataUtil;
import util.Ejb;

@Path("/operacaoViagem")
public class OperacaoViagemRest {

	private OperacaoViagemService operacaoViagemService;

	public OperacaoViagemRest() {
		operacaoViagemService = Ejb.lookup(OperacaoViagemService.class);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(
			@QueryParam("p") @DefaultValue("1") int pagina, 
			@QueryParam("t") @DefaultValue("10") int tamanhoPagina, 
			@QueryParam("e") String estabelecimentoId)  throws Exception {

		List<OperacaoViagem> listagem = null;
		Estabelecimento estabelecimento = new Estabelecimento();
		estabelecimento.setId(Long.parseLong(estabelecimentoId));

		try {
			Date d = DataUtil.extrairDataSemHora(new Date());
			listagem  = operacaoViagemService.listarPorDataEEstabelecimento(d, estabelecimento);
			return Response.ok()
					.entity(
							toJson(listagem))
					//new Gson().toJson(toJsonObject(listagem)))
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
								field.getDeclaringClass().equals(OperacaoViagem.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("tipo")
										|| field.getName().equals("status")
										|| field.getName().equals("dataHoraStatus")
										|| field.getName().equals("pontoViagem")
										)
								||
								field.getDeclaringClass().equals(PontoViagem.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("estabelecimento")
										|| field.getName().equals("dataChegadaAcordada")
										|| field.getName().equals("dataHoraChegadaPrevista")
										|| field.getName().equals("dataHoraChegada")
										|| field.getName().equals("dataHoraSaida")
										)
								||
								field.getDeclaringClass().equals(Estabelecimento.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("nome")
										|| field.getName().equals("endereco")
										)
								|| field.getDeclaringClass().equals(Endereco.class)
								&& (
										field.getName().equals("logradouro")
										|| field.getName().equals("bairro")
										|| field.getName().equals("municipio")
										|| field.getName().equals("complemento")
										|| field.getName().equals("latitude")
										|| field.getName().equals("longitude")
										)
								|| field.getDeclaringClass().equals(Municipio.class)
								&& (
										field.getName().equals("id")
										|| field.getName().equals("nome")
										|| field.getName().equals("uf")
										)
								|| field.getDeclaringClass().equals(UF.class)
								&& (
										field.getName().equals("nome")
										|| field.getName().equals("abreviatura")
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