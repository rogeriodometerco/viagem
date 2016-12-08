package servico;

import dao.OperacaoEtapaDao;
import enums.StatusOperacaoEtapa;
import enums.TipoTerminoOperacao;
import modelo.Evento;
import modelo.EventoTerminoOperacao;
import modelo.OperacaoEtapa;
import util.Ejb;

public class ProcessadorEventoTerminoOperacao extends ProcessadorEvento {

	@Override
	public void eventoCriado(Evento evento) throws Exception {
		if (!(evento instanceof EventoTerminoOperacao)) {
			throw new Exception("Classe de evento não prevista");
		}
		EventoTerminoOperacao eventoTerminoOperacao = (EventoTerminoOperacao)evento;
		OperacaoEtapaDao operacaoEtapaDao = Ejb.lookup(OperacaoEtapaDao.class);
		OperacaoEtapa operacao = eventoTerminoOperacao.getOperacaoEtapa();
		switch (eventoTerminoOperacao.getTipoTermino()) {
			case ABORTADA:
				operacao.setStatus(StatusOperacaoEtapa.ABORTADA);
				break;
			case REALIZADA:
				operacao.setStatus(StatusOperacaoEtapa.REALIZADA);
				break;
		}
		operacao.setDataHoraStatus(eventoTerminoOperacao.getDataHoraTermino());		
		operacao.setStatus(StatusOperacaoEtapa.REALIZADA);
		operacao.setDataHoraStatus(eventoTerminoOperacao.getDataHoraTermino());
		operacaoEtapaDao.salvar(operacao);
	}
	
	
}