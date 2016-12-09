package managedbean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import modelo.PontoViagem;
import modelo.Viagem;
import servico.ViagemService;
import util.JsfUtil;

@ManagedBean
@ViewScoped
public class ViagemMotoristaMb {

	@EJB
	private ViagemService viagemService;
	
	private Viagem viagem;
	private PontoViagem pontoViagem;
	
	@PostConstruct
	private void inicializar() {
		carregarViagem();
	}
	
	private void carregarViagem() {
		try {
			viagem = viagemService.obterViagemEmFocoDoMotoristaLogado();
			System.out.println(viagem.getId());
			System.out.println(viagem.getEtapas().iterator().next().getOrigem().getNome());
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}
	public void iniciarViagem() {
		try {
			viagemService.iniciarViagem(viagem);
			carregarViagem();
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}
	
	public void registrarPrevisaoChegada() {
		try {
			viagemService.registrarPrevisaoChegada(pontoViagem);
			carregarViagem();
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}
	
	public void registrarChegada() {
		
	}
	
	public void registrarSaida() {
		
	}
	
	public void registrarTerminoOperacao() {
		
	}

	public Viagem getViagem() {
		return viagem;
	}

	public PontoViagem getPontoViagem() {
		return pontoViagem;
	}

	public void setPontoViagem(PontoViagem pontoViagem) {
		this.pontoViagem = pontoViagem;
	}

	
}
