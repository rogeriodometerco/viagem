package servico;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.ViagemDao;
import enums.StatusEntrega;
import enums.StatusEtapaEntrega;
import enums.StatusOperacaoViagem;
import enums.StatusPontoViagem;
import enums.StatusViagem;
import modelo.Entrega;
import modelo.EtapaEntrega;
import modelo.Evento;
import modelo.EventoCriacaoViagem;
import modelo.OperacaoViagem;
import modelo.PontoViagem;
import modelo.Viagem;

@Stateless
public class ProcessadorEventoCriacaoViagem extends ProcessadorEvento {

	@EJB
	private ViagemDao viagemDao;
	
	@Override
	public void eventoCriado(Evento evento) throws Exception {
		if (!(evento instanceof EventoCriacaoViagem)) {
			throw new Exception("Classe de evento não prevista");
		}
		EventoCriacaoViagem eventoCriacaoViagem = (EventoCriacaoViagem)evento;
		Viagem viagem = eventoCriacaoViagem.getViagem();
		viagem.setStatus(StatusViagem.PENDENTE);
		viagem.setDataHoraStatus(eventoCriacaoViagem.getDataHoraCriacao());
		
		for (PontoViagem pontoViagem: viagem.getPontos()) {
			pontoViagem.setStatus(StatusPontoViagem.PENDENTE);
			pontoViagem.setDataHoraStatus(new Date());
			
			for (OperacaoViagem operacaoViagem: pontoViagem.getOperacoes()) {
				operacaoViagem.setStatus(StatusOperacaoViagem.PENDENTE);
				operacaoViagem.setDataHoraStatus(new Date());;
				
				EtapaEntrega etapaEntrega = operacaoViagem.getEtapaEntrega();
				if (etapaEntrega.getStatus() == null) {
					etapaEntrega.setStatus(StatusEtapaEntrega.PENDENTE);
					etapaEntrega.setDataHoraStatus(new Date());
				}

				Entrega entrega = operacaoViagem.getEtapaEntrega().getEntrega();
				if (entrega.getStatus() == null) {
					entrega.setStatus(StatusEntrega.PENDENTE);
					entrega.setDataHoraStatus(new Date());
				}
			}
		}
		viagemDao.salvar(viagem);
	}
	
	
}