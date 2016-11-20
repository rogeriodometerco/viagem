package dao;

import java.util.List;

import javax.ejb.Stateless;

import modelo.AdminConta;
import modelo.Conta;
import modelo.UsuarioConta;

@Stateless
public class AdminContaDao extends GenericDao<AdminConta> {

	public List<AdminConta> listarPorConta(Conta conta) throws Exception {
		String sql = "SELECT x FROM AdminConta x WHERE" +
				" x.conta = :conta";

		return getEntityManager()
				.createQuery(sql, AdminConta.class)
				.setParameter("conta", conta)
				.getResultList();
	}


}