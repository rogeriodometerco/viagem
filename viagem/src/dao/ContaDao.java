package dao;

import java.util.List;

import javax.ejb.Stateless;

import enums.Perfil;
import modelo.Conta;

@Stateless
public class ContaDao extends GenericDao<Conta> {

	public List<Conta> listarOrdenadoPorNome(int pagina, int tamanhoPagina) throws Exception {
		
		List<Conta> result = null;
		String sql = "SELECT x FROM Conta x " +
				" ORDER BY x.nome";
		result = getEntityManager()
				.createQuery(sql, Conta.class)
				.setFirstResult(pagina * tamanhoPagina - tamanhoPagina)
				.setMaxResults(tamanhoPagina)
				.getResultList();
		return result;
	}

	public Long contar() throws Exception {
		String sql = "SELECT COUNT(x) FROM Conta x";
		return getEntityManager()
				.createQuery(sql, Long.class)
				.getSingleResult();
	}

	public List<Conta> listarPorNomeOrdenadoPorNome(
			int pagina, int tamanhoPagina, String contendo) throws Exception {
		
		List<Conta> result = null;
		String sql = "SELECT x FROM Conta x WHERE" +
				" UPPER(x.nome) LIKE :contendo" +
				" ORDER BY x.nome";
		result = getEntityManager()
				.createQuery(sql, Conta.class)
				.setParameter("contendo", "%".concat(contendo.toUpperCase()).concat("%"))
				.setFirstResult(pagina * tamanhoPagina - tamanhoPagina)
				.setMaxResults(tamanhoPagina)
				.getResultList();
		return result;
	}

	public Long contarPorNome(String contendo) throws Exception {
		String sql = "SELECT COUNT(x) FROM Conta x WHERE" +
				" UPPER(x.nome) LIKE :contendo";
		return getEntityManager()
				.createQuery(sql, Long.class)
				.setParameter("contendo", "%".concat(contendo.toUpperCase()).concat("%"))
				.getSingleResult();
	}


	public List<Conta> listarPorPerfilOrdenadoPorNome(
			int pagina, int tamanhoPagina, Perfil perfil) throws Exception {
		
		List<Conta> result = null;
		String sql = "SELECT DISTINCT(c) FROM Conta c INNER JOIN c.perfis p WHERE" +
				" p.perfil= :perfil" +
				" ORDER BY c.nome";
		result = getEntityManager()
				.createQuery(sql, Conta.class)
				.setParameter("perfil", perfil)
				.setFirstResult(pagina * tamanhoPagina - tamanhoPagina)
				.setMaxResults(tamanhoPagina)
				.getResultList();
		return result;
	}

	public Long contarPorPerfil(Perfil perfil) throws Exception {
		String sql = "SELECT COUNT(x) FROM Conta x WHERE x IN ("
				+ " SELECT DISTINCT(c) FROM Conta c INNER JOIN c.perfis p WHERE" +
				" p.perfil= :perfil)";
		return getEntityManager()
				.createQuery(sql, Long.class)
				.setParameter("perfil", perfil)
				.getSingleResult();
	}


	public List<Conta> listarPorNomePerfilOrdenadoPorNome(
			int pagina, int tamanhoPagina, String contendo, Perfil perfil) throws Exception {
		
		List<Conta> result = null;
		
		String sql = "SELECT DISTINCT(c) FROM Conta c INNER JOIN c.perfis p WHERE" +
				" UPPER(c.nome) LIKE :contendo" +
				" AND p.perfil= :perfil" +
				" ORDER BY c.nome";
		result = getEntityManager()
				.createQuery(sql, Conta.class)
				.setParameter("contendo", "%".concat(contendo.toUpperCase()).concat("%"))
				.setParameter("perfil", perfil)
				.setFirstResult(pagina * tamanhoPagina - tamanhoPagina)
				.setMaxResults(tamanhoPagina)
				.getResultList();
		
		return result;
	}
	
	public Long contarPorNomePerfil(
			String contendo, Perfil perfil) throws Exception {
		
		String sql = "SELECT COUNT(x) FROM Conta x WHERE x IN (SELECT DISTINCT(c) FROM Conta c INNER JOIN c.perfis p WHERE" +
				" UPPER(c.nome) LIKE :contendo" +
				" AND p.perfil= :perfil)";
		return getEntityManager()
				.createQuery(sql, Long.class)
				.setParameter("contendo", "%".concat(contendo.toUpperCase()).concat("%"))
				.setParameter("perfil", perfil)
				.getSingleResult();
		
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<Conta> listarPorNome(String chave, int rows) throws Exception {
		
		List<Conta> result = null;
		
		// Recupera registros iniciando pela string pesquisada.
		String sql = "SELECT x FROM Conta x WHERE" +
				" upper(x.nome) like :iniciandoPor" +
				" order by x.nome";
		result = getEntityManager()
				.createQuery(sql, Conta.class)
				.setParameter("iniciandoPor", chave.toUpperCase().concat("%"))
				.setMaxResults(rows)
				.getResultList();
		
		// Recupera registros contendo string pesquisada at� completar a quantidade de linhas a retornar.
		if (result.size() < rows) {
			List<Conta> result2 = null;
			String sql2 = "SELECT x FROM Conta x WHERE" +
					" upper(x.nome) like :contendo" +
					" and upper(x.nome) not like :iniciandoPor" +
					" order by x.nome";
			result2 = getEntityManager()
					.createQuery(sql2, Conta.class)
					.setParameter("contendo", "%".concat(chave.toUpperCase()).concat("%"))
					.setParameter("iniciandoPor", chave.toUpperCase().concat("%"))
					.setMaxResults(rows - result.size())
					.getResultList();
			result.addAll(result2);
		}
		return result;
	}

	/*
	 * Lista contas que possuem determinado perfil
	 */
	public List<Conta> listarPorNome(Perfil perfil, String chave, int rows) throws Exception {
		List<Conta> result = null;
		// Recupera registros iniciando pela string pesquisada.
		String sql = "SELECT c FROM Conta c INNER JOIN c.perfis p WHERE" +
				" upper(c.nome) like :iniciandoPor" +
				" and p.perfil= :perfil" +
				" order by c.nome";
		result = getEntityManager()
				.createQuery(sql, Conta.class)
				.setParameter("iniciandoPor", chave.toUpperCase().concat("%"))
				.setParameter("perfil", perfil)
				.setMaxResults(rows)
				.getResultList();
		
		// Recupera registros contendo string pesquisada at� completar a quantidade de linhas a retornar.
		if (result.size() < rows) {
			List<Conta> result2 = null;
			String sql2 = "SELECT c FROM Conta c INNER JOIN c.perfis p WHERE" +
					" upper(c.nome) like :contendo" +
					" and upper(c.nome) not like :iniciandoPor" +
					" and p.perfil= :perfil" +
					" order by c.nome";
			result2 = getEntityManager()
					.createQuery(sql2, Conta.class)
					.setParameter("contendo", "%".concat(chave.toUpperCase()).concat("%"))
					.setParameter("iniciandoPor", chave.toUpperCase().concat("%"))
					.setParameter("perfil", perfil)
					.setMaxResults(rows - result.size())
					.getResultList();
			result.addAll(result2);
		}
		return result;
	}
	
}
