package servico;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.PontoViagemDao;
import modelo.Evento;
import modelo.EventoPrevisaoChegada;
import modelo.PontoViagem;

@Stateless
public class ProcessadorEventoPrevisaoChegada extends ProcessadorEvento {

	@EJB
	private PontoViagemDao pontoViagemDao;
	
	@Override
	public void eventoCriado(Evento evento) throws Exception {
		if (!(evento instanceof EventoPrevisaoChegada)) {
			throw new Exception("Classe de evento não prevista");
		}
		EventoPrevisaoChegada eventoPrevisaoChegada = (EventoPrevisaoChegada)evento;
		PontoViagem pontoViagem = eventoPrevisaoChegada.getPontoViagem();
		pontoViagem.setDataHoraPrevistaChegada(eventoPrevisaoChegada.getDataHoraPrevista());
		pontoViagemDao.salvar(pontoViagem);
	}
	
	
	
}