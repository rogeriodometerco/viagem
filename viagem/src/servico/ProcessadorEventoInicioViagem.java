package servico;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.ViagemDao;
import enums.StatusViagem;
import modelo.Evento;
import modelo.EventoInicioViagem;
import modelo.Viagem;

@Stateless
public class ProcessadorEventoInicioViagem extends ProcessadorEvento {

	@EJB
	private ViagemDao viagemDao;
	
	@Override
	public void eventoCriado(Evento evento) throws Exception {
		if (!(evento instanceof EventoInicioViagem)) {
			throw new Exception("Classe de evento não prevista");
		}
		EventoInicioViagem eventoInicioViagem = (EventoInicioViagem)evento;
		Viagem viagem = eventoInicioViagem.getViagem();
		viagem.setStatus(StatusViagem.INICIADA);
		viagem.setDataHoraStatus(eventoInicioViagem.getDataHoraInicio());
		viagemDao.salvar(viagem);
	}
	
	
}