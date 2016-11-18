package managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import enums.Crud;
import modelo.Conta;
import modelo.Usuario;
import servico.UsuarioService;
import util.JsfUtil;

@ManagedBean
@ViewScoped
public class UsuarioMb implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private UsuarioService usuarioService;
	private List<Usuario> lista;

	private Usuario usuario;
	private Conta conta;
	private String confirmacaoSenha;
	
	
	private Crud estadoView;
	
	@PostConstruct
	private void inicializar() {
		listar();
	}
	/**
	 * Prepara a view para criação de usuário.
	 */
	public void prepararNovo() {
		usuario = new Usuario();
		conta = new Conta();
		estadoView = Crud.INCLUSAO;
	}

	/**
	 * Action responsável pela criação do usuário.
	 */
	public void criar() {
		try {
			// Por padrão, cria-se usuário com senha igual ao login.
			usuario.setSenha(usuario.getLogin());
			confirmacaoSenha = usuario.getSenha();
			usuarioService.criar(usuario, confirmacaoSenha);
			JsfUtil.addMsgSucesso("Usuário criado com sucesso");
			prepararNovo();
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}
	
	public void listar() {
		try {
			this.lista = usuarioService.listar();
			this.estadoView = Crud.CONSULTA;
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}

	public boolean estaEmModoCriacao() {
		return estadoView.equals(Crud.INCLUSAO);
	}
	
	public boolean estaEmModoListagem() {
		return estadoView.equals(Crud.CONSULTA);
	}
	
	public List<Usuario> getLista() {
		return lista;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}
	
	public Conta getConta() {
		return conta;
	}
	
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
}
