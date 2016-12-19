package dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import enums.StatusOperacaoViagem;
import modelo.OperacaoViagem;
import util.DataUtil;

@Stateless
public class OperacaoViagemDao extends GenericDao<OperacaoViagem> {

	public List<OperacaoViagem> listarTodasDoDia(Date data, StatusOperacaoViagem status) throws Exception {
		Date dataInicial = DataUtil.extrairDataSemHora(new Date());
		Date dataFinal = DataUtil.somarDias(dataInicial, 1);
		
		String sql = "SELECT x FROM OperacaoViagem x WHERE" +
				" x.dataHoraStatus >= :dataInicial"
				+ " and x.dataHoraStatus < :dataFinal";
		List<OperacaoViagem> operacoes = getEntityManager()
				.createQuery(sql, OperacaoViagem.class)
				.setParameter("dataInicial", dataInicial)
				.setParameter("dataFinal", dataFinal)
				.getResultList();

		return operacoes;
	}
}
