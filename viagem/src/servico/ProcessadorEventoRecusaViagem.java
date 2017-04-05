package servico;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.ViagemDao;
import enums.StatusViagem;
import modelo.Evento;
import modelo.EventoRecusaViagem;
import modelo.Viagem;

@Stateless
public class ProcessadorEventoRecusaViagem extends ProcessadorEvento {

	@EJB
	private ViagemDao viagemDao;
	
	@Override
	public void eventoCriado(Evento evento) throws Exception {
		if (!(evento instanceof EventoRecusaViagem)) {
			throw new Exception("Classe de evento não prevista");
		}
		EventoRecusaViagem eventoRecusaViagem = (EventoRecusaViagem)evento;
		Viagem viagem = eventoRecusaViagem.getViagem();
		viagem.setStatus(StatusViagem.RECUSADA);
		viagem.setDataHoraStatus(eventoRecusaViagem.getDataHoraRecusa());
		viagemDao.salvar(viagem);
	}
	
	
}