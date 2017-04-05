package dao;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import modelo.Conta;
import modelo.UltimaLocalizacaoMotorista;

@Stateless
public class UltimaLocalizacaoMotoristaDao extends GenericDao<UltimaLocalizacaoMotorista> {
	
	public UltimaLocalizacaoMotorista recuperar(Conta motorista) throws Exception {
		UltimaLocalizacaoMotorista result = null;
		String sql = "select distinct x from UltimaLocalizacaoMotorista x"
				+ " where x.motorista = :motorista";
		try {
			result = getEntityManager()
				.createQuery(sql, UltimaLocalizacaoMotorista.class)
				.setParameter("motorista", motorista)
				.getSingleResult();
		} catch (NoResultException e) {
			// Nada a fazer, retornar null.
		}
		return result;
	}
}
