package dao;

import java.util.List;

import javax.ejb.Stateless;

import enums.Perfil;
import modelo.Conta;

@Stateless
public class ContaDao extends GenericDao<Conta> {

	public List<Conta> listarPorNome(String chave, int rows) throws Exception {
		
		List<Conta> result = null;
		
		// Recupera registros iniciando pela string pesquisada.
		String sql = "SELECT x FROM Conta x WHERE" +
				" upper(x.nome) like :iniciandoPor" +
				" order by x.nome";
		System.out.println(sql);
		result = getEntityManager()
				.createQuery(sql, Conta.class)
				.setParameter("iniciandoPor", chave.toUpperCase().concat("%"))
				.setMaxResults(rows)
				.getResultList();
		
		// Recupera registros contendo string pesquisada até completar a quantidade de linhas a retornar.
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
		System.out.println(sql);
		result = getEntityManager()
				.createQuery(sql, Conta.class)
				.setParameter("iniciandoPor", chave.toUpperCase().concat("%"))
				.setParameter("perfil", perfil)
				.setMaxResults(rows)
				.getResultList();
		
		// Recupera registros contendo string pesquisada até completar a quantidade de linhas a retornar.
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
