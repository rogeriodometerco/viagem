package dao;

import java.util.List;

import javax.ejb.Stateless;

import modelo.PontoOperacao;

@Stateless
public class PontoOperacaoDao extends GenericDao<PontoOperacao> {
	
	public List<PontoOperacao> listar() throws Exception {
		return getEntityManager().createQuery("select x from PontoOperacao x").getResultList();
	}
	
	public List<Object[]> testar(String sql) throws Exception {
		return getEntityManager().createQuery(sql).getResultList();
	}
}
