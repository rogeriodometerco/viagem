package dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import enums.StatusViagem;
import modelo.Conta;
import modelo.Viagem;

@Stateless
public class ViagemDao extends GenericDao<Viagem> {

	public List<Viagem> recuperarViagensDoMotorista(Conta motorista) throws Exception {
		String sql = "SELECT x FROM Viagem x WHERE "
				+ " x.motorista = :motorista"
				+ " ORDER BY x.id DESC";
		return getEntityManager()
					.createQuery(sql, Viagem.class)
					.setParameter("motorista", motorista)
					.getResultList();
	}

	public List<Viagem> recuperarViagensNaoFinalizadas(Conta motorista) throws Exception {
		String sql = "SELECT x FROM Viagem x WHERE" +
				" x.status not in :status" +
				" AND x.motorista = :motorista"
				+ " ORDER BY x.id DESC";
		List<StatusViagem> status = new ArrayList<StatusViagem>();
		status.add(StatusViagem.CONCLUIDA);
		status.add(StatusViagem.ABORTADA);
		return getEntityManager()
					.createQuery(sql, Viagem.class)
					.setParameter("status", status)
					.setParameter("motorista", motorista)
					.getResultList();
	}

	public List<Viagem> recuperarViagensAguardandoAceite(Conta motorista) throws Exception {
		String sql = "SELECT x FROM Viagem x WHERE" +
				" x.status = :status" +
				" AND x.motorista = :motorista"
				+ " ORDER BY x.id DESC";
		return getEntityManager()
					.createQuery(sql, Viagem.class)
					.setParameter("status", StatusViagem.PENDENTE)
					.setParameter("motorista", motorista)
					.getResultList();
	}

	public List<Viagem> recuperarViagensAguardandoInicio(Conta motorista) throws Exception {
		String sql = "SELECT x FROM Viagem x WHERE" +
				" x.status = :status" +
				" AND x.motorista = :motorista"
				+ " ORDER BY x.id DESC";
		return getEntityManager()
					.createQuery(sql, Viagem.class)
					.setParameter("status", StatusViagem.ACEITA)
					.setParameter("motorista", motorista)
					.getResultList();
	}

	public List<Viagem> recuperarViagensIniciadas(Conta motorista) throws Exception {
		String sql = "SELECT x FROM Viagem x WHERE" +
				" x.status = :status" +
				" AND x.motorista = :motorista"
				+ " ORDER BY x.id DESC";
		return getEntityManager()
					.createQuery(sql, Viagem.class)
					.setParameter("status", StatusViagem.INICIADA)
					.setParameter("motorista", motorista)
					.getResultList();
	}

	public List<Viagem> recuperarViagensFinalizadas(Conta motorista) throws Exception {
		String sql = "SELECT x FROM Viagem x WHERE" +
				" x.status in :status" +
				" AND x.motorista = :motorista"
				+ " ORDER BY x.id DESC";
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
