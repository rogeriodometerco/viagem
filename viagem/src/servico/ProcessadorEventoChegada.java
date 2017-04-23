package servico;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.EtapaEntregaDao;
import dao.PontoViagemDao;
import enums.StatusEtapaEntrega;
import enums.StatusPontoViagem;
import modelo.EtapaEntrega;
import modelo.Evento;
import modelo.EventoChegada;
import modelo.OperacaoViagem;
import modelo.PontoViagem;

@Stateless
public class ProcessadorEventoChegada extends ProcessadorEvento {

	@EJB
	private PontoViagemDao pontoViagemDao;
	@EJB
	private EtapaEntregaDao etapaEntregaDao;
	
	@Override
	public void eventoCriado(Evento evento) throws Exception {
		if (!(evento instanceof EventoChegada)) {
			throw new Exception("Classe de evento não prevista");
		}
		EventoChegada eventoChegada = (EventoChegada)evento;
		PontoViagem pontoViagem = eventoChegada.getPontoViagem();
		pontoViagem.setDataHoraChegada(eventoChegada.getDataHoraChegada());
		pontoViagem.setStatus(StatusPontoViagem.NO_LOCAL);
		pontoViagemDao.salvar(pontoViagem);
		
		// Se há alguma etapa que finaliza neste local, encerra a etapa.
		EtapaEntrega etapaEntrega = null;
		for (OperacaoViagem operacao: pontoViagem.getOperacoes()) {
			etapaEntrega = operacao.getEtapaEntrega();
			if (etapaEntrega.getDestino().equals(pontoViagem.getEstabelecimento())) {
				etapaEntrega.setStatus(StatusEtapaEntrega.CONCLUIDA);
				etapaEntrega.setDataHoraStatus(eventoChegada.getDataHoraChegada());
				etapaEntregaDao.salvar(etapaEntrega);
			}
		}
	}
	
	
	
}