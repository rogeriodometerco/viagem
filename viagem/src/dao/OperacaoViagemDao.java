package dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import modelo.Estabelecimento;
import modelo.OperacaoViagem;
import util.DataUtil;

@Stateless
public class OperacaoViagemDao extends GenericDao<OperacaoViagem> {

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
