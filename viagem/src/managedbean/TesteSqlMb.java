package managedbean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.PontoOperacaoDao;
import util.JsfUtil;

@ManagedBean
@ViewScoped
public class TesteSqlMb implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private PontoOperacaoDao pontoOperacaoDao;
	private List<Object[]> lista;
	private String sql;
	private Long tempo;
	
	
	public Long getTempo() {
		return tempo;
	}

	public void setTempo(Long tempo) {
		this.tempo = tempo;
	}

	public void setLista(List<Object[]> lista) {
		this.lista = lista;
	}

	@PostConstruct
	private void inicializar() {
		listar();
	}

	public void listar() {
		try {
			Long t1 = System.currentTimeMillis();
			this.lista = pontoOperacaoDao.testar(sql);
			Long t2 = System.currentTimeMillis();
			tempo = t2 - t1;
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
 	}
	
	public void listarOperacoes() {
		try {
			Map<String, Object> filtros = new HashMap<String, Object>();	
			filtros.put("demandaId", 110l);
			Long t1 = System.currentTimeMillis();
			this.lista = pontoOperacaoDao.listarFiltroMap(filtros);
			Long t2 = System.currentTimeMillis();
			tempo = t2 - t1;
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
 	}
	public List<Object[]> getLista() {
		return lista;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	
}
