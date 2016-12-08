package servico;

import dao.ViagemDao;
import enums.StatusViagem;
import modelo.Evento;
import modelo.EventoTerminoViagem;
import modelo.Viagem;
import util.Ejb;

public class ProcessadorEventoTerminoViagem extends ProcessadorEvento {

	@Override
	public void eventoCriado(Evento evento) throws Exception {
		if (!(evento instanceof EventoTerminoViagem)) {
			throw new Exception("Classe de evento não prevista");
		}
		EventoTerminoViagem eventoTerminoViagem = (EventoTerminoViagem)evento;
		ViagemDao viagemDao = Ejb.lookup(ViagemDao.class);
		Viagem viagem = eventoTerminoViagem.getViagem();
		viagem.setStauts(StatusViagem.CONCLUIDA);
		viagem.setDataHoraStatus(eventoTerminoViagem.getDataHoraTermino());
		viagemDao.salvar(viagem);
	}
	
	
}