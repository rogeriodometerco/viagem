package facade;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import exception.AppPersistenceException;


public abstract class GenericFacade<T> {

	private final static String UNIT_NAME = "viagemPU";

	@PersistenceContext(unitName = UNIT_NAME)
	private EntityManager em;

	private Class<T> classeEntidade;

	@PostConstruct
	@SuppressWarnings("unchecked")
	public void inicializar() {
		this.classeEntidade = (Class<T>)((ParameterizedType)
				getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	protected EntityManager getEntityManager() {
		return em;
	}

	public T recuperar(Object id) throws Exception {
		try {
			return (T)getEntityManager().find(classeEntidade, id);
		} catch (Exception e) {
			throw new AppPersistenceException("Erro ao recuperar entidade " 
					+ classeEntidade.getSimpleName() + ": "
					+ e.getMessage());
		}
	}

	public T salvar(T entidade) throws Exception {
		try {
			entidade = this.getEntityManager().merge(entidade);
			return entidade;
		} catch (Exception e) {
			throw new AppPersistenceException("Erro ao salvar entidade " 
					+ classeEntidade.getSimpleName() + ": "
					+ e.getMessage());
		}
	}

	public List<T> salvar(List<T> lista) throws Exception {
		try {
			List<T> retorno = new ArrayList<T>();
			for (T entidade: lista) {
				retorno.add(salvar(entidade));
			}
			return retorno;
		} catch (Exception e) {
			throw new AppPersistenceException("Erro ao salvar entidades " 
					+ classeEntidade.getSimpleName() + ": "
					+ e.getMessage());
		}
	}

	public void excluir(T entidade) throws Exception {
		try {
			this.getEntityManager().remove(
						getEntityManager().merge(entidade));
		} catch (Exception e) {
			throw new AppPersistenceException("Erro ao excluir entidade " 
					+ classeEntidade.getSimpleName() + ": "
					+ e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> listar() throws Exception {
		try {
		/*
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<T> cq = cb.createQuery(classeEntidade);
		Root<T> root = cq.from(classeEntidade);
		cq.select(root);

		TypedQuery<T> q = em.createQuery(cq);
		return q.getResultList();
		*/
		return this.getEntityManager()
				.createQuery("select x from " + classeEntidade.getSimpleName() + " x")
				.getResultList();
		} catch (Exception e) {
			throw new AppPersistenceException("Erro ao listar entidades " 
					+ classeEntidade.getSimpleName() + ": "
					+ e.getMessage());
		}
	}
}