package servico;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.ViagemDao;
import enums.Crud;
import enums.StatusEntrega;
import enums.StatusEtapaEntrega;
import enums.StatusOperacaoViagem;
import enums.StatusPontoViagem;
import enums.StatusViagem;
import enums.TipoTerminoOperacao;
import exception.AppException;
import modelo.EtapaEntrega;
import modelo.EventoChegada;
import modelo.EventoCriacaoViagem;
import modelo.EventoInicioViagem;
import modelo.EventoPrevisaoChegada;
import modelo.EventoSaida;
import modelo.EventoTerminoOperacao;
import modelo.OperacaoViagem;
import modelo.PontoViagem;
import modelo.Viagem;

@Stateless
public class ViagemService {

	@EJB
	private ViagemDao viagemDao;

	@EJB
	private EventoService eventoService;

	public Viagem criar(Viagem viagem) throws AppException {
		Viagem result = null;
		try {
			List<String> erros = validarViagem(viagem);
			if (erros.size() > 0) {
				throw new AppException(erros.toString());
			}
			//completarInformacoes(viagem, Crud.INCLUSAO);
			result = viagemDao.salvar(viagem);
			viagemCriada(result);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return result;
	}

	private void completarInformacoes(Viagem viagem, Crud crud) {
		if (crud.equals(Crud.INCLUSAO)) {
			viagem.setStatus(StatusViagem.PENDENTE);
			viagem.setDataHoraStatus(new Date());
			for (EtapaEntrega etapa: viagem.getEtapas()) {
				etapa.setStatus(StatusEtapaEntrega.PENDENTE);
				etapa.getEntrega().setStatus(StatusEntrega.PENDENTE);
			}
			for (PontoViagem ponto: viagem.getPontos()) {
				ponto.setStatus(StatusPontoViagem.PENDENTE);
				for (OperacaoViagem operacao: ponto.getOperacoes()) {
					operacao.setStatus(StatusOperacaoViagem.PENDENTE);
				}
			}
		}
	}

	private List<String> validarViagem(Viagem viagem) {
		List<String> erros = new ArrayList<String>();

		// TODO

		if (viagem.getVeiculo() == null) {
			erros.add("Veículo da viagem é obrigatório");
		}
		if (viagem.getMotorista() == null) {
			erros.add("Motorista da viagem é obrigatório");
		}
		if (viagem.getEtapas() == null || viagem.getEtapas().isEmpty()) {
			erros.add("Deve ser acrescentada ao menos uma demanda para a viagem");
		} else {
			for (EtapaEntrega etapa: viagem.getEtapas()) {
				if (etapa.getEntrega().getQuantidade() == null || etapa.getEntrega().getQuantidade() <= 0) {
					// TODO Retirar comentário abaixo para ativar validação.
					//erros.add("Quantidade de produto deve ser informada");
					break;
				}
			}
		}
		for (PontoViagem ponto: viagem.getPontos()) {
			if (ponto.getDataChegadaAcordada() == null) {
				// TODO Retirar comentário abaixo para ativar validação.
				erros.add("Data prevista de chegada em " + ponto.getEstabelecimento().getNome() + " deve ser informada");
			}
		}

		return erros;
	}

	public List<Viagem> listar() throws AppException {
		List<Viagem> result = new ArrayList<Viagem>();
		try {
			result = viagemDao.listar();
		} catch(Exception e) {
			throw new AppException("Erro ao listar viagens: " + e.getMessage(), e);
		}
		return result;
	}

	public Viagem recuperar(Long id) throws AppException {
		Viagem result = null;
		try {
			result = viagemDao.recuperar(id);
		} catch(Exception e) {
			throw new AppException("Erro ao recuperar viagem: " + e.getMessage(), e);
		}
		return result;
	}

	public Viagem obterViagemEmFocoDoMotoristaLogado() throws AppException {
		// TODO Recuperar motorista logado e passar como parâmetro.
		Viagem viagemEmFoco = null;
		try {
			viagemEmFoco = viagemDao.recuperarViagensNaoEncerradas(null)
					.get(0);
		} catch (Exception e) {
			throw new AppException("Erro ao recuperar viagem para o motorista: " + e.getMessage());
		}
		return viagemEmFoco;
	}

	private void viagemCriada(Viagem viagem) throws Exception {
		EventoCriacaoViagem evento = new EventoCriacaoViagem();
		evento.setViagem(viagem);
		evento.setDataHoraCriacao(new Date());
		evento.setDataHoraRegistro(new Date());
		eventoService.registrarEvento(evento);
	}

	public void iniciarViagem(Viagem viagem) throws AppException {
		try {
			EventoInicioViagem evento = new EventoInicioViagem();
			evento.setViagem(viagem);
			evento.setDataHoraInicio(new Date());
			evento.setDataHoraRegistro(new Date());
			eventoService.registrarEvento(evento);
		} catch (Exception e) {
			throw new AppException("Erro ao iniciar viagem: " + e.getMessage());
		}
	}

	public void registrarPrevisaoChegada(PontoViagem pontoViagem) throws AppException {
		try {
			EventoPrevisaoChegada evento = new EventoPrevisaoChegada();
			evento.setPontoViagem(pontoViagem);
			evento.setDataHoraPrevista(pontoViagem.getDataHoraPrevistaChegada());
			evento.setDataHoraRegistro(new Date());
			eventoService.registrarEvento(evento);
		} catch (Exception e) {
			throw new AppException("Erro ao registrar previsão de chegada: " + e.getMessage());
		}
	}

	public void registrarChegada(PontoViagem pontoViagem) throws AppException {
		try {
			EventoChegada evento = new EventoChegada();
			evento.setPontoViagem(pontoViagem);
			evento.setDataHoraChegada(pontoViagem.getDataHoraChegada());
			evento.setDataHoraRegistro(new Date());
			eventoService.registrarEvento(evento);
		} catch (Exception e) {
			throw new AppException("Erro ao registrar chegada: " + e.getMessage());
		}
	}

	public void registrarSaida(PontoViagem pontoViagem) throws AppException {
		try {
			EventoSaida evento = new EventoSaida();
			evento.setPontoViagem(pontoViagem);
			evento.setDataHoraSaida(pontoViagem.getDataHoraSaida());
			evento.setDataHoraRegistro(new Date());
			eventoService.registrarEvento(evento);
		} catch (Exception e) {
			throw new AppException("Erro ao registrar saída: " + e.getMessage());
		}
	}

	public void registrarTerminoOperacoes(PontoViagem pontoViagem) throws AppException {
		try {
			for (OperacaoViagem operacao: pontoViagem.getOperacoes()) {
				EventoTerminoOperacao evento = new EventoTerminoOperacao();
				evento.setOperacaoViagem(operacao);
				evento.setDataHoraTermino(new Date());
				if (operacao.getStatus().equals(StatusOperacaoViagem.ABORTADA)) {
					evento.setTipoTermino(TipoTerminoOperacao.ABORTADA);
				} else {
					evento.setTipoTermino(TipoTerminoOperacao.REALIZADA);
				}
				evento.setDataHoraRegistro(new Date());
				eventoService.registrarEvento(evento);
			}
		} catch (Exception e) {
			throw new AppException("Erro ao registrar término das operações: " + e.getMessage());
		}
	}
}

