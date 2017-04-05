package servico;

import javax.ejb.EJB;

import dao.EventoDao;
import dao.UltimaLocalizacaoMotoristaDao;
import modelo.Evento;
import modelo.Localizacao;
import modelo.UltimaLocalizacaoMotorista;
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

	@EJB
	private UltimaLocalizacaoMotoristaService ultimaLocalizacaoMotoristaService;

	public void processarEvento(Evento evento) throws Exception {
		EventoDao eventoDao = Ejb.lookup(EventoDao.class);
		Evento eventoCriado = eventoDao.salvar(evento);
		eventoCriado(eventoCriado);
		if (eventoCriado.getLocalizacao() != null) {
			atualizarUltimaLocalizacaoMotoristaSeNecessario(eventoCriado.getLocalizacao());
		}

	}

	private void atualizarUltimaLocalizacaoMotoristaSeNecessario(Localizacao localizacao) throws Exception {
		UltimaLocalizacaoMotorista ultimaLocalizacaoMotorista = ultimaLocalizacaoMotoristaService
				.recuperar(localizacao.getMotorista());

		if (ultimaLocalizacaoMotorista == null 
				|| localizacao.getDataHora().after(ultimaLocalizacaoMotorista.getDataHora())) {

			if (ultimaLocalizacaoMotorista == null) {
				ultimaLocalizacaoMotorista = new UltimaLocalizacaoMotorista();
				ultimaLocalizacaoMotorista.setMotorista(localizacao.getMotorista());
			}
			ultimaLocalizacaoMotorista.setDataHora(localizacao.getDataHora());
			ultimaLocalizacaoMotorista.setLatitude(localizacao.getLatitude());
			ultimaLocalizacaoMotorista.setLongitude(localizacao.getLongitude());
			ultimaLocalizacaoMotorista.setVelocidade(localizacao.getVelocidade());

			ultimaLocalizacaoMotoristaService.salvar(ultimaLocalizacaoMotorista);
		}
	}

	protected abstract void eventoCriado(Evento evento) throws Exception;

}