package servico;

import dao.EventoDao;
import modelo.Evento;
import util.Ejb;

/**
 * Processa os eventos.
 * @author Rog�rio
 *
 * Regras:
 * - Cria��o da viagem: inicializa os status de todas as entidades envolvidas: 
 * viagem, pontoViagem, operacaoViagem, etapaViagem, etapaEntrega, entrega.
 * - T�rmino da opera��o: finaliza a opera��o e a entrega, se a etapa feita pelo ve�culo finda no ponto destino da entrega.
 * - Chegada no ponto: atualiza status do ponto, finaliza a etapa se o fim da etapa � naquele ponto.
 * - Sa�da do ponto: atualiza status do ponto, inicia a etapa se ela inicia naquele ponto.
 * - Previs�o de chegada: atualiza previs�o de chegada no ponto.
 * 
 */
public abstract class ProcessadorEvento {
	
	public void processarEvento(Evento evento) throws Exception {
		EventoDao eventoDao = Ejb.lookup(EventoDao.class);
		Evento eventoCriado = eventoDao.salvar(evento);
		eventoCriado(eventoCriado);
		
	}
	
	protected abstract void eventoCriado(Evento evento) throws Exception;
	
}