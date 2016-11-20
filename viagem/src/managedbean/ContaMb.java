package managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import enums.EstadoView;
import enums.Perfil;
import modelo.AdminConta;
import modelo.Conta;
import modelo.PerfilConta;
import modelo.Usuario;
import servico.ContaService;
import util.JsfUtil;

@ManagedBean
@ViewScoped
public class ContaMb implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private ContaService contaService;
	private List<Conta> lista;

	private Conta contaEdicao;
	private Conta contaSelecionada;
	private List<Perfil> perfisEdicao;
	private List<Usuario> administradoresEdicao;
	private EstadoView estadoView;
	
	@PostConstruct
	private void inicializar() {
		listar();
	}
	/**
	 * Prepara a view para criação de conta.
	 */
	public void prepararNovo() {
		this.contaEdicao = new Conta();
		this.contaEdicao.setPerfis(new HashSet<PerfilConta>());
		this.contaEdicao.setAdministradores(new HashSet<AdminConta>());
		this.estadoView = EstadoView.INCLUSAO;
		this.perfisEdicao = new ArrayList<Perfil>();
		this.administradoresEdicao = new ArrayList<Usuario>();
	}

	/**
	 * Prepara a view para edição de conta.
	 */
	public void prepararEdicao() {
		try {
			this.contaEdicao = contaService.recuperar(contaSelecionada.getId());
			if (contaEdicao.getPerfis() == null) {
				this.contaEdicao.setPerfis(new HashSet<PerfilConta>());
			}
			this.perfisEdicao = new ArrayList<Perfil>();
			for (PerfilConta perfilConta: contaEdicao.getPerfis()) {
				perfisEdicao.add(perfilConta.getPerfil());
			}
			this.administradoresEdicao = new ArrayList<Usuario>();
			if (contaEdicao.getAdministradores() == null) {
				this.contaEdicao.setAdministradores(new HashSet<AdminConta>());
			}
			for (AdminConta adminConta: contaEdicao.getAdministradores()) {
				administradoresEdicao.add(adminConta.getUsuario());
			}
			this.estadoView = EstadoView.ALTERACAO;
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}
	/**
	 * Action responsável por salvar a conta.
	 */
	public void salvar() {
		try {
			sincronizarPerfisParaSalvar();
			contaService.salvar(contaEdicao);
			JsfUtil.addMsgSucesso("Conta salva com sucesso");
			if (estadoView.equals(EstadoView.INCLUSAO)) {
				prepararNovo();
			} else {
				listar();
			}
		} catch (Exception e) {
			JsfUtil.addMsgErro("Erro ao salvar informações: " + e.getMessage());
		}
	}
	
	private void sincronizarPerfisParaSalvar() {
		if (perfisEdicao == null) {
			perfisEdicao = new ArrayList<Perfil>();
		}
		if (administradoresEdicao == null) {
			administradoresEdicao = new ArrayList<Usuario>();
		}
		
		// Remove perfis que não estão mais na lista.
		List<PerfilConta> perfisARemover = new ArrayList<PerfilConta>();
		for (PerfilConta perfilConta: contaEdicao.getPerfis()) {
			if (!perfisEdicao.contains(perfilConta.getPerfil())) {
				perfisARemover.add(perfilConta);
			}
		}
		contaEdicao.getPerfis().removeAll(perfisARemover);

		// Adiciona os novos perfis.
		for (Perfil perfil: perfisEdicao) {
			boolean contem = false;
			for (PerfilConta perfilConta: contaEdicao.getPerfis()) {
				if (perfil.equals(perfilConta.getPerfil())) {
					contem = true;
					break;
				}
			}
			if (!contem) {
				PerfilConta perfilConta = new PerfilConta();
				perfilConta.setConta(contaEdicao);
				perfilConta.setPerfil(perfil);
				contaEdicao.getPerfis().add(perfilConta);
			}
		}

	
		// Remove administradores que não estão mais na lista.
		List<AdminConta> adminsARemover = new ArrayList<AdminConta>();
		for (AdminConta adminConta: contaEdicao.getAdministradores()) {
			if (!administradoresEdicao.contains(adminConta.getUsuario())) {
				adminsARemover.add(adminConta);
			}
		}
		contaEdicao.getAdministradores().removeAll(adminsARemover);
		// Adiciona os novos administradores.
		for (Usuario usuario: administradoresEdicao) {
			boolean contem = false;
			for (AdminConta adminConta: contaEdicao.getAdministradores()) {
				if (usuario.equals(adminConta.getUsuario())) {
					contem = true;
					break;
				}
			}
			if (!contem) {
				AdminConta adminConta = new AdminConta();
				adminConta.setConta(contaEdicao);
				adminConta.setUsuario(usuario);
				contaEdicao.getAdministradores().add(adminConta);
			}
		}
}
	
	public void listar() {
		try {
			this.lista = contaService.listar();
			this.contaSelecionada = null;
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
	
	public List<Conta> getLista() {
		return lista;
	}

	public Conta getContaEdicao() {
		return contaEdicao;
	}

	public void setContaEdicao(Conta conta) {
		this.contaEdicao = conta;
	}
	
	public void setContaSelecionada(Conta conta) {
		this.contaSelecionada = conta;
	}
	
	public Conta getContaSelecionada() {
		return contaSelecionada;
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
	
	public List<Perfil> getPerfisEdicao() {
		return perfisEdicao;
	}
	
	public void setPerfisEdicao(List<Perfil> perfisEdicao) {
		this.perfisEdicao = perfisEdicao;
	}
	
	public List<Usuario> getAdministradoresEdicao() {
		return administradoresEdicao;
	}
	
	public void setAdministradoresEdicao(List<Usuario> administradores) {
		this.administradoresEdicao = administradores;
	}

	
}
