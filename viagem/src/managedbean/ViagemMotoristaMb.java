package managedbean;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import enums.StatusOperacaoViagem;
import enums.StatusViagem;
import modelo.Conta;
import modelo.PontoViagem;
import modelo.Viagem;
import servico.PontoViagemService;
import servico.ViagemService;
import util.JsfUtil;

@ManagedBean
@ViewScoped
public class ViagemMotoristaMb {

	@EJB
	private ViagemService viagemService;
	@EJB
	private PontoViagemService pontoViagemService;

	private Conta motorista;
	private Viagem viagem;
	private PontoViagem pontoViagem;

	@PostConstruct
	private void inicializar() {
		carregarViagem();
	}

	public void motoristaSelecionado() {
		carregarViagem();
	}

	private void carregarViagem() {
		this.viagem = null;
		this.pontoViagem = null;
		try {
			if (motorista != null) {
				this.viagem = viagemService.obterViagemEmFocoDoMotorista(motorista);
				if (viagem != null) {
					if (!viagem.getStatus().equals(StatusViagem.PENDENTE)) {
						carregarPontoViagem();
					}
				}
			}
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}

	private void carregarPontoViagem() {
		//this.pontoViagem = viagem.getPontos().iterator().next();
	}

	public void selecionarPonto(PontoViagem pontoViagem) {
		System.out.println("Selecionou ponto viagem: " + pontoViagem.getId());
		this.pontoViagem = pontoViagem;
	}

	public void iniciarViagem() {
		try {
			viagemService.iniciarViagem(viagem);
			// TODO Remover linha abaixo e descomentar a seguinte quando estiver implementado autenticação.
			viagem = viagemService.recuperar(viagem.getId());
			//carregarViagem();
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}

	public void registrarPrevisaoChegada() {
		try {
			// TODO
			//pontoViagem.setDataHoraPrevistaChegada(new Date());
			viagemService.registrarPrevisaoChegada(pontoViagem);
			pontoViagem = pontoViagemService.recuperar(pontoViagem.getId());
			//carregarViagem();
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}

	public void registrarChegada() {
		try {
			// TODO
			pontoViagem.setDataHoraChegada(new Date());
			viagemService.registrarChegada(pontoViagem);
			pontoViagem = pontoViagemService.recuperar(pontoViagem.getId());
			//carregarViagem();
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}

	}

	public void registrarSaida() {
		try {
			// TODO
			pontoViagem.setDataHoraSaida(new Date());
			viagemService.registrarSaida(pontoViagem);
			pontoViagem = pontoViagemService.recuperar(pontoViagem.getId());
			//carregarViagem();
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}

	public void registrarTerminoOperacoes() {
		try {
			// TODO
			viagemService.registrarTerminoOperacoes(pontoViagem);
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}

	public void finalizarViagem() {
		try {
			// TODO
			viagemService.finalizarViagem(viagem);
			carregarViagem();
			//carregarViagem();
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}

	public Viagem getViagem() {
		return viagem;
	}

	public PontoViagem getPontoViagem() {
		return pontoViagem;
	}

	public void setPontoViagem(PontoViagem pontoViagem) {
		System.out.println("setPontoViagem: " + pontoViagem.getId());
		this.pontoViagem = pontoViagem;
	}

	public StatusViagem getStatusViagemPendente() {
		return StatusViagem.PENDENTE;
	}

	public StatusOperacaoViagem getStatusOperacaoViagemRealizada() {
		return StatusOperacaoViagem.REALIZADA;
	}

	public StatusOperacaoViagem getStatusOperacaoViagemAbortada() {
		return StatusOperacaoViagem.ABORTADA;
	}

	public Conta getMotorista() {
		return motorista;
	}

	public void setMotorista(Conta motorista) {
		this.motorista = motorista;
	}
}
