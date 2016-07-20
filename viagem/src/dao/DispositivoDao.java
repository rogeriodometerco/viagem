package dao;

import javax.ejb.Stateless;

import modelo.Dispositivo;

@Stateless
public class DispositivoDao extends GenericDao<Dispositivo> {

	public Dispositivo recuperarPeloLogin(String identificador) throws Exception {
		String sql = "SELECT x FROM Dispositivo x WHERE" +
				" x.identificador = :identificador";
		return getEntityManager()
				.createQuery(sql, Dispositivo.class)
				.setParameter("identificador", identificador)
				.getSingleResult();
	}
}
