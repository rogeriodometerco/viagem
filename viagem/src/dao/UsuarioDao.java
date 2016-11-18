package dao;

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
}
