package servico;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.EntregaDao;
import dao.OperacaoViagemDao;
import enums.StatusEntrega;
import enums.StatusOperacaoViagem;
import enums.TipoOperacaoViagem;
import modelo.Entrega;
import modelo.EtapaEntrega;
import modelo.Evento;
import modelo.EventoTerminoOperacao;
import modelo.OperacaoViagem;

@Stateless
public class ProcessadorEventoTerminoOperacao extends ProcessadorEvento {

	@EJB
	private OperacaoViagemDao operacaoViagemDao;
	
	@EJB
	private EntregaDao entregaDao;
	
	@Override
	public void eventoCriado(Evento evento) throws Exception {
		if (!(evento instanceof EventoTerminoOperacao)) {
			throw new Exception("Classe de evento não prevista");
		}
		EventoTerminoOperacao eventoTerminoOperacao = (EventoTerminoOperacao)evento;
		OperacaoViagem operacao = eventoTerminoOperacao.getOperacao();
		switch (eventoTerminoOperacao.getTipoTermino()) {
			case ABORTADA:
				operacao.setStatus(StatusOperacaoViagem.ABORTADA);
				break;
			case REALIZADA:
				operacao.setStatus(StatusOperacaoViagem.REALIZADA);
				break;
		}
		operacao.setDataHoraStatus(eventoTerminoOperacao.getDataHoraTermino());		
		operacaoViagemDao.salvar(operacao);
		
		EtapaEntrega etapaEntrega = operacao.getEtapaEntrega();
		Entrega entrega = etapaEntrega.getEntrega();
		
		// Se a etapa termina no destino da entrega, finaliza a entrega.
		if (operacao.getTipo().equals(TipoOperacaoViagem.ENTREGA) 
				&& etapaEntrega.getDestino().equals(entrega.getDestino())) {
			if (operacao.getStatus().equals(StatusOperacaoViagem.REALIZADA)) {
				entrega.setStatus(StatusEntrega.REALIZADA);
			} else {
				entrega.setStatus(StatusEntrega.ABORTADA);
			}
			entrega.setDataHoraStatus(eventoTerminoOperacao.getDataHoraTermino());
			entregaDao.salvar(entrega);
		}
	}
	
	
}