package managedbean;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import enums.EstadoView;
import modelo.Conta;
import modelo.PontoViagem;
import modelo.Viagem;
import servico.PontoViagemService;
import servico.SessaoService;
import servico.ViagemService;
import util.JsfUtil;

@ManagedBean
@ViewScoped
public class ViagemMotoristaMb {

	@EJB
	private ViagemService viagemService;
	@EJB
	private PontoViagemService pontoViagemService;
	@EJB
	private SessaoService sessaoService;

	private List<Viagem> viagens;
	private Viagem viagemSelecionada;
	private Conta motorista;
	private PontoViagem pontoViagem;
	private EstadoView estadoView;

	@PostConstruct
	private void inicializar() throws Exception {
		this.motorista = sessaoService.getConta();
		listar();
	}

	private void carregarViagens() {
		try {
			this.viagens = viagemService.recuperarViagensDoMotorista(motorista);
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}
	
	public void listar() {
		carregarViagens();
		irParaModoListagem();
	}
	
	public List<Viagem> getViagens() {
		return viagens;
	}
	
	public Boolean estaEmModoListagem() {
		return this.estadoView.equals(EstadoView.LISTAGEM);
	}
	
	public Boolean estaEmModoDetalhe() {
		return this.estadoView.equals(EstadoView.ALTERACAO);
	}
	
	public void setViagemSelecionada(Viagem selecionada) {
		this.viagemSelecionada = selecionada;
	}
	
	public void irParaModoListagem() {
		this.estadoView = EstadoView.LISTAGEM;
	}
	
	public Conta getMotorista() {
		return motorista;
	}

	public void setMotorista(Conta motorista) {
		this.motorista = motorista;
	}
}
