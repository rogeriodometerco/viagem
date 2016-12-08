package servico;

import dao.PontoViagemDao;
import enums.StatusPontoViagem;
import modelo.Evento;
import modelo.EventoChegada;
import modelo.PontoViagem;
import util.Ejb;

public class ProcessadorEventoChegada extends ProcessadorEvento {

	@Override
	public void eventoCriado(Evento evento) throws Exception {
		if (!(evento instanceof EventoChegada)) {
			throw new Exception("Classe de evento não prevista");
		}
		EventoChegada eventoChegada = (EventoChegada)evento;
		PontoViagemDao pontoViagemDao = Ejb.lookup(PontoViagemDao.class);
		PontoViagem pontoViagem = eventoChegada.getPontoViagem();
		pontoViagem.setDataHoraChegada(eventoChegada.getDataHoraChegada());
		pontoViagem.setStatus(StatusPontoViagem.NO_LOCAL);
		pontoViagemDao.salvar(pontoViagem);
	}
	
	
	
}