package servico;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.EventoDao;
import exception.AppException;
import modelo.Evento;
import modelo.EventoAceiteViagem;
import modelo.EventoChegada;
import modelo.EventoCriacaoViagem;
import modelo.EventoInicioViagem;
import modelo.EventoLocalizacao;
import modelo.EventoPrevisaoChegada;
import modelo.EventoRecusaViagem;
import modelo.EventoSaida;
import modelo.EventoTerminoOperacao;
import modelo.EventoTerminoViagem;

@Stateless
public class EventoService {
	
	@EJB
	private EventoDao eventoDao;
	
	@EJB
	private ProcessadorEventoLocalizacao processadorEventoLocalizacao;
	@EJB
	private ProcessadorEventoCriacaoViagem processadorEventoCriacaoViagem;
	@EJB
	private ProcessadorEventoAceiteViagem processadorEventoAceiteViagem;
	@EJB
	private ProcessadorEventoRecusaViagem processadorEventoRecusaViagem;
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
		processadores.put(EventoLocalizacao.class, processadorEventoLocalizacao);
		processadores.put(EventoCriacaoViagem.class, processadorEventoCriacaoViagem);
		processadores.put(EventoAceiteViagem.class, processadorEventoAceiteViagem);
		processadores.put(EventoRecusaViagem.class, processadorEventoRecusaViagem);
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
	
	public List<Evento> listar() throws AppException {
		List<Evento> result = null;
		try {
			result = eventoDao.listar();
		} catch (Exception e) {
			throw new AppException("Erro ao listar eventos: " + e.getMessage());
		}
		return result;
	}
	
}