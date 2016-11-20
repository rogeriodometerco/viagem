package dao;

import java.util.List;

import javax.ejb.Stateless;

import modelo.Conta;
import modelo.UsuarioConta;

@Stateless
public class UsuarioContaDao extends GenericDao<UsuarioConta> {

	public List<UsuarioConta> listarPorConta(Conta conta) throws Exception {
		String sql = "SELECT x FROM UsuarioConta x WHERE" +
				" x.conta = :conta";

		return getEntityManager()
				.createQuery(sql, UsuarioConta.class)
				.setParameter("conta", conta)
				.getResultList();
	}


}
