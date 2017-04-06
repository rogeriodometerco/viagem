package managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import enums.EstadoView;
import modelo.Conta;
import modelo.Veiculo;
import servico.VeiculoService;
import util.JsfUtil;

@ManagedBean
@ViewScoped
public class VeiculoMb implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private VeiculoService veiculoService;
	private List<Veiculo> lista;
	private Veiculo veiculoEdicao;
	private Veiculo veiculoSelecionado;
	private EstadoView estadoView;
	
	@PostConstruct
	private void inicializar() {
		listar();
	}

	public void prepararNovo() {
		this.veiculoEdicao = new Veiculo();
		this.estadoView = EstadoView.INCLUSAO;
	}

	public void prepararEdicao() {
		try {
			this.veiculoEdicao = veiculoService.recuperar(veiculoSelecionado.getId());
			this.estadoView = EstadoView.ALTERACAO;
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}

	public void salvar() {
		try {
			veiculoService.salvar(veiculoEdicao);
			JsfUtil.addMsgSucesso("Informa��es salvas com sucesso");
			if (estaEmModoCriacao()) {
				prepararNovo();
			} else {
				listar();
			}
		} catch (Exception e) {
			JsfUtil.addMsgErro("Erro ao salvar informa��es: " + e.getMessage());
		}
	}
	
	public void listar() {
		try {
			this.lista = veiculoService.listar();
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
	
	public List<Veiculo> getLista() {
		return lista;
	}

	public Veiculo getVeiculoEdicao() {
		return veiculoEdicao;
	}

	public void setVeiculoEdicao(Veiculo veiculo) {
		this.veiculoEdicao = veiculo;
	}

	public Veiculo getVeiculoSelecionado() {
		return veiculoSelecionado;
	}

	public void setVeiculoSelecionado(Veiculo veiculoSelecionado) {
		this.veiculoSelecionado = veiculoSelecionado;
	}

	public List<Veiculo> autocomplete(String query) {
		List<Veiculo> result = new ArrayList<Veiculo>();
		try {
			result = veiculoService.listarPelaPlaca(query, 10);
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
		return result;
	}

}
