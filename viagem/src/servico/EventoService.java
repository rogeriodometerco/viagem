package servico;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.EventoDao;
import exception.AppException;
import modelo.Evento;
import modelo.EventoInicioViagem;
import modelo.EventoPrevisaoChegada;

@Stateless
public class EventoService {
	
	@EJB
	private EventoDao eventoDao;
	
	private Map<Object, ProcessadorEvento> processadores;
	
	@PostConstruct
	private void inicializar() {
		processadores = new HashMap<Object, ProcessadorEvento>();
		processadores.put(EventoInicioViagem.class, new ProcessadorEventoInicioViagem());
		processadores.put(EventoPrevisaoChegada.class, new ProcessadorEventoPrevisaoChegada());
	}
	
	public void registrarEvento(Evento evento) throws AppException {
		try {
			ProcessadorEvento processador = processadores.get(evento.getClass());
			processador.processarEvento(evento);
		} catch (Exception e) {
			throw new AppException("Erro ao registrar evento: " + e.getMessage());
		}
	}
	
}