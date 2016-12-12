package servico;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.ViagemDao;
import enums.StatusViagem;
import modelo.Evento;
import modelo.EventoTerminoViagem;
import modelo.Viagem;

@Stateless
public class ProcessadorEventoTerminoViagem extends ProcessadorEvento {

	@EJB
	private ViagemDao viagemDao;
	
	@Override
	public void eventoCriado(Evento evento) throws Exception {
		if (!(evento instanceof EventoTerminoViagem)) {
			throw new Exception("Classe de evento não prevista");
		}
		EventoTerminoViagem eventoTerminoViagem = (EventoTerminoViagem)evento;
		Viagem viagem = eventoTerminoViagem.getViagem();
		viagem.setStatus(StatusViagem.CONCLUIDA);
		viagem.setDataHoraStatus(eventoTerminoViagem.getDataHoraTermino());
		viagemDao.salvar(viagem);
	}
	
	
}