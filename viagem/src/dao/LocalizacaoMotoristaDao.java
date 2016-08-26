package dao;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import modelo.LocalizacaoMotorista;
import modelo.Motorista;

@Stateless
public class LocalizacaoMotoristaDao extends GenericDao<LocalizacaoMotorista> {
	
	public LocalizacaoMotorista recuperar(Motorista motorista) throws Exception {
		LocalizacaoMotorista result = null;
		String sql = "select distinct x from " + getClasseEntidade().getSimpleName() + " x"
				+ " where x.motorista = :motorista";
		try {
			result = getEntityManager()
				.createQuery(sql, LocalizacaoMotorista.class)
				.setParameter("motorista", motorista)
				.getSingleResult();
		} catch (NoResultException e) {
			// Nada a fazer, retornar null.
		}
		return result;
	}
}
