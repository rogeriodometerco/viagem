package servico;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.EventoDaoGeral;
import exception.AppException;
import exception.AppPersistenceException;
import modelo.EventoGeral;

@Stateless
public class EventoServiceGeral {

	@EJB
	private EventoDaoGeral eventoDaoGeral;
	
	public EventoGeral registrar(EventoGeral eventoGeral) throws AppException {
		EventoGeral result = null;
		try {
			result = eventoDaoGeral.salvar(eventoGeral);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return result;
	}
}
