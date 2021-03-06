package resumo;

import java.util.Date;
import java.util.List;

import enums.TipoOperacaoViagem;
import modelo.Conta;
import modelo.DemandaTransporte;
import modelo.Estabelecimento;
import modelo.OperacaoViagem;
import util.DataUtil;

public class Massa<T> {
	private final String TOTAL = "TOTAL";
	private final String CHEGARAM = "CHEGARAM";
	private final String NO_LOCAL = "NO_LOCAL";
	private final String A_CAMINHO = "A_CAMINHO";
	private final String DESACORDO = "DESACORDO";
	private final String ATRASO = "ATRASO";
	
	private List<OperacaoViagem> operacoes;
	
	private Resumo<Conta> resumoPorTransportador;
	private Resumo<DemandaTransporte> resumoPorDemanda;
	private Resumo<Estabelecimento> resumoPorEstabelecimento;
	private Resumo<String> resumoTotal;

	public Massa(List<OperacaoViagem> operacoes) {
		this.operacoes = operacoes;
		resumir();
	}
	
	private void resumir() {

		resumoPorTransportador = new Resumo<Conta>();
		resumoPorDemanda = new Resumo<DemandaTransporte>();
		resumoPorEstabelecimento = new Resumo<Estabelecimento>();
		resumoTotal = new Resumo<String>();

		int i = 0;
		for (OperacaoViagem operacao: operacoes) {
			Conta transportador = operacao.getPontoViagem().getViagem().getTransportador();
			DemandaTransporte demanda = operacao.getEtapaEntrega().getEntrega().getDemanda();
			Estabelecimento estabelecimento = null;
			if (operacao.getTipo().equals(TipoOperacaoViagem.COLETA)) {
				estabelecimento = operacao.getEtapaEntrega().getOrigem();
			} else {
				estabelecimento = operacao.getEtapaEntrega().getDestino();
			}

			if (operacao.getPontoViagem().getDataHoraChegada() != null) {
				resumoTotal.acumular(TOTAL, TOTAL, 1, i);
				resumoPorTransportador.acumular(transportador, CHEGARAM, 1, i);
				resumoPorDemanda.acumular(demanda, CHEGARAM, 1, i);
				resumoPorEstabelecimento.acumular(estabelecimento, CHEGARAM, 1, i);
				
				if (operacao.getPontoViagem().getDataHoraSaida() == null) {
					resumoTotal.acumular(TOTAL, NO_LOCAL, 1, i);
					resumoPorTransportador.acumular(transportador, NO_LOCAL, 1, i);
					resumoPorDemanda.acumular(demanda, NO_LOCAL, 1, i);
					resumoPorEstabelecimento.acumular(estabelecimento, NO_LOCAL, 1, i);
				}
				
			} else {
				resumoTotal.acumular(TOTAL, A_CAMINHO, 1, i);
				resumoPorTransportador.acumular(transportador, A_CAMINHO, 1, i);
				resumoPorDemanda.acumular(demanda, A_CAMINHO, 1, i);
				resumoPorEstabelecimento.acumular(estabelecimento, A_CAMINHO, 1, i);

				if (operacao.getPontoViagem().getDataHoraPrevistaChegada().before(new Date())) {
					resumoTotal.acumular(TOTAL, ATRASO, 1, i);
					resumoPorTransportador.acumular(transportador, ATRASO, 1, i);
					resumoPorDemanda.acumular(demanda, ATRASO, 1, i);
					resumoPorEstabelecimento.acumular(estabelecimento, ATRASO, 1, i);
				}
				if (operacao.getPontoViagem().getDataChegadaAcordada().before(DataUtil.extrairDataSemHora(new Date()))) {
					resumoTotal.acumular(TOTAL, DESACORDO, 1, i);
					resumoPorTransportador.acumular(transportador, DESACORDO, 1, i);
					resumoPorDemanda.acumular(demanda, DESACORDO, 1, i);
					resumoPorEstabelecimento.acumular(estabelecimento, DESACORDO, 1, i);
				}
			}
			i++;
		}
	}

}
