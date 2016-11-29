package dao;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import modelo.Veiculo;

@Stateless
public class VeiculoDao extends GenericDao<Veiculo> {

	public Veiculo recuperarPelaIdentificacao(String identificacao) throws Exception {
		String sql = "SELECT x FROM Veiculo x WHERE" +
				" x.identificacao = :identificacao";
		Veiculo result = null;
		try {
			result = getEntityManager()
					.createQuery(sql, Veiculo.class)
					.setParameter("identificacao", identificacao)
					.getSingleResult();
		} catch (NoResultException e) {
			throw new Exception("Veículo " + identificacao + " não encontrado");
		}
		return result;
	}

}
