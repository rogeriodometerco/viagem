package dao;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import dto.Filtro;
import dto.Filtro.Restricao;
import dto.Listagem;
import exception.AppException;

public abstract class Catalogo<T> {

	protected Map<String, String> conversaoFiltroParaCampo;
	private final static String UNIT_NAME = "viagemPU";
	private Class<T> classeEntidade;

	@PersistenceContext(unitName = UNIT_NAME)
	private EntityManager em;


	@PostConstruct
	@SuppressWarnings("unchecked")
	public void inicializar() {
		this.classeEntidade = (Class<T>)((ParameterizedType)
				getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		inicializarConversaoFiltroParaCampo();
	}

	protected EntityManager getEntityManager() {
		return em;
	}

	public Listagem<T> listar(Filtro filtro) throws Exception {
		Listagem<T> listagem = new Listagem<T>();

		List<T> lista = new ArrayList<T>();

		try {
			lista = recuperarLista("SELECT " + classeEntidade.getSimpleName() + " FROM " + classeEntidade.getName() + " " + classeEntidade.getSimpleName(), filtro);
			Long count = realizarContagem("SELECT COUNT(" + classeEntidade.getSimpleName() + ") FROM " + classeEntidade.getName() + " " + classeEntidade.getSimpleName(), filtro);
			listagem.set(filtro.getPagina(), lista, count);
		} catch(Exception e) {
			throw new AppException("Erro ao listar " + classeEntidade.getName() + ": " + e.getMessage(), e);
		}
		return listagem;
	}

	public List<T> recuperarLista(String select, Filtro filtro) throws Exception {
		List<T> result = null;
		try {

			String sql = montarSql(select, filtro);
			TypedQuery<T> query = getEntityManager()
					.createQuery(sql, classeEntidade)
					.setFirstResult((int)(filtro.getPagina() * filtro.getTamanhoPagina() - filtro.getTamanhoPagina()))
					.setMaxResults(filtro.getTamanhoPagina());
			inicializarParametros(query, filtro);
			result = query.getResultList();
		} catch(Exception e) {
			throw new AppException("Erro ao listar " + classeEntidade.getName() + ": " + e.getMessage(), e);
		}
		return result;
	}

	public Long realizarContagem(String select, Filtro filtro) throws Exception {
		Long count = 0l;
		try {
			String sql = montarSql(select, filtro);
			TypedQuery<Long> query = getEntityManager()
					.createQuery(sql, Long.class);
			inicializarParametros(query, filtro);

			count = query.getSingleResult();
		} catch(Exception e) {
			throw new AppException("Erro ao contar registros " + classeEntidade.getName() + ": " + e.getMessage(), e);
		}
		return count;
	}

	private String montarSql(String sqlParcial, Filtro filtro) {
		StringBuffer sql = new StringBuffer(sqlParcial);//("SELECT x FROM " + classeEntidade.getName() + " x");
		int i = 0;

		for (Restricao r: filtro.getRestricoes()) {
			String condicao = 
					conversaoFiltroParaCampo.get(r.getChave())
					+ " ".concat(r.getOperador())
					+ " :".concat(r.getChave());
			if (i == 0) {
				sql.append(" WHERE");
			} else {
				sql.append(" AND");
			}
			sql.append(" ").append(condicao);
			i++;
		}
		return sql.toString();
	}


	protected void inicializarParametros(TypedQuery<?> query, Filtro filtro) {
		for (Restricao r: filtro.getRestricoes()) {
			String chave = r.getChave();
			query.setParameter(chave, r.getValor());
		}
	}

	protected abstract void inicializarConversaoFiltroParaCampo();

}
