package servico;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.ViagemDao;
import enums.StatusViagem;
import modelo.Evento;
import modelo.EventoAceiteViagem;
import modelo.EventoAceiteViagem;
import modelo.Viagem;

@Stateless
public class ProcessadorEventoAceiteViagem extends ProcessadorEvento {

	@EJB
	private ViagemDao viagemDao;
	
	@Override
	public void eventoCriado(Evento evento) throws Exception {
		if (!(evento instanceof EventoAceiteViagem)) {
			throw new Exception("Classe de evento não prevista");
		}
		EventoAceiteViagem eventoAceiteViagem = (EventoAceiteViagem)evento;
		Viagem viagem = eventoAceiteViagem.getViagem();
		viagem.setStatus(StatusViagem.ACEITA);
		viagem.setDataHoraStatus(eventoAceiteViagem.getDataHoraAceite());
		viagemDao.salvar(viagem);
	}
	
	
}