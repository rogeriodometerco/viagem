package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import modelo.Usuario;

@Stateless
public class UsuarioDao extends GenericDao<Usuario> {

	public Usuario recuperarPeloLoginSeExistir(String login) throws Exception {
		String sql = "SELECT x FROM Usuario x WHERE" +
				" x.login = :login";
		Usuario result = null;
		try {
			result = getEntityManager()
					.createQuery(sql, Usuario.class)
					.setParameter("login", login)
					.getSingleResult();
		} catch (NoResultException e) {
			// Situa��o n�o � exce��o.
		}
		return result;
	}


	public List<Usuario> listarPorNome(String chave, int rows) throws Exception {

		List<Usuario> result = null;

		// Recupera registros iniciando pela string pesquisada.
		String sql = "SELECT x FROM Usuario x WHERE" +
				" upper(x.nome) like :iniciandoPor" +
				" order by x.nome";

		result = getEntityManager()
				.createQuery(sql, Usuario.class)
				.setParameter("iniciandoPor", chave.toUpperCase().concat("%"))
				.setMaxResults(rows)
				.getResultList();

		// Recupera registros contendo string pesquisada at� completar a quantidade de linhas a retornar.
		if (result.size() < rows) {
			List<Usuario> result2 = null;
			String sql2 = "SELECT x FROM Usuario x WHERE" +
					" upper(x.nome) like :contendo" +
					" and upper(x.nome) not like :iniciandoPor" +
					" order by x.nome";
			result2 = getEntityManager()
					.createQuery(sql2, Usuario.class)
					.setParameter("contendo", "%".concat(chave.toUpperCase()).concat("%"))
					.setParameter("iniciandoPor", chave.toUpperCase().concat("%"))
					.setMaxResults(rows - result.size())
					.getResultList();
			result.addAll(result2);
		}
		return result;
	}


	public List<Usuario> listarOrdenadoPorNome(int pagina, int tamanhoPagina) throws Exception {

		List<Usuario> result = null;
		String sql = "SELECT x FROM Usuario x " +
				" ORDER BY x.nome";
		result = getEntityManager()
				.createQuery(sql, Usuario.class)
				.setFirstResult(pagina * tamanhoPagina - tamanhoPagina)
				.setMaxResults(tamanhoPagina)
				.getResultList();
		return result;
	}

	public Long contar() throws Exception {
		String sql = "SELECT COUNT(x) FROM Usuario x";
		return getEntityManager()
				.createQuery(sql, Long.class)
				.getSingleResult();
	}

	public List<Usuario> listarPorNomeOrdenadoPorNome(
			int pagina, int tamanhoPagina, String contendo) throws Exception {

		List<Usuario> result = null;
		String sql = "SELECT x FROM Usuario x WHERE" +
				" UPPER(x.nome) LIKE :contendo" +
				" ORDER BY x.nome";
		result = getEntityManager()
				.createQuery(sql, Usuario.class)
				.setParameter("contendo", "%".concat(contendo.toUpperCase()).concat("%"))
				.setFirstResult(pagina * tamanhoPagina - tamanhoPagina)
				.setMaxResults(tamanhoPagina)
				.getResultList();
		return result;
	}

	public Long contarPorNome(String contendo) throws Exception {
		String sql = "SELECT COUNT(x) FROM Usuario x WHERE" +
				" UPPER(x.nome) LIKE :contendo";
		return getEntityManager()
				.createQuery(sql, Long.class)
				.setParameter("contendo", "%".concat(contendo.toUpperCase()).concat("%"))
				.getSingleResult();
	}



}
