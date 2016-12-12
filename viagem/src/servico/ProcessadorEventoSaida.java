package servico;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.EtapaEntregaDao;
import dao.PontoViagemDao;
import enums.StatusEtapaEntrega;
import enums.StatusPontoViagem;
import modelo.EtapaEntrega;
import modelo.Evento;
import modelo.EventoSaida;
import modelo.OperacaoViagem;
import modelo.PontoViagem;

@Stateless
public class ProcessadorEventoSaida extends ProcessadorEvento {

	@EJB
	private PontoViagemDao pontoViagemDao;
	@EJB
	private EtapaEntregaDao etapaEntregaDao;
	
	@Override
	public void eventoCriado(Evento evento) throws Exception {
		if (!(evento instanceof EventoSaida)) {
			throw new Exception("Classe de evento não prevista");
		}
		EventoSaida eventoSaida = (EventoSaida)evento;
		PontoViagem pontoViagem = eventoSaida.getPontoViagem();
		pontoViagem.setDataHoraSaida(eventoSaida.getDataHoraSaida());
		pontoViagem.setStatus(StatusPontoViagem.NO_LOCAL);
		pontoViagemDao.salvar(pontoViagem);
		
		// Se há alguma etapa que inicia neste local, inicia a etapa.
		EtapaEntrega etapaEntrega = null;
		for (OperacaoViagem operacao: pontoViagem.getOperacoes()) {
			etapaEntrega = operacao.getEtapaEntrega();
			if (etapaEntrega.getOrigem().equals(pontoViagem.getEstabelecimento())) {
				etapaEntrega.setStatus(StatusEtapaEntrega.INICIADA);
				etapaEntrega.setDataHoraStatus(eventoSaida.getDataHoraSaida());
				etapaEntregaDao.salvar(etapaEntrega);
			}
		}
		
	}
	
	
	
}