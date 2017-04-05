package servico;

import javax.ejb.Stateless;

import modelo.Evento;
import modelo.EventoLocalizacao;

@Stateless
public class ProcessadorEventoLocalizacao extends ProcessadorEvento {

	@Override
	public void eventoCriado(Evento evento) throws Exception {
		if (!(evento instanceof EventoLocalizacao)) {
			throw new Exception("Classe de evento não prevista");
		}

		// Nada a processar.
	}
	
	
}