package dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import modelo.Evento;
import modelo.OperacaoViagem;
import modelo.PontoViagem;
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
				+ " AND e.dataHoraCriacao < :dataFinal";
		List<OperacaoViagem> operacoes = getEntityManager()
				.createQuery(sql, OperacaoViagem.class)
				.setParameter("dataInicial", dataInicial)
				.setParameter("dataFinal", dataFinal)
				.getResultList();

		return operacoes;
	}


	public List<PontoViagem> listarChegadas(Date data) throws Exception {
		Date dataInicial = DataUtil.extrairDataSemHora(data);
		Date dataFinal = DataUtil.somarDias(dataInicial, 1);

		String sql = "SELECT DISTINCT p "
				+ "FROM EventoChegada e JOIN FETCH e.pontoViagem AS p FETCH JOIN p.operacoes o"
				+ " WHERE" +
				" e.dataHoraChegada >= :dataInicial"
				+ " and e.dataHoraChegada < :dataFinal";
		List<PontoViagem> pontos = getEntityManager()
				.createQuery(sql, PontoViagem.class)
				.setParameter("dataInicial", dataInicial)
				.setParameter("dataFinal", dataFinal)
				.getResultList();

		return pontos;
	}

	public List<PontoViagem> listarPontosComVeiculoNoLocal(Date dataHora) throws Exception {

		String sql = "SELECT DISTINCT p "
				+ "FROM PontoViagem AS p FETCH JOIN p.operacoes o"
				+ " WHERE" +
				" p.dataHoraChegada <= :dataHora"
				+ " AND (p.dataHoraSaida > :dataHora OR p.dataHoraSaida IS NULL)";
		List<PontoViagem> pontos = getEntityManager()
				.createQuery(sql, PontoViagem.class)
				.setParameter("dataHora", dataHora)
				.getResultList();

		return pontos;
	}

	public List<PontoViagem> listarPontosPrevistosPendentes(Date dataHoraInicial, Date dataHoraFinal) throws Exception {

		String sql = "SELECT DISTINCT p "
				+ "FROM PontoViagem AS p FETCH JOIN p.operacoes o"
				+ " WHERE" +
				" p.dataHoraPrevistaChegada >= :dataHoraInicial"
				+ " AND p.dataHoraPrevistaChegada <= :dataHoraFinal"
				+ " AND p.dataHoraChegada IS NULL";
		List<PontoViagem> pontos = getEntityManager()
				.createQuery(sql, PontoViagem.class)
				.setParameter("dataHoraInicial", dataHoraInicial)
				.setParameter("dataHoraFinal", dataHoraFinal)
				.getResultList();

		return pontos;
	}

	
}
