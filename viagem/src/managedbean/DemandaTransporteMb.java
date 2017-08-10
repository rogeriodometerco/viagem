package managedbean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dto.DemandaTomadorDto;
import dto.Filtro;
import enums.EstadoView;
import modelo.Conta;
import modelo.DemandaTransporte;
import modelo.Entrega;
import modelo.Estabelecimento;
import modelo.Produto;
import modelo.TransportadorDemandaAutorizado;
import servico.DemandaTransporteService;
import servico.EntregaService;
import servico.SessaoService;
import util.JsfUtil;

@ManagedBean
@ViewScoped
public class DemandaTransporteMb implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private DemandaTransporteService demandaTransporteService;
	@EJB
	private SessaoService sessaoService;
	@EJB
	private EntregaService entregaService;
	private List<DemandaTomadorDto> lista;
	private DemandaTransporte demandaTransporteEdicao;
	private DemandaTomadorDto demandaTomadorDtoSelecionada;
	private Entrega entregaSelecionada;
	private EstadoView estadoView;
	private Conta transportadorInformado;
	private Integer tabIndex;

	private Long filtroDemandaId;
	private Estabelecimento filtroOrigem;
	private Estabelecimento filtroDestino;
	private Produto filtroProduto;
	private Boolean ehParaExibirExtratoEntregas;

	private List<Entrega> extratoEntregas;
	
	@PostConstruct
	private void inicializar() {
		//sessaoService = Ejb.lookup(SessaoService.class);
		listar();
		this.ehParaExibirExtratoEntregas = false;
	}

	public void prepararNovo() {
		this.demandaTransporteEdicao = new DemandaTransporte();
		this.demandaTransporteEdicao.setTransportadores(new HashSet<TransportadorDemandaAutorizado>());
		this.estadoView = EstadoView.INCLUSAO;
		this.tabIndex = 0;
	}

	public void prepararEdicao() {
		try {
			this.demandaTransporteEdicao = demandaTransporteService.recuperar(
					demandaTomadorDtoSelecionada.getDemandaTransporte().getId());

			if (demandaTransporteEdicao.getTransportadores() == null) {
				this.demandaTransporteEdicao.setTransportadores(new HashSet<TransportadorDemandaAutorizado>());
			}
			this.estadoView = EstadoView.ALTERACAO;
			this.tabIndex = 0;
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}

	public void cancelarEdicao() {
		this.demandaTransporteEdicao = null;
		this.demandaTomadorDtoSelecionada = null;
		this.estadoView = EstadoView.LISTAGEM;
	}

	public void salvar() {
		try {
			if (estaEmModoCriacao()) {
				demandaTransporteEdicao.setTomador(sessaoService.getConta());
				demandaTransporteService.criar(demandaTransporteEdicao);
			} else {
				demandaTransporteService.alterar(demandaTransporteEdicao);
			}
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
			this.estadoView = EstadoView.LISTAGEM;
			Filtro filtro = new Filtro();
			if (filtroDemandaId != null && filtroDemandaId > 0) {
				filtro.igual("demandaId", filtroDemandaId);
			}
			if (filtroOrigem != null) {
				filtro.igual("origemId", filtroOrigem.getId());
			}
			if (filtroDestino != null) {
				filtro.igual("destinoId", filtroDestino.getId());
			}
			if (filtroProduto != null) {
				filtro.igual("produtoId", filtroProduto.getId());
			}
			filtro.orderDesc("demandaId");
			this.lista = demandaTransporteService.listarDemandasTomador(sessaoService.getConta(), filtro);
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

	public void listarEntregasDaDemanda()  {
		try {
			this.ehParaExibirExtratoEntregas = true;
			
			this.extratoEntregas = entregaService.listarPorDemanda(demandaTomadorDtoSelecionada.getDemandaTransporte());
		} catch (Exception e) {
			JsfUtil.addMsgErro("Erro listar as cargas do lote: " + e.getMessage());
		}
	}
	
	public void exibirExtratoEntregas() {
		try {
			this.ehParaExibirExtratoEntregas = true;
			FacesContext.getCurrentInstance()
			.getExternalContext().getSessionMap().put("demandaId", demandaTomadorDtoSelecionada.getDemandaTransporte().getId());

			this.extratoEntregas = entregaService.listarPorDemanda(demandaTomadorDtoSelecionada.getDemandaTransporte());
		} catch (Exception e) {
			JsfUtil.addMsgErro("Erro ao listar cargas do lote: " + e.getMessage());
		}
	}
	
	public void ocultarExtratoEntregas() {
		this.ehParaExibirExtratoEntregas = false;
	}
	
	public Boolean ehParaExibirAreaListagem() {
		return estadoView.equals(EstadoView.LISTAGEM) && !ehParaExibirExtratoEntregas;
	}
	
	public Boolean ehParaExibirAreaEdicao() {
		return (estadoView.equals(EstadoView.INCLUSAO) || estadoView.equals(EstadoView.ALTERACAO))
				&& !ehParaExibirExtratoEntregas;
	}
	
	public Boolean ehParaExibirAreaExtratoEntregas() {
		return ehParaExibirExtratoEntregas;
	}
	
	public List<DemandaTomadorDto> getLista() {
		return lista;
	}

	public DemandaTransporte getDemandaTransporteEdicao() {
		return demandaTransporteEdicao;
	}

	public void setDemandaTransporteEdicao(DemandaTransporte demandaTransporte) {
		this.demandaTransporteEdicao = demandaTransporte;
	}

	public DemandaTomadorDto getDemandaTomadorDtoSelecionada() {
		return demandaTomadorDtoSelecionada;
	}

	public void setDemandaTomadorDtoSelecionada(DemandaTomadorDto demandaTomadorDtoSelecionada) {
		this.demandaTomadorDtoSelecionada = demandaTomadorDtoSelecionada;
	}

	public Estabelecimento getFiltroOrigem() {
		return filtroOrigem;
	}

	public void setFiltroOrigem(Estabelecimento filtroOrigem) {
		this.filtroOrigem = filtroOrigem;
	}

	public Estabelecimento getFiltroDestino() {
		return filtroDestino;
	}

	public void setFiltroDestino(Estabelecimento filtroDestino) {
		this.filtroDestino = filtroDestino;
	}

	public Produto getFiltroProduto() {
		return filtroProduto;
	}

	public void setFiltroProduto(Produto filtroProduto) {
		this.filtroProduto = filtroProduto;
	}

	public Long getFiltroDemandaId() {
		return filtroDemandaId;
	}

	public void setFiltroDemandaId(Long filtroDemandaId) {
		this.filtroDemandaId = filtroDemandaId;
	}

	public Conta getTransportadorInformado() {
		return transportadorInformado;
	}

	public void setTransportadorInformado(Conta transportadorInformado) {
		this.transportadorInformado = transportadorInformado;
	}

	public void transportadorInformado() {
		adicionarTransportador(transportadorInformado);
		this.transportadorInformado = null;
	}
	
	private void adicionarTransportador(Conta transportador) {
		TransportadorDemandaAutorizado novo = new TransportadorDemandaAutorizado();
		novo.setTransportador(transportadorInformado);
		novo.setDemanda(demandaTransporteEdicao);
		demandaTransporteEdicao.getTransportadores().add(novo);
	}

	public Integer getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(Integer tabIndex) {
		this.tabIndex = tabIndex;
	}

	public Boolean getEhParaExibirExtratoEntregas() {
		return ehParaExibirExtratoEntregas;
	}

	public List<Entrega> getExtratoEntregas() {
		return extratoEntregas;
	}
}
