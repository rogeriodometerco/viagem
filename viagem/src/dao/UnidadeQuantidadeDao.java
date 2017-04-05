package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import modelo.UnidadeQuantidade;

@Stateless
public class UnidadeQuantidadeDao extends GenericDao<UnidadeQuantidade> {

	public UnidadeQuantidade recuperarPelaAbreviacaoSeExistir(String abreviacao) throws Exception {
		String sql = "SELECT x FROM UnidadeQuantidade x WHERE" +
				" x.abreviacao = :abreviacao";
		UnidadeQuantidade result = null;
		try {
			result = getEntityManager()
					.createQuery(sql, UnidadeQuantidade.class)
					.setParameter("abreviacao", abreviacao)
					.getSingleResult();
		} catch (NoResultException e) {
			// Situação não é exceção.
		}
		return result;
	}

	public List<UnidadeQuantidade> listarOrdenadoPorAbreviacao(int pagina, int tamanhoPagina) throws Exception {
		List<UnidadeQuantidade> result = null;
		String sql = "SELECT x FROM UnidadeQuantidade x "
				+ "ORDER BY x.abreviacao";
		result = getEntityManager()
				.createQuery(sql, UnidadeQuantidade.class)
				.setFirstResult(pagina * tamanhoPagina - tamanhoPagina)
				.setMaxResults(tamanhoPagina)
				.getResultList();
		return result;
	}

	public Long contar() throws Exception {
		String sql = "SELECT COUNT(x.id) FROM UnidadeQuantidade x ";
		
		return getEntityManager()
				.createQuery(sql, Long.class)
				.getSingleResult();
	}

}
