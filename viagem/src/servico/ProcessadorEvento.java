package servico;

import dao.EventoDao;
import modelo.Evento;
import util.Ejb;

public abstract class ProcessadorEvento {
	
	public void processarEvento(Evento evento) throws Exception {
		EventoDao eventoDao = Ejb.lookup(EventoDao.class);
		Evento eventoCriado = eventoDao.salvar(evento);
		eventoCriado(eventoCriado);
		
	}
	
	protected abstract void eventoCriado(Evento evento) throws Exception;
	
}