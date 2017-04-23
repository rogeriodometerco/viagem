package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import enums.TipoOperacaoViagem;
import modelo.Conta;
import modelo.Estabelecimento;
import modelo.OperacaoViagem;
import util.DataUtil;

@Stateless
public class OperacaoViagemDao extends GenericDao<OperacaoViagem> {

	private TipoOperacaoViagem tipoOperacao;
	private Estabelecimento estabelecimento;
	private Conta transportador;

	public List<OperacaoViagem> listar() {
		tipoOperacao = TipoOperacaoViagem.COLETA;
		//estabelecimento = new Estabelecimento();
		//estabelecimento.setId(94l);
		//transportador = new Conta();
		//transportador.setId(71l);
		Date dataInicial = new Date();
		Date dataFinal = new Date();
		boolean acordadoAtraso = true;
		
		
		Map<String, Object> filtros = new HashMap<String, Object>();
		
		
		List<String> condicoes = new ArrayList<String>();
		List<OperacaoViagem> result = null;
		String sql = "SELECT x FROM OperacaoViagem x JOIN x.pontoViagem p JOIN p.viagem v";

		if (tipoOperacao != null) {
			condicoes.add("x.tipo = :tipo");
			filtros.put("tipo", tipoOperacao );
		}
		
		if (estabelecimento != null) {
			sql += " JOIN x.pontoViagem p";
			condicoes.add("p.estabelecimento = :estabelecimento");
			filtros.put("estabelecimento", estabelecimento);
		}
		
		if (transportador != null) {
			sql += " JOIN p.viagem v";
			condicoes.add("v.transportador = :transportador");
			filtros.put("transportador", transportador);
		}
		
		if (dataFinal != null) {
			condicoes.add("x.dataHoraStatus <= :dataFinal");
			filtros.put("dataFinal", dataFinal);
		}

		if (acordadoAtraso) {
			condicoes.add("p.dataChegadaAcordada < :diaAtual AND p.dataHoraChegada IS NULL");
			filtros.put("diaAtual", DataUtil.extrairDataSemHora(new Date()));
		}
		
		
		int i = 0;
		for (String condicao: condicoes) {
			if (i == 0) {
				sql += " WHERE";
			} else {
				sql += " AND";
			}
			sql = sql + " " + condicao;
			i++;
		}
				
		TypedQuery<OperacaoViagem> query = getEntityManager()
				.createQuery(sql, OperacaoViagem.class);

		i = 1;
		for (String filtro: filtros.keySet()) {
			query.setParameter(filtro, filtros.get(filtro));
			i++;
		}
		
		result = query.getResultList();
		return result;
	}


	public List<OperacaoViagem> listarPorDataEEstabelecimento(
			Date dataReferencia, Estabelecimento estabelecimento) throws Exception {

		Date diaSeguinte = DataUtil.somarDias(dataReferencia, 1);

		List<OperacaoViagem> result = null;

		String sql = "SELECT DISTINCT o FROM PontoViagem p JOIN p.operacoes o "
				+ " WHERE "
				+ "	p.estabelecimento = :estabelecimento"
				+ " AND ("
				+ "		("
				+ "			p.dataHoraChegada IS NOT NULL "
				+ "			AND p.dataHoraChegada < :diaSeguinte "
				+ "			AND (p.dataHoraSaida >= :dataReferencia OR p.dataHoraSaida IS NULL))"
				+ " 	OR ("
				+ "			p.dataHoraPrevistaChegada >= :dataReferencia AND p.dataHoraPrevistaChegada < :diaSeguinte AND p.dataHoraChegada IS NULL)"
				+ " 	OR ("
				+ "			p.dataChegadaAcordada = :dataReferencia AND p.dataHoraPrevistaChegada IS NULL AND p.dataHoraChegada IS NULL)"
				+ "	)";

		result = getEntityManager()
				.createQuery(sql, OperacaoViagem.class)
				.setParameter("estabelecimento", estabelecimento)
				.setParameter("dataReferencia", dataReferencia)
				.setParameter("diaSeguinte", diaSeguinte)
				.getResultList();

		return result;
	}
}
