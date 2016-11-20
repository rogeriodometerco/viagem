package managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import enums.EstadoView;
import modelo.Municipio;
import servico.MunicipioService;
import util.JsfUtil;

@ManagedBean
@ViewScoped
public class MunicipioMb implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private MunicipioService municipioService;
	private List<Municipio> lista;
	private Municipio municipioEdicao;
	private Municipio municipioSelecionado;
	private EstadoView estadoView;
	
	@PostConstruct
	private void inicializar() {
		listar();
	}

	public void prepararNovo() {
		this.municipioEdicao = new Municipio();
		this.estadoView = EstadoView.INCLUSAO;
	}

	public void prepararEdicao() {
		try {
			this.municipioEdicao = municipioService.recuperar(municipioSelecionado.getId());
			this.estadoView = EstadoView.ALTERACAO;
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}

	public void salvar() {
		try {
			municipioService.salvar(municipioEdicao);
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
			this.lista = municipioService.listar();
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
	
	public List<Municipio> getLista() {
		return lista;
	}

	public Municipio getMunicipioEdicao() {
		return municipioEdicao;
	}

	public void setMunicipioEdicao(Municipio municipio) {
		this.municipioEdicao = municipio;
	}

	public List<Municipio> autocomplete(String query) {
		List<Municipio> result = new ArrayList<Municipio>();
		try {
			result = municipioService.listarPorNome(query, 10);
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
		return result;
	}

	public Municipio getMunicipioSelecionado() {
		return municipioSelecionado;
	}

	public void setMunicipioSelecionado(Municipio municipioSelecionado) {
		this.municipioSelecionado = municipioSelecionado;
	}
	
}
