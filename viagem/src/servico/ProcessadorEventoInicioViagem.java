package servico;

import dao.ViagemDao;
import enums.StatusViagem;
import modelo.Evento;
import modelo.EventoInicioViagem;
import modelo.Viagem;
import util.Ejb;

public class ProcessadorEventoInicioViagem extends ProcessadorEvento {

	@Override
	public void eventoCriado(Evento evento) throws Exception {
		if (!(evento instanceof EventoInicioViagem)) {
			throw new Exception("Classe de evento não prevista");
		}
		EventoInicioViagem eventoInicioViagem = (EventoInicioViagem)evento;
		ViagemDao viagemDao = Ejb.lookup(ViagemDao.class);
		Viagem viagem = eventoInicioViagem.getViagem();
		viagem.setStauts(StatusViagem.INICIADA);
		viagem.setDataHoraStatus(eventoInicioViagem.getDataHoraInicio());
		viagemDao.salvar(viagem);
	}
	
	
}