package servico;

import dao.PontoViagemDao;
import enums.StatusPontoViagem;
import modelo.Evento;
import modelo.EventoSaida;
import modelo.PontoViagem;
import util.Ejb;

public class ProcessadorEventoSaida extends ProcessadorEvento {

	@Override
	public void eventoCriado(Evento evento) throws Exception {
		if (!(evento instanceof EventoSaida)) {
			throw new Exception("Classe de evento não prevista");
		}
		EventoSaida eventoSaida = (EventoSaida)evento;
		PontoViagemDao pontoViagemDao = Ejb.lookup(PontoViagemDao.class);
		PontoViagem pontoViagem = eventoSaida.getPontoViagem();
		pontoViagem.setDataHoraSaida(eventoSaida.getDataHoraSaida());
		pontoViagem.setStatus(StatusPontoViagem.NO_LOCAL);
		pontoViagemDao.salvar(pontoViagem);
	}
	
	
	
}