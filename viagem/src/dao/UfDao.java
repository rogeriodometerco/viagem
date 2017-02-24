package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import modelo.UF;

@Stateless
public class UfDao extends GenericDao<UF> {

	public List<UF> listarPorNome(String chave, int rows) throws Exception {
		
		List<UF> result = null;
		String sql = "SELECT x FROM UF x WHERE" +
				" upper(x.nome) like :iniciandoPor" +
				" order by x.nome";
		result = getEntityManager()
				.createQuery(sql, UF.class)
				.setParameter("iniciandoPor", chave.toUpperCase().concat("%"))
				.setMaxResults(rows)
				.getResultList();
		return result;
	}

	public UF recuperarPelaAbreviaturaSeExistir(String abreviatura) throws Exception {
		String sql = "SELECT x FROM UF x WHERE" +
				" x.abreviatura = :abreviatura";
		UF result = null;
		try {
			result = getEntityManager()
					.createQuery(sql, UF.class)
					.setParameter("abreviatura", abreviatura)
					.getSingleResult();
		} catch (NoResultException e) {
			// Situação não é exceção.
		}
		return result;
	}

	public List<UF> listarOrdenadoPorAbreviatura(int pagina, int tamanhoPagina) throws Exception {
		List<UF> result = null;
		String sql = "SELECT x FROM UF x "
				+ "ORDER BY x.abreviatura";
		result = getEntityManager()
				.createQuery(sql, UF.class)
				.setFirstResult(pagina * tamanhoPagina - tamanhoPagina)
				.setMaxResults(tamanhoPagina)
				.getResultList();
		return result;
	}

	public Long contarRegistros() throws Exception {
		String sql = "SELECT COUNT(x.id) FROM UF x ";
		
		return getEntityManager()
				.createQuery(sql, Long.class)
				.getSingleResult();
	}

}
