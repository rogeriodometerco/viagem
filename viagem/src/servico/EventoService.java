package servico;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.EventoDao;
import exception.AppException;
import exception.AppPersistenceException;
import modelo.Evento;

@Stateless
public class EventoService {

	@EJB
	private EventoDao eventoDao;
	
	public Evento registrar(Evento evento) throws AppException {
		Evento result = null;
		try {
			result = eventoDao.salvar(evento);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return result;
	}
}
