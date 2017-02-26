package dao;

import java.util.List;

import javax.ejb.Stateless;

import modelo.Municipio;

@Stateless
public class MunicipioDao extends GenericDao<Municipio> {

	public List<Municipio> listarOrdenadoPorNome(int pagina, int tamanhoPagina) throws Exception {
		
		List<Municipio> result = null;
		String sql = "SELECT x FROM Municipio x " +
				" order by x.nome";
		result = getEntityManager()
				.createQuery(sql, Municipio.class)
				.setFirstResult(pagina * tamanhoPagina - tamanhoPagina)
				.setMaxResults(tamanhoPagina)
				.getResultList();
		return result;
	}

	public Long contar() throws Exception {
		String sql = "SELECT COUNT(x) FROM Municipio x";
		return getEntityManager()
				.createQuery(sql, Long.class)
				.getSingleResult();
	}

	public List<Municipio> listarPorNomeOrdenadoPorNome(
			String iniciandoPor, int pagina, int tamanhoPagina) throws Exception {
		
		List<Municipio> result = null;
		String sql = "SELECT x FROM Municipio x WHERE" +
				" upper(x.nome) like :iniciandoPor" +
				" order by x.nome";
		result = getEntityManager()
				.createQuery(sql, Municipio.class)
				.setParameter("iniciandoPor", iniciandoPor.toUpperCase().concat("%"))
				.setFirstResult(pagina * tamanhoPagina - tamanhoPagina)
				.setMaxResults(tamanhoPagina)
				.getResultList();
		return result;
	}

	public Long contarPorNome(String iniciandoPor) throws Exception {
		String sql = "SELECT COUNT(x) FROM Municipio x WHERE" +
				" upper(x.nome) like :iniciandoPor";
		return getEntityManager()
				.createQuery(sql, Long.class)
				.setParameter("iniciandoPor", iniciandoPor.toUpperCase().concat("%"))
				.getSingleResult();
	}

	
}
