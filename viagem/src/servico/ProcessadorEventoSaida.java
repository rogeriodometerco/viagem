package servico;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.EntregaDao;
import dao.EtapaEntregaDao;
import dao.PontoViagemDao;
import enums.StatusEntrega;
import enums.StatusEtapaEntrega;
import enums.StatusPontoViagem;
import modelo.Entrega;
import modelo.EtapaEntrega;
import modelo.Evento;
import modelo.EventoSaida;
import modelo.OperacaoViagem;
import modelo.PontoViagem;

@Stateless
public class ProcessadorEventoSaida extends ProcessadorEvento {

	@EJB
	private PontoViagemDao pontoViagemDao;
	@EJB
	private EtapaEntregaDao etapaEntregaDao;
	@EJB
	private EntregaDao entregaDao;
	
	@Override
	public void eventoCriado(Evento evento) throws Exception {
		if (!(evento instanceof EventoSaida)) {
			throw new Exception("Classe de evento não prevista");
		}
		EventoSaida eventoSaida = (EventoSaida)evento;
		PontoViagem pontoViagem = eventoSaida.getPontoViagem();
		pontoViagem.setDataHoraSaida(eventoSaida.getDataHoraSaida());
		pontoViagem.setStatus(StatusPontoViagem.CONCLUIDO);
		pontoViagemDao.salvar(pontoViagem);
		
		// Se há alguma etapa que inicia neste local, inicia a etapa.
		// Se a entrega inicia neste local, coloca em trânsito.
		EtapaEntrega etapaEntrega = null;
		Entrega entrega = null;
		for (OperacaoViagem operacao: pontoViagem.getOperacoes()) {
			etapaEntrega = operacao.getEtapaEntrega();
			entrega = etapaEntrega.getEntrega();
			
			if (etapaEntrega.getOrigem().equals(pontoViagem.getEstabelecimento())) {
				etapaEntrega.setStatus(StatusEtapaEntrega.TRANSITO);
				etapaEntrega.setDataHoraStatus(eventoSaida.getDataHoraSaida());
				etapaEntregaDao.salvar(etapaEntrega);
			}
			if (entrega.getOrigem().equals(pontoViagem.getEstabelecimento())) {
				entrega.setStatus(StatusEntrega.A_CAMINHO);
				entrega.setDataHoraStatus(eventoSaida.getDataHoraSaida());
				entregaDao.salvar(entrega);
			}
		}
		
	}
	
	
	
}