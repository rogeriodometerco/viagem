package servico;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.EventoDao;
import exception.AppException;
import modelo.Evento;
import modelo.EventoChegada;
import modelo.EventoCriacaoViagem;
import modelo.EventoInicioViagem;
import modelo.EventoPrevisaoChegada;
import modelo.EventoSaida;
import modelo.EventoTerminoOperacao;
import modelo.EventoTerminoViagem;

@Stateless
public class EventoService {
	
	@EJB
	private EventoDao eventoDao;
	
	@EJB
	private ProcessadorEventoCriacaoViagem processadorEventoCriacaoViagem;
	@EJB
	private ProcessadorEventoInicioViagem processadorEventoInicioViagem;
	@EJB
	private ProcessadorEventoPrevisaoChegada processadorEventoPrevisaoChegada;
	@EJB
	private ProcessadorEventoChegada processadorEventoChegada;
	@EJB
	private ProcessadorEventoSaida processadorEventoSaida;
	@EJB
	private ProcessadorEventoTerminoOperacao processadorEventoTerminoOperacao;
	@EJB
	private ProcessadorEventoTerminoViagem processadorEventoTerminoViagem;
	
	private Map<Object, ProcessadorEvento> processadores;
	
	@PostConstruct
	private void inicializar() {
		
		processadores = new HashMap<Object, ProcessadorEvento>();
		processadores.put(EventoCriacaoViagem.class, processadorEventoCriacaoViagem);
		processadores.put(EventoInicioViagem.class, processadorEventoInicioViagem);
		processadores.put(EventoPrevisaoChegada.class, processadorEventoPrevisaoChegada);
		processadores.put(EventoChegada.class, processadorEventoChegada);
		processadores.put(EventoSaida.class, processadorEventoSaida);
		processadores.put(EventoTerminoOperacao.class, processadorEventoTerminoOperacao);
		processadores.put(EventoTerminoViagem.class, processadorEventoTerminoViagem);
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