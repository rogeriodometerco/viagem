package servico;

import dao.EventoDao;
import modelo.Evento;
import util.Ejb;

/**
 * Processa os eventos.
 * @author Rogério
 *
 * Regras:
 * - Criação da viagem: inicializa os status de todas as entidades envolvidas: 
 * viagem, pontoViagem, operacaoViagem, etapaViagem, etapaEntrega, entrega.
 * - Término da operação: finaliza a operação e a entrega, se a etapa feita pelo veículo finda no ponto destino da entrega.
 * - Chegada no ponto: atualiza status do ponto, finaliza a etapa se o fim da etapa é naquele ponto.
 * - Saída do ponto: atualiza status do ponto, inicia a etapa se ela inicia naquele ponto.
 * - Previsão de chegada: atualiza previsão de chegada no ponto.
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