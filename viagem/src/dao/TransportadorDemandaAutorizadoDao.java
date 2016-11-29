package dao;

import java.util.List;

import javax.ejb.Stateless;

import modelo.DemandaTransporte;
import modelo.TransportadorDemandaAutorizado;

@Stateless
public class TransportadorDemandaAutorizadoDao extends GenericDao<TransportadorDemandaAutorizado> {

	public List<TransportadorDemandaAutorizado> recuperarPelaDemanda(DemandaTransporte demanda) throws Exception {
		String sql = "SELECT x FROM TransportadorDemandaAutorizado x WHERE" +
				" x.demanda = :demanda";
		return getEntityManager()
					.createQuery(sql, TransportadorDemandaAutorizado.class)
					.setParameter("demanda", demanda)
					.getResultList();
	}
}
