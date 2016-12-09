package dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import enums.StatusViagem;
import modelo.Motorista;
import modelo.Viagem;

@Stateless
public class ViagemDao extends GenericDao<Viagem> {

	public List<Viagem> recuperarViagensNaoEncerradas(Motorista motorista) throws Exception {
		// TODO Acrescentar motorista na cláusula abaixo.
		String sql = "SELECT x FROM Viagem x WHERE" +
				//" x.status <> 1";// + StatusViagem.CONCLUIDA;
				" x.status not in :status";// + StatusViagem.CONCLUIDA;
		List<StatusViagem> status = new ArrayList<StatusViagem>();
		status.add(StatusViagem.CONCLUIDA);
		status.add(StatusViagem.ABORTADA);
		return getEntityManager()
					.createQuery(sql, Viagem.class)
					.setParameter("status", status)
					.getResultList();
	}
}
