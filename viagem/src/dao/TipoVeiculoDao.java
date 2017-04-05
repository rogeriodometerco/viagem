package dao;

import java.util.List;

import javax.ejb.Stateless;

import modelo.TipoVeiculo;

@Stateless
public class TipoVeiculoDao extends GenericDao<TipoVeiculo> {

	public List<TipoVeiculo> listarOrdenadoPorNome(int pagina, int tamanhoPagina) throws Exception {
		List<TipoVeiculo> result = null;
		String sql = "SELECT x FROM TipoVeiculo x " +
				" order by x.nome";
		result = getEntityManager()
				.createQuery(sql, TipoVeiculo.class)
				.setFirstResult(pagina * tamanhoPagina - tamanhoPagina)
				.setMaxResults(tamanhoPagina)
				.getResultList();
		return result;
	}

	public Long contar() throws Exception {
		String sql = "SELECT COUNT(x) FROM TipoVeiculo x";
		return getEntityManager()
				.createQuery(sql, Long.class)
				.getSingleResult();
	}


	public List<TipoVeiculo> listarPorNomeOrdenadoPorNome(
			int pagina, int tamanhoPagina, String iniciandoPor) throws Exception {

		List<TipoVeiculo> result = null;
		String sql = "SELECT x FROM TipoVeiculo x WHERE" +
				" upper(x.nome) like :iniciandoPor" +
				" order by x.nome";
		result = getEntityManager()
				.createQuery(sql, TipoVeiculo.class)
				.setParameter("iniciandoPor", iniciandoPor.toUpperCase().concat("%"))
				.setFirstResult(pagina * tamanhoPagina - tamanhoPagina)
				.setMaxResults(tamanhoPagina)
				.getResultList();
		return result;
	}

	public Long contarPorNome(String iniciandoPor) throws Exception {
		String sql = "SELECT COUNT(x) FROM TipoVeiculo x WHERE" +
				" upper(x.nome) like :iniciandoPor";
		return getEntityManager()
				.createQuery(sql, Long.class)
				.setParameter("iniciandoPor", iniciandoPor.toUpperCase().concat("%"))
				.getSingleResult();
	}

}
