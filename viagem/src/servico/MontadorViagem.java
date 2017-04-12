package servico;

import java.util.Date;
import java.util.HashSet;

import enums.StatusOperacaoViagem;
import enums.TipoOperacaoViagem;
import modelo.Conta;
import modelo.DemandaTransporte;
import modelo.Entrega;
import modelo.Estabelecimento;
import modelo.EtapaEntrega;
import modelo.OperacaoViagem;
import modelo.PontoViagem;
import modelo.Veiculo;
import modelo.Viagem;

public class MontadorViagem {
	
	private Viagem viagem;
	
	public MontadorViagem() {
		viagem = new Viagem();
		viagem.setPontos(new HashSet<PontoViagem>());
		viagem.setEtapas(new HashSet<EtapaEntrega>());
	}
	
	public Viagem getViagem() {
		return viagem;
	}
	
	public void setTransportador(Conta transportador) {
		this.viagem.setTransportador(transportador);
	}
	
	public void setMotorista(Conta motorista) {
		this.viagem.setMotorista(motorista);
	}

	public void setVeiculo(Veiculo veiculo) {
		this.viagem.setVeiculo(veiculo);
	}
	
	public void adicionarDemanda(DemandaTransporte demanda, Integer quantidade) {
		Entrega entrega = new Entrega();
		entrega.setDemanda(demanda);
		entrega.setOrigem(demanda.getOrigem());
		entrega.setDestino(demanda.getDestino());
		entrega.setProduto(demanda.getProduto());
		entrega.setUnidadeQuantidade(demanda.getUnidadeQuantidade());
		entrega.setQuantidade(quantidade);

		EtapaEntrega etapa = new EtapaEntrega();
		etapa.setEntrega(entrega);
		etapa.setViagem(viagem);
		etapa.setOrigem(entrega.getOrigem());
		etapa.setDestino(entrega.getDestino());

		entrega.setEtapas(new HashSet<EtapaEntrega>());
		entrega.getEtapas().add(etapa);
		
		viagem.getEtapas().add(etapa);
	}
	
	/*
	 * Ativar este método somente após todas as demandas tiverem sido adicionadas.
	 */
	public void adicionarPontoECriarOperacoes(Estabelecimento estabelecimento, Date dataChegadaAcordada, int ordem) {
		PontoViagem pontoViagem = new PontoViagem();
		pontoViagem.setViagem(viagem);
		pontoViagem.setEstabelecimento(estabelecimento);
		pontoViagem.setDataChegadaAcordada(dataChegadaAcordada);
		pontoViagem.setOrdem(ordem);
		
		criarOperacoes(pontoViagem);

		viagem.getPontos().add(pontoViagem);
	}
	
	private void criarOperacoes(PontoViagem pontoViagem) {
		pontoViagem.setOperacoes(new HashSet<OperacaoViagem>());
		TipoOperacaoViagem tipoOperacaoDefinida = null;
		for (EtapaEntrega etapa: viagem.getEtapas()) {
			if (etapa.getOrigem().getId().equals(pontoViagem.getEstabelecimento().getId())
					|| etapa.getDestino().getId().equals(pontoViagem.getEstabelecimento().getId())) {
				
				if (etapa.getOrigem().getId().equals(pontoViagem.getEstabelecimento().getId())) {
					tipoOperacaoDefinida = TipoOperacaoViagem.COLETA;
				} else {
					tipoOperacaoDefinida = TipoOperacaoViagem.ENTREGA;
				}
				OperacaoViagem operacaoViagem = new OperacaoViagem();
				operacaoViagem.setPontoViagem(pontoViagem);
				operacaoViagem.setEtapaEntrega(etapa);
				operacaoViagem.setTipo(tipoOperacaoDefinida);
				
				pontoViagem.getOperacoes().add(operacaoViagem);
				
				// TODO: Esta lógica não está preparada para demandas origem e destino iguais, porque não é para existir mesmo.
			}
		}
	}
}
