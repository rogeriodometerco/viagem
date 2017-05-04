package managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.PontoOperacaoDao;
import enums.EstadoView;
import modelo.PontoOperacao;
import util.JsfUtil;

@ManagedBean
@ViewScoped
public class PontoOperacaoMb implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private PontoOperacaoDao pontoOperacaoDao;
	private List<Object[]> lista;
	private String sql;
	
	@PostConstruct
	private void inicializar() {
		listar();
	}

	public void listar() {
		try {
			this.lista = pontoOperacaoDao.testar(sql);
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
