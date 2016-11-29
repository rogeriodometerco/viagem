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
import modelo.Conta;
import modelo.DemandaTransporte;
import modelo.TransportadorDemandaAutorizado;
import servico.DemandaTransporteService;
import util.JsfUtil;

@ManagedBean
@ViewScoped
public class DemandaTransporteMb implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private DemandaTransporteService demandaTransporteService;
	private List<DemandaTransporte> lista;
	private DemandaTransporte demandaTransporteEdicao;
	private DemandaTransporte demandaTransporteSelecionado;
	private List<Conta> transportadoresEdicao;
	private EstadoView estadoView;

	@PostConstruct
	private void inicializar() {
		listar();
	}

	public void prepararNovo() {
		this.demandaTransporteEdicao = new DemandaTransporte();
		this.demandaTransporteEdicao.setTransportadores(new HashSet<TransportadorDemandaAutorizado>());
		this.transportadoresEdicao = new ArrayList<Conta>();
		this.estadoView = EstadoView.INCLUSAO;
	}

	public void prepararEdicao() {
		try {
			this.demandaTransporteEdicao = demandaTransporteService.recuperar(demandaTransporteSelecionado.getId());

			this.transportadoresEdicao = new ArrayList<Conta>();
			if (demandaTransporteEdicao.getTransportadores() == null) {
				this.demandaTransporteEdicao.setTransportadores(new HashSet<TransportadorDemandaAutorizado>());
			}
			for (TransportadorDemandaAutorizado transportador: demandaTransporteEdicao.getTransportadores()) {
				transportadoresEdicao.add(transportador.getTransportador());
			}

			this.estadoView = EstadoView.ALTERACAO;
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}

	public void salvar() {
		try {
			sincronizarDadosEdicaoParaSalvar();
			demandaTransporteService.salvar(demandaTransporteEdicao);
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

	private void sincronizarDadosEdicaoParaSalvar() {
		if (transportadoresEdicao == null) {
			transportadoresEdicao = new ArrayList<Conta>();
		}

		// Remove transportadores que não estão mais na lista.
		List<TransportadorDemandaAutorizado> transportadoresARemover = new ArrayList<TransportadorDemandaAutorizado>();
		for (TransportadorDemandaAutorizado transportador: demandaTransporteEdicao.getTransportadores()) {
			if (!transportadoresEdicao.contains(transportador.getTransportador())) {
				transportadoresARemover.add(transportador);
			}
		}
		demandaTransporteEdicao.getTransportadores().removeAll(transportadoresARemover);

		// Adiciona os novos transportadores.
		for (Conta transportador: transportadoresEdicao) {
			boolean contem = false;
			for (TransportadorDemandaAutorizado tda: demandaTransporteEdicao.getTransportadores()) {
				if (transportador.equals(tda.getTransportador())) {
					contem = true;
					break;
				}
			}
			if (!contem) {
				TransportadorDemandaAutorizado tda = new TransportadorDemandaAutorizado();
				tda.setDemanda(demandaTransporteEdicao);
				tda.setTransportador(transportador);
				demandaTransporteEdicao.getTransportadores().add(tda);
			}
		}
	}	

	public void listar() {
		try {
			this.lista = demandaTransporteService.listar();
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

	public List<DemandaTransporte> getLista() {
		return lista;
	}

	public DemandaTransporte getDemandaTransporteEdicao() {
		return demandaTransporteEdicao;
	}

	public void setDemandaTransporteEdicao(DemandaTransporte demandaTransporte) {
		this.demandaTransporteEdicao = demandaTransporte;
	}

	public DemandaTransporte getDemandaTransporteSelecionado() {
		return demandaTransporteSelecionado;
	}

	public void setDemandaTransporteSelecionado(DemandaTransporte demandaTransporteSelecionado) {
		this.demandaTransporteSelecionado = demandaTransporteSelecionado;
	}

	public List<Conta> getTransportadoresEdicao() {
		return transportadoresEdicao;
	}

	public void setTransportadoresEdicao(List<Conta> transportadoresEdicao) {
		this.transportadoresEdicao = transportadoresEdicao;
	}

}
