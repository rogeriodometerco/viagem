package dao;

import java.util.List;

import javax.ejb.Stateless;

import modelo.TipoCarroceria;

@Stateless
public class TipoCarroceriaDao extends GenericDao<TipoCarroceria> {

	public List<TipoCarroceria> listarOrdenadoPorNome(int pagina, int tamanhoPagina) throws Exception {
		List<TipoCarroceria> result = null;
		String sql = "SELECT x FROM TipoCarroceria x " +
				" order by x.nome";
		result = getEntityManager()
				.createQuery(sql, TipoCarroceria.class)
				.setFirstResult(pagina * tamanhoPagina - tamanhoPagina)
				.setMaxResults(tamanhoPagina)
				.getResultList();
		return result;
	}

	public Long contar() throws Exception {
		String sql = "SELECT COUNT(x) FROM TipoCarroceria x";
		return getEntityManager()
				.createQuery(sql, Long.class)
				.getSingleResult();
	}


	public List<TipoCarroceria> listarPorNomeOrdenadoPorNome(
			int pagina, int tamanhoPagina, String iniciandoPor) throws Exception {

		List<TipoCarroceria> result = null;
		String sql = "SELECT x FROM TipoCarroceria x WHERE" +
				" upper(x.nome) like :iniciandoPor" +
				" order by x.nome";
		result = getEntityManager()
				.createQuery(sql, TipoCarroceria.class)
				.setParameter("iniciandoPor", iniciandoPor.toUpperCase().concat("%"))
				.setFirstResult(pagina * tamanhoPagina - tamanhoPagina)
				.setMaxResults(tamanhoPagina)
				.getResultList();
		return result;
	}

	public Long contarPorNome(String iniciandoPor) throws Exception {
		String sql = "SELECT COUNT(x) FROM TipoCarroceria x WHERE" +
				" upper(x.nome) like :iniciandoPor";
		return getEntityManager()
				.createQuery(sql, Long.class)
				.setParameter("iniciandoPor", iniciandoPor.toUpperCase().concat("%"))
				.getSingleResult();
	}

}
