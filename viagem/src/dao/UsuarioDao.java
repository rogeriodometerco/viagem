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
			// Situação não é exceção.
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
		
		// Recupera registros contendo string pesquisada até completar a quantidade de linhas a retornar.
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
}
