package dao;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import modelo.LocalizacaoMotorista;
import modelo.Motorista;

@Stateless
public class LocalizacaoMotoristaDao extends GenericDao<LocalizacaoMotorista> {
	
	public LocalizacaoMotorista getLocalizacaoAtual(Motorista motorista) throws Exception {
		LocalizacaoMotorista result = null;
		String sql = "select distinct x from LocalizacaoAtual x"
				+ " where x.motorista = :motorista";
		try {
			result = getEntityManager()
				.createQuery(sql, LocalizacaoMotorista.class)
				.setParameter("motorista", motorista)
				.getSingleResult();
		} catch (NonUniqueResultException e) {
			// TODO
		} catch (NoResultException e) {
			
		}
		return result;
	}
}
