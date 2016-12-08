package servico;

import dao.PontoViagemDao;
import enums.StatusViagem;
import modelo.Evento;
import modelo.EventoPrevisaoChegada;
import modelo.PontoViagem;
import util.Ejb;

public class ProcessadorEventoPrevisaoChegada extends ProcessadorEvento {

	@Override
	public void eventoCriado(Evento evento) throws Exception {
		if (!(evento instanceof EventoPrevisaoChegada)) {
			throw new Exception("Classe de evento não prevista");
		}
		EventoPrevisaoChegada eventoPrevisaoChegada = (EventoPrevisaoChegada)evento;
		PontoViagemDao pontoViagemDao = Ejb.lookup(PontoViagemDao.class);
		PontoViagem pontoViagem = eventoPrevisaoChegada.getPontoViagem();
		pontoViagem.setDataHoraPrevistaChegada(eventoPrevisaoChegada.getDataHoraPrevista());
		pontoViagemDao.salvar(pontoViagem);
	}
	
	
	
}