package facade;

import javax.ejb.Stateless;

import modelo.Usuario;

@Stateless
public class UsuarioFacade extends GenericFacade<Usuario> {

	public Usuario recuperarPeloLogin(String login) throws Exception {
		String sql = "SELECT x FROM Usuario x WHERE" +
				" x.login = :login";
		return getEntityManager()
				.createQuery(sql, Usuario.class)
				.setParameter("login", login)
				.getSingleResult();
	}
}
