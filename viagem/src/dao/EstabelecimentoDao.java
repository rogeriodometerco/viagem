package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import modelo.Estabelecimento;
import modelo.Estabelecimento;
import modelo.Estabelecimento;

@Stateless
public class EstabelecimentoDao extends GenericDao<Estabelecimento> {

	public List<Estabelecimento> listarPorNome(String chave, int rows) throws Exception {
		List<Estabelecimento> result = null;
		// Recupera registros iniciando pela string pesquisada.
		String sql = "SELECT x FROM Estabelecimento x WHERE" +
				" upper(x.nome) like :iniciandoPor" +
				" order by x.nome";
		result = getEntityManager()
				.createQuery(sql, Estabelecimento.class)
				.setParameter("iniciandoPor", chave.toUpperCase().concat("%"))
				.setMaxResults(rows)
				.getResultList();
		
		// Recupera registros contendo string pesquisada até completar a quantidade de linhas a retornar.
		if (result.size() < rows) {
			List<Estabelecimento> result2 = null;
			String sql2 = "SELECT x FROM Estabelecimento x WHERE" +
					" upper(x.nome) like :contendo" +
					" and upper(x.nome) not like :iniciandoPor" +
					" order by x.nome";
			result2 = getEntityManager()
					.createQuery(sql2, Estabelecimento.class)
					.setParameter("contendo", "%".concat(chave.toUpperCase()).concat("%"))
					.setParameter("iniciandoPor", chave.toUpperCase().concat("%"))
					.setMaxResults(rows - result.size())
					.getResultList();
			result.addAll(result2);
		}
		return result;
	}

	
	public List<Estabelecimento> listarOrdenadoPorNome(int pagina, int tamanhoPagina) throws Exception {
		
		List<Estabelecimento> result = null;
		String sql = "SELECT x FROM Estabelecimento x " +
				" order by x.nome";
		result = getEntityManager()
				.createQuery(sql, Estabelecimento.class)
				.setFirstResult(pagina * tamanhoPagina - tamanhoPagina)
				.setMaxResults(tamanhoPagina)
				.getResultList();
		return result;
	}

	public Long contar() throws Exception {
		String sql = "SELECT COUNT(x) FROM Estabelecimento x";
		return getEntityManager()
				.createQuery(sql, Long.class)
				.getSingleResult();
	}

	public List<Estabelecimento> listarPorNomeOrdenadoPorNome(
			int pagina, int tamanhoPagina, String iniciandoPor) throws Exception {
		
		List<Estabelecimento> result = null;
		String sql = "SELECT x FROM Estabelecimento x WHERE" +
				" upper(x.nome) like :iniciandoPor" +
				" order by x.nome";
		result = getEntityManager()
				.createQuery(sql, Estabelecimento.class)
				.setParameter("iniciandoPor", iniciandoPor.toUpperCase().concat("%"))
				.setFirstResult(pagina * tamanhoPagina - tamanhoPagina)
				.setMaxResults(tamanhoPagina)
				.getResultList();
		return result;
	}

	public Long contarPorNome(String iniciandoPor) throws Exception {
		String sql = "SELECT COUNT(x) FROM Estabelecimento x WHERE" +
				" upper(x.nome) like :iniciandoPor";
		return getEntityManager()
				.createQuery(sql, Long.class)
				.setParameter("iniciandoPor", iniciandoPor.toUpperCase().concat("%"))
				.getSingleResult();
	}

}
