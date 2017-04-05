package dao;

import java.util.List;

import javax.ejb.Stateless;

import modelo.Produto;

@Stateless
public class ProdutoDao extends GenericDao<Produto> {

	public List<Produto> listarOrdenadoPorNome(int pagina, int tamanhoPagina) throws Exception {
		List<Produto> result = null;
		String sql = "SELECT x FROM Produto x " +
				" order by x.nome";
		result = getEntityManager()
				.createQuery(sql, Produto.class)
				.setFirstResult(pagina * tamanhoPagina - tamanhoPagina)
				.setMaxResults(tamanhoPagina)
				.getResultList();
		return result;
	}

	public Long contar() throws Exception {
		String sql = "SELECT COUNT(x) FROM Produto x";
		return getEntityManager()
				.createQuery(sql, Long.class)
				.getSingleResult();
	}


	public List<Produto> listarPorNomeOrdenadoPorNome(
			int pagina, int tamanhoPagina, String iniciandoPor) throws Exception {

		List<Produto> result = null;
		String sql = "SELECT x FROM Produto x WHERE" +
				" upper(x.nome) like :iniciandoPor" +
				" order by x.nome";
		result = getEntityManager()
				.createQuery(sql, Produto.class)
				.setParameter("iniciandoPor", iniciandoPor.toUpperCase().concat("%"))
				.setFirstResult(pagina * tamanhoPagina - tamanhoPagina)
				.setMaxResults(tamanhoPagina)
				.getResultList();
		return result;
	}

	public Long contarPorNome(String iniciandoPor) throws Exception {
		String sql = "SELECT COUNT(x) FROM Produto x WHERE" +
				" upper(x.nome) like :iniciandoPor";
		return getEntityManager()
				.createQuery(sql, Long.class)
				.setParameter("iniciandoPor", iniciandoPor.toUpperCase().concat("%"))
				.getSingleResult();
	}

	/*
	public List<Produto> listar(Filtro filtro, int pagina, int tamanhoPagina) throws Exception {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Produto> cq = cb.createQuery(Produto.class);
		Root<Produto> root = cq.from(Produto.class);

		for (Restricao r: filtro.getRestricoes()) {
			if (r.getChave().equals("nome")) {
				if (r.getRestricao().equals(Restricao.CONTEM)) {
					Path<String> name = root.get("name");
					cq.where(cb.and(cb.equal(name, r.getValor())));
				} else {
					throw new Exception("Critério para pesquisa não suportada");
				}
			} else {
				throw new Exception("Chave para pesquisa não suportada");
			}
		}
		TypedQuery<Produto> query = getEntityManager().createQuery(cq);
		query.setFirstResult(pagina * tamanhoPagina - tamanhoPagina);
		query.setMaxResults(tamanhoPagina);
		List<Produto> result = query.getResultList();
		return result;
	}
*/
	

/*
	public List<Produto> listarOrdenadoPorNome(int pagina, int tamanhoPagina) throws Exception {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);
		criteriaQuery.select(root);

		TypedQuery<Produto> query = getEntityManager().createQuery(criteriaQuery);
		query.setFirstResult(pagina * tamanhoPagina - tamanhoPagina);
		query.setMaxResults(tamanhoPagina);
		List<Produto> result = query.getResultList();
		return result;
	}
*/

}
