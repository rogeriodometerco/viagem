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
			int pagina, int tamanhoPagina, String iniciandoPor) throws Exception {
		
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

	/*
	public List<Municipio> listar(ParametrosListagem params) throws Exception {
		List<Municipio> lista = new ArrayList<Municipio>();
		
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Municipio> criteriaQuery = criteriaBuilder.createQuery(Municipio.class);
		Root<Municipio> municipioRoot = criteriaQuery.from(Municipio.class);
		

		for (Filtro filtro: params.getFiltros()) {
			if (filtro.getRestricao().toUpperCase().equals(Filtro.IGUAL)) {
				System.out.println("igual");
				criteriaQuery.where(criteriaBuilder.equal(municipioRoot.get(filtro.getChave()), filtro.getValor()));
			} else if (filtro.getRestricao().toUpperCase().equals(Filtro.INICIA)) {
				System.out.println("inicia");
				criteriaQuery.where(criteriaBuilder.like(municipioRoot.get(filtro.getChave()), ((String)filtro.getValor()).concat("%")));
			} else if (filtro.getRestricao().toUpperCase().equals(Filtro.TERMINA)) {
				System.out.println("termina");
				criteriaQuery.where(criteriaBuilder.like(municipioRoot.get(filtro.getChave()), ("%".concat((String)filtro.getValor()))));
			} else if (filtro.getRestricao().toUpperCase().equals(Filtro.CONTEM)) {
				System.out.println("contem");
				criteriaQuery.where(criteriaBuilder.like(municipioRoot.get(filtro.getChave()), ("%".concat((String)filtro.getValor()).concat("%"))));
			}
		}

		TypedQuery<Municipio> query = getEntityManager().createQuery(criteriaQuery);
		lista = query.getResultList();

		return lista;
	}
	*/
}
 