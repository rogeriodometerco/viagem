package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import modelo.Municipio;

@Stateless
public class MunicipioDao extends GenericDao<Municipio> {

	public List<Municipio> listarPorNome(String chave, int rows) throws Exception {
		
		List<Municipio> result = null;
		String sql = "SELECT x FROM Municipio x WHERE" +
				" upper(x.nome) like :iniciandoPor" +
				" order by x.nome";
		result = getEntityManager()
				.createQuery(sql, Municipio.class)
				.setParameter("iniciandoPor", chave.toUpperCase().concat("%"))
				.setMaxResults(rows)
				.getResultList();
		return result;
	}

}
