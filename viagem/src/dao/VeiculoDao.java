package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import modelo.Veiculo;

@Stateless
public class VeiculoDao extends GenericDao<Veiculo> {

	public Veiculo recuperarPelaPlaca(String placa) throws Exception {
		String sql = "SELECT DISTINCT x FROM Veiculo x JOIN x.componentes c WHERE" +
				" UPPER(c.placa)= :placa"
				+ " AND c.ordemNoVeiculo = 1";
		Veiculo result = null;
		try {
			result = getEntityManager()
					.createQuery(sql, Veiculo.class)
					.setParameter("placa", placa)
					.getSingleResult();
		} catch (NoResultException e) {
			throw new Exception("Veículo " + placa + " não encontrado");
		}
		return result;
	}

	public Veiculo recuperarPelaPlacaSeExistir(String placa) throws Exception {
		String sql = "SELECT DISTINCT x FROM Veiculo x JOIN x.componentes c WHERE" +
				" UPPER(c.placa)= :placa"
				+ " AND c.ordemNoVeiculo = 1";
		Veiculo result = null;
		try {
			result = getEntityManager()
					.createQuery(sql, Veiculo.class)
					.setParameter("placa", placa)
					.getSingleResult();
		} catch (NoResultException e) {
			// Não tratar como exceção.
		}
		return result;
	}

	public List<Veiculo> listarPelaPlaca(String placaIniciandoPor, int rows) throws Exception {
		List<Veiculo> result = null;
		String sql = "SELECT x FROM Veiculo x JOIN x.componentes c WHERE" +
				" UPPER(c.placa) LIKE :iniciandoPor"
				+ " AND c.ordemNoVeiculo = 1"
				+ " ORDER BY c.placa";
		result = getEntityManager()
				.createQuery(sql, Veiculo.class)
				.setParameter("iniciandoPor", placaIniciandoPor.toUpperCase().concat("%"))
				.setMaxResults(rows)
				.getResultList();
		return result;
	}
	
}
