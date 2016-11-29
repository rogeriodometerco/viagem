package managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import enums.EstadoView;
import modelo.Estabelecimento;
import servico.EstabelecimentoService;
import util.JsfUtil;

@ManagedBean
@ViewScoped
public class EstabelecimentoMb implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private EstabelecimentoService estabelecimentoService;
	private List<Estabelecimento> lista;
	private Estabelecimento estabelecimentoEdicao;
	private Estabelecimento estabelecimentoSelecionado;
	private EstadoView estadoView;
	
	@PostConstruct
	private void inicializar() {
		listar();
	}

	public void prepararNovo() {
		this.estabelecimentoEdicao = new Estabelecimento();
		this.estadoView = EstadoView.INCLUSAO;
	}

	public void prepararEdicao() {
		try {
			this.estabelecimentoEdicao = estabelecimentoService.recuperar(estabelecimentoSelecionado.getId());
			this.estadoView = EstadoView.ALTERACAO;
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}

	public void salvar() {
		try {
			estabelecimentoService.salvar(estabelecimentoEdicao);
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
			this.lista = estabelecimentoService.listar();
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
	
	public List<Estabelecimento> getLista() {
		return lista;
	}

	public Estabelecimento getEstabelecimentoEdicao() {
		return estabelecimentoEdicao;
	}

	public void setEstabelecimentoEdicao(Estabelecimento estabelecimento) {
		this.estabelecimentoEdicao = estabelecimento;
	}

	public List<Estabelecimento> autocomplete(String query) {
		List<Estabelecimento> result = new ArrayList<Estabelecimento>();
		try {
			result = estabelecimentoService.listarPorNome(query, 10);
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
		return result;
	}

	public Estabelecimento getEstabelecimentoSelecionado() {
		return estabelecimentoSelecionado;
	}

	public void setEstabelecimentoSelecionado(Estabelecimento estabelecimentoSelecionado) {
		this.estabelecimentoSelecionado = estabelecimentoSelecionado;
	}
	
}
