package managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import enums.Crud;
import modelo.Conta;
import servico.ContaService;
import util.JsfUtil;

@ManagedBean
@ViewScoped
public class ContaMb implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private ContaService contaService;
	private List<Conta> lista;

	private Conta conta;
	private Crud estadoView;
	
	@PostConstruct
	private void inicializar() {
		listar();
	}
	/**
	 * Prepara a view para criação de conta.
	 */
	public void prepararNovo() {
		this.conta = new Conta();
		this.estadoView = Crud.INCLUSAO;
	}

	/**
	 * Prepara a view para edição de conta.
	 */
	public void prepararEdição(Conta conta) {
		this.conta = conta;
		this.estadoView = Crud.ALTERACAO;
	}
	/**
	 * Action responsável pela criação do conta.
	 */
	public void salvar() {
		try {
			contaService.salvar(conta);
			JsfUtil.addMsgSucesso("Conta salva com sucesso");
			prepararNovo();
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}
	
	public void listar() {
		try {
			this.lista = contaService.listar();
			this.estadoView = Crud.CONSULTA;
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}

	public boolean estaEmModoCriacao() {
		return estadoView.equals(Crud.INCLUSAO);
	}
	
	public boolean estaEmModoEdicao() {
		return estadoView.equals(Crud.ALTERACAO);
	}
	
	public boolean estaEmModoListagem() {
		return estadoView.equals(Crud.CONSULTA);
	}
	
	public List<Conta> getLista() {
		return lista;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
	public List<Conta> autocomplete(String query) {
		List<Conta> result = new ArrayList<Conta>();
		try {
			result = contaService.listarPorNome(query, 10);
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
		return result;
	}

}
