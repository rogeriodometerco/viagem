package dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import enums.StatusViagem;
import modelo.Conta;
import modelo.Motorista;
import modelo.Viagem;

@Stateless
public class ViagemDao extends GenericDao<Viagem> {

	public List<Viagem> recuperarViagensNaoEncerradas() throws Exception {
		// TODO Acrescentar motorista na cláusula abaixo.
		String sql = "SELECT x FROM Viagem x WHERE" +
				" x.status not in :status";// + StatusViagem.CONCLUIDA;
		List<StatusViagem> status = new ArrayList<StatusViagem>();
		status.add(StatusViagem.CONCLUIDA);
		status.add(StatusViagem.ABORTADA);
		return getEntityManager()
					.createQuery(sql, Viagem.class)
					.setParameter("status", status)
					.getResultList();
	}
	
	public List<Viagem> recuperarViagensNaoEncerradas(Conta motorista) throws Exception {
		// TODO Acrescentar motorista na cláusula abaixo.
		String sql = "SELECT x FROM Viagem x WHERE" +
				" x.status not in :status" +
				" and x.motorista = :motorista";
		List<StatusViagem> status = new ArrayList<StatusViagem>();
		status.add(StatusViagem.CONCLUIDA);
		status.add(StatusViagem.ABORTADA);
		return getEntityManager()
					.createQuery(sql, Viagem.class)
					.setParameter("status", status)
					.setParameter("motorista", motorista)
					.getResultList();
	}

}
