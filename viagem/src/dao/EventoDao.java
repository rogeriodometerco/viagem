package dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import modelo.Evento;
import modelo.OperacaoViagem;
import util.DataUtil;

@Stateless
public class EventoDao extends GenericDao<Evento> {

	public List<OperacaoViagem> listarTerminoOperacoes(Date data) throws Exception {
		Date dataInicial = DataUtil.extrairDataSemHora(new Date());
		Date dataFinal = DataUtil.somarDias(dataInicial, 1);
		
		String sql = "SELECT DISTINCT x.operacao FROM EventoTerminoOperacao x WHERE" +
				" x.dataHoraTermino >= :dataInicial"
				+ " and x.dataHoraTermino < :dataFinal";
		List<OperacaoViagem> operacoes = getEntityManager()
				.createQuery(sql, OperacaoViagem.class)
				.setParameter("dataInicial", dataInicial)
				.setParameter("dataFinal", dataFinal)
				.getResultList();

		return operacoes;
	}

	public List<OperacaoViagem> listarOperacoesProgramadas(Date data) throws Exception {
		Date dataInicial = DataUtil.extrairDataSemHora(data);
		Date dataFinal = DataUtil.somarDias(dataInicial, 1);
		
		String sql = "SELECT DISTINCT o "
				+ "FROM EventoCriacaoViagem e JOIN e.viagem AS v JOIN v.pontos p JOIN p.operacoes o"
				+ " WHERE" +
				" e.dataHoraCriacao >= :dataInicial"
				+ " and e.dataHoraCriacao < :dataFinal";
		List<OperacaoViagem> operacoes = getEntityManager()
				.createQuery(sql, OperacaoViagem.class)
				.setParameter("dataInicial", dataInicial)
				.setParameter("dataFinal", dataFinal)
				.getResultList();

		return operacoes;
	}

}
