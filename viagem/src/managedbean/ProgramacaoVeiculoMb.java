package managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.DragDropEvent;

import exception.AppException;
import modelo.Conta;
import modelo.DemandaTransporte;
import modelo.Entrega;
import modelo.Estabelecimento;
import modelo.EtapaEntrega;
import modelo.PontoViagem;
import modelo.Veiculo;
import modelo.Viagem;
import servico.DemandaTransporteService;
import servico.VeiculoService;
import servico.ViagemService;
import util.JsfUtil;

@ManagedBean
@ViewScoped
public class ProgramacaoVeiculoMb implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private DemandaTransporteService demandaTransporteService;
	@EJB
	private VeiculoService veiculoService;
	@EJB
	private ViagemService viagemService;
	private List<DemandaTransporte> demandasDisponiveis;
	private List<DemandaTransporte> demandasSelecionadas;
	private Viagem viagemEdicao;
	private String identificacaoVeiculo;
	private List<EtapaEntrega> etapas;
	private List<PontoViagem> pontos;
	private static String MODO_SELECAO = "MODO_SELECAO";
	private static String MODO_CONCLUSAO = "MODO_CONCLUSAO";
	private String modoView;

	@PostConstruct
	private void inicializar() {
		prepararNovo();
	}

	private void prepararNovo() {
		this.viagemEdicao = new Viagem();
		this.viagemEdicao.setEtapas(new HashSet<EtapaEntrega>());
		this.viagemEdicao.setPontos(new HashSet<PontoViagem>());
		this.demandasSelecionadas = new ArrayList<DemandaTransporte>();
		this.etapas = new ArrayList<EtapaEntrega>();
		this.pontos = new ArrayList<PontoViagem>();
		iniciarSelecao();
	}
	
	public void irParaSelecao() {
		this.modoView = MODO_SELECAO;
	}
	
	public void iniciarSelecao() {
		listar();
		this.modoView = MODO_SELECAO;
	}
	
	public void iniciarConclusao() {
		this.modoView = MODO_CONCLUSAO;
	}
	
	/**
	* Trata o evento de quando o usu�rio informa a identifica��o do ve�culo.
	*/
	public void veiculoInformado(String identificacao) {
		try {
			Veiculo veiculo = null;
			if (identificacao != null) {
				veiculo = veiculoService.recuperarPelaIdentificacao(identificacao); 
			}
			viagemEdicao.setVeiculo(veiculo);
		} catch (AppException e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}
	
	/**
	* Trata o evento de quando o usu�rio arrasta a demanda para o ve�culo atender.
	*/
	public void demandaSelecionada(DragDropEvent ddEvent) {
        DemandaTransporte demanda = ((DemandaTransporte)ddEvent.getData());
        demandasSelecionadas.add(demanda);
        demandasDisponiveis.remove(demanda);
		adicionarEntrega(demanda);

    }

	/**
	* Trata o evento de quando o usu�rio retira a demanda que o ve�culo iria atender.
	*/
	public void demandaDescartada(DemandaTransporte demanda) {
		demandasSelecionadas.remove(demanda);
		removerEntrega(demanda);
		if (viagemEdicao.getEtapas().size() == 0) {
			iniciarSelecao();
		}
		
	}
	
	public void selecionarDemanda(DemandaTransporte demanda) {
		demandasSelecionadas.add(demanda);
        demandasDisponiveis.remove(demanda);
		adicionarEntrega(demanda);
	}
	
	private void adicionarEntrega(DemandaTransporte demanda) {
		// Cria entrega
		Entrega entrega = new Entrega();
		entrega.setDemanda(demanda);
		entrega.setOrigem(demanda.getOrigem());
		entrega.setDestino(demanda.getDestino());
		entrega.setProduto(demanda.getProduto());
		// n�o inicializa quantidade, o usu�rio que informa.
		entrega.setUnidadeQuantidade(demanda.getUnidadeQuantidade());

		// Cria etapa.
		EtapaEntrega etapa = new EtapaEntrega();
		etapa.setEntrega(entrega);
		etapa.setViagem(viagemEdicao);
		etapa.setOrigem(entrega.getOrigem());
		etapa.setDestino(entrega.getDestino());
		etapa.setViagem(viagemEdicao);

		// Adiciona etapa na entrega;
		entrega.setEtapas(new HashSet<EtapaEntrega>());
		entrega.getEtapas().add(etapa);
		
		// Adiciona na lista de entregas que ser�o realizadas pelo ve�culo.
		//entregas.add(entrega);
		
		viagemEdicao.getEtapas().add(etapa);
		etapas.add(etapa);

		garantirPassagemPor(etapa);		
	}
	
	/**
	* Faz com que a viagem passe pelos pontos de origem e de destino da etapa.
	*/
	private void garantirPassagemPor(EtapaEntrega etapa) {
		boolean origem = false, destino = false;
		for (PontoViagem pontoViagem: viagemEdicao.getPontos()) {
			if (pontoViagem.getEstabelecimento().equals(etapa.getOrigem())) {
				origem = true;
			} 
			if (pontoViagem.getEstabelecimento().equals(etapa.getDestino())) {
				destino = true;
			}
			if (origem && destino) {
				break;
			}
		}
		if (!origem) {
			PontoViagem pontoViagem = new PontoViagem();
			pontoViagem.setViagem(viagemEdicao);
			pontoViagem.setEstabelecimento(etapa.getOrigem());
			viagemEdicao.getPontos().add(pontoViagem);
			pontos.add(pontoViagem);
		}
		if (!destino) {
			PontoViagem pontoViagem = new PontoViagem();
			pontoViagem.setViagem(viagemEdicao);
			pontoViagem.setEstabelecimento(etapa.getDestino());
			viagemEdicao.getPontos().add(pontoViagem);
			pontos.add(pontoViagem);
		}
	}
	
	private void removerEntrega(DemandaTransporte demanda) {
		EtapaEntrega etapaRemovida = null;
		for (EtapaEntrega etapa: viagemEdicao.getEtapas()) {
			if (etapa.getEntrega().getDemanda().equals(demanda)) {
				viagemEdicao.getEtapas().remove(etapa);
				etapas.remove(etapa);
				etapaRemovida = etapa;
				break;
			}
		}
		if (etapaRemovida != null) {
			if (!possuiEtapaEm(etapaRemovida.getOrigem())) {
				removerPontoViagem(etapaRemovida.getOrigem());
			}
			if (!possuiEtapaEm(etapaRemovida.getDestino())) {
				removerPontoViagem(etapaRemovida.getDestino());
			}
		}
	}
	
	private void removerPontoViagem(Estabelecimento estabelecimento) {
		List<PontoViagem> objetosARemover = new ArrayList<PontoViagem>();
		
		for (PontoViagem pontoViagem: viagemEdicao.getPontos()) {
			if (pontoViagem.getEstabelecimento().equals(estabelecimento)) {
				objetosARemover.add(pontoViagem);
			} 
		}
		/*
		for (PontoViagem objetoARemover: objetosARemover) {
			viagemEdicao.getPontos().remove(objetoARemover);
		}
		*/
		viagemEdicao.getPontos().removeAll(objetosARemover);
		pontos.removeAll(objetosARemover);
	}

	/**
	* Verifica se a viagem possui alguma etapa que passa por um determinado ponto.
	*/
	private boolean possuiEtapaEm(Estabelecimento estabelecimento) {
		for (EtapaEntrega etapa: viagemEdicao.getEtapas()) {
			if (etapa.getOrigem().equals(estabelecimento) 
					|| etapa.getDestino().equals(estabelecimento)) {
				return true;
			}
		}
		return false;
	}
	
	public void salvar() {
		try {
			viagemEdicao.getPontos().clear();
			System.out.println("salvar() ");
			int i = 1;
			for (PontoViagem ponto : pontos) {
				ponto.setOrdem(i);
				i++;
				viagemEdicao.getPontos().add(ponto);
			}
			for (PontoViagem ponto : viagemEdicao.getPontos()) {
				System.out.println(ponto.getOrdem() + " - " + ponto.getEstabelecimento().getNome() + " - " + ponto.getDataChegadaAcordada());
			}
			viagemService.criar(viagemEdicao);
			JsfUtil.addMsgSucesso("Informa��es salvas com sucesso");
			prepararNovo();
		} catch (Exception e) {
			JsfUtil.addMsgErro("Erro ao salvar informa��es: " + e.getMessage());
		}
	}

	public void listar() {
		try {
			System.out.println("Listar()");
			this.demandasDisponiveis = demandaTransporteService.listar();
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}

	public List<DemandaTransporte> getDemandasDisponiveis() {
		return demandasDisponiveis;
	}

	public Viagem getViagemEdicao() {
		return viagemEdicao;
	}

	public void setViagemEdicao(Viagem viagem) {
		this.viagemEdicao = viagem;
	}

	public Boolean estaEmModoSelecao() {
		return modoView.equals(MODO_SELECAO);
	}
	public Boolean estaEmModoConclusao() {
		return modoView.equals(MODO_CONCLUSAO);
	}
	
	/*
	public List<PontoViagem> getPontos() {
		return new ArrayList<PontoViagem>(viagemEdicao.getPontos());
	}
	*/
	public List<EtapaEntrega> getEtapas() {
		return etapas;
	}
	
	public List<PontoViagem> getPontos() {
		return pontos;
	}

	public String getIdentificacaoVeiculo() {
		return identificacaoVeiculo;
	}

	public void setIdentificacaoVeiculo(String identificacaoVeiculo) {
		this.identificacaoVeiculo = identificacaoVeiculo;
	}

	public void identificacaoVeiculoInformada() {
		
	}
	/*
	public void setPontos(List<PontoViagem> pontos) {
		System.out.println("setPontos: ");
		for (PontoViagem ponto : pontos) {
			System.out.println(ponto.getEstabelecimento().getNome());
		}
		this.pontos = pontos;
	}
	*/
}
