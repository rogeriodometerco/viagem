package dao;

import java.util.List;

import javax.ejb.Stateless;

import modelo.DemandaTransporte;

@Stateless
public class DemandaTomadorViewDao extends GenericDao<DemandaTransporte> {

	public List<DemandaTransporte> listarOrdenadoPorIdDescendente(int pagina, int tamanhoPagina) 
			throws Exception {
		
		List<DemandaTransporte> result = null;
		String sql = "SELECT x FROM DemandaTransporte x " +
				" ORDER BY x.id DESC";
		result = getEntityManager()
				.createQuery(sql, DemandaTransporte.class)
				.setFirstResult(pagina * tamanhoPagina - tamanhoPagina)
				.setMaxResults(tamanhoPagina)
				.getResultList();
		return result;
	}

	public Long contar() throws Exception {
		String sql = "SELECT COUNT(x) FROM DemandaTransporte x";
		return getEntityManager()
				.createQuery(sql, Long.class)
				.getSingleResult();
	}
}
