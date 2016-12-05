package dao;

import java.util.List;

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

	public List<Veiculo> listarPelaIdentificacao(String chave, int rows) throws Exception {
		
		List<Veiculo> result = null;
		String sql = "SELECT x FROM Veiculo x WHERE" +
				" upper(x.identificacao) like :iniciandoPor" +
				" order by x.identificacao";
		result = getEntityManager()
				.createQuery(sql, Veiculo.class)
				.setParameter("iniciandoPor", chave.toUpperCase().concat("%"))
				.setMaxResults(rows)
				.getResultList();
		return result;
	}
	
}
