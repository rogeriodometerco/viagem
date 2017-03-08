package managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import enums.EstadoView;
import modelo.UF;
import servico.UfService;
import util.JsfUtil;

@ManagedBean
@ViewScoped
public class UfMb implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private UfService ufService;
	private List<UF> lista;
	private UF ufEdicao;
	private UF ufSelecionada;
	private EstadoView estadoView;
	
	@PostConstruct
	private void inicializar() {
		listar();
	}

	public void prepararNovo() {
		this.ufEdicao = new UF();
		this.estadoView = EstadoView.INCLUSAO;
	}

	public void prepararEdicao() {
		try {
			this.ufEdicao = ufService.recuperar(ufSelecionada.getId());
			this.estadoView = EstadoView.ALTERACAO;
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}

	public void salvar() {
		try {
			ufService.salvar(ufEdicao);
			JsfUtil.addMsgSucesso("Informações salvas com sucesso");
			if (estaEmModoCriacao()) {
				prepararNovo();
			} else {
				listar();
			}
		} catch (Exception e) {
			JsfUtil.addMsgErro("Erro ao salvar informações: " + e.getMessage());
		}
	}
	
	public void listar() {
		try {
			this.lista = ufService.listar();
			this.estadoView = EstadoView.LISTAGEM;
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}

	public boolean estaEmModoCriacao() {
		return estadoView.equals(EstadoView.INCLUSAO);
	}
	
	public boolean estaEmModoEdicao() {
		return estadoView.equals(EstadoView.ALTERACAO);
	}
	
	public boolean estaEmModoListagem() {
		return estadoView.equals(EstadoView.LISTAGEM);
	}
	
	public List<UF> getLista() {
		return lista;
	}

	public UF getUfEdicao() {
		return ufEdicao;
	}

	public void setUfEdicao(UF uf) {
		this.ufEdicao = uf;
	}

	public List<UF> autocomplete(String query) {
		List<UF> result = new ArrayList<UF>();
		try {
			result = ufService.listarPorNome(query, 10);
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
		return result;
	}

	public UF getUfSelecionada() {
		return ufSelecionada;
	}

	public void setUfSelecionada(UF ufSelecionada) {
		this.ufSelecionada = ufSelecionada;
	}
	
}
