package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import modelo.Veiculo;

@Stateless
public class VeiculoDao extends GenericDao<Veiculo> {

	public Veiculo recuperarPelaPlaca(String placa) throws Exception {
		String sql = "SELECT x FROM Veiculo x JOIN x.componentes c WHERE" +
				" UPPER(c.placa)= :placa"
				+ " AND c.posicaoNoVeiculo = 1";
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
		String sql = "SELECT x FROM Veiculo x JOIN x.componentes c WHERE" +
				" UPPER(c.placa)= :placa"
				+ " AND c.posicaoNoVeiculo = 1";
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

	public List<Veiculo> listarOrdenadoPorPlaca(int pagina, int tamanhoPagina) throws Exception {
		List<Veiculo> result = null;
		String sql = "SELECT x.veiculo FROM ComponenteVeiculo x "
				+ " WHERE x.posicaoNoVeiculo = 1 ORDER BY x.placa";
		result = getEntityManager()
				.createQuery(sql, Veiculo.class)
				.setFirstResult(pagina * tamanhoPagina - tamanhoPagina)
				.setMaxResults(tamanhoPagina)
				.getResultList();
		return result;
	}

	public Long contar() throws Exception {
		String sql = "SELECT COUNT(x) FROM Veiculo x";
		return getEntityManager()
				.createQuery(sql, Long.class)
				.getSingleResult();
	}

	public List<Veiculo> listarPorPlacaOrdenadoPorPlaca(
			int pagina, int tamanhoPagina, String placaContendo) throws Exception {

		List<Veiculo> result = null;
		/*		String sql = "SELECT DISTINCT x FROM Veiculo x JOIN x.componentes c WHERE" +
				" upper(c.placa) like :contendo" 
				+ " AND c.posicaoNoVeiculo = 1";
		 */		String sql = "SELECT x.veiculo FROM ComponenteVeiculo x WHERE" +
				 " upper(x.placa) like :contendo" 
				 + " AND x.posicaoNoVeiculo = 1";
		 result = getEntityManager()
				 .createQuery(sql, Veiculo.class)
				 .setParameter("contendo", "%".concat(placaContendo.toUpperCase()).concat("%"))
				 .setFirstResult(pagina * tamanhoPagina - tamanhoPagina)
				 .setMaxResults(tamanhoPagina)
				 .getResultList();
		 return result;
	}

	public Long contarPorPlaca(String placaContendo) throws Exception {
		String sql = "SELECT COUNT(x) FROM ComponenteVeiculo x WHERE" +
				" upper(x.placa) like :contendo" 
				+ " AND x.posicaoNoVeiculo = 1";
		return getEntityManager()
				.createQuery(sql, Long.class)
				.setParameter("contendo", "%".concat(placaContendo.toUpperCase()).concat("%"))
				.getSingleResult();
	}

}