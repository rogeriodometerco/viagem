package servico;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.ViagemDao;
import enums.Crud;
import enums.StatusEntrega;
import enums.StatusEtapaEntrega;
import enums.StatusPontoViagem;
import enums.StatusViagem;
import exception.AppException;
import modelo.EtapaEntrega;
import modelo.PontoViagem;
import modelo.Viagem;

@Stateless
public class ViagemService {

	@EJB
	private ViagemDao viagemDao;
	
	public Viagem criar(Viagem viagem) throws AppException {
		Viagem result = null;
		try {
			List<String> erros = validarViagem(viagem);
			if (erros.size() > 0) {
				throw new AppException(erros.toString());
			}
			completarInformacoes(viagem, Crud.INCLUSAO);
			result = viagemDao.salvar(viagem);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return result;
	}

	private void completarInformacoes(Viagem viagem, Crud crud) {
		if (crud.equals(Crud.INCLUSAO)) {
			viagem.setStauts(StatusViagem.PENDENTE);
			for (EtapaEntrega etapa: viagem.getEtapas()) {
				etapa.setStatus(StatusEtapaEntrega.PENDENTE);
				etapa.getEntrega().setStatus(StatusEntrega.PENDENTE);
			}
			for (PontoViagem ponto: viagem.getPontos()) {
				ponto.setStatus(StatusPontoViagem.PENDENTE);
			}
		}
	}
	
	private List<String> validarViagem(Viagem viagem) {
		List<String> erros = new ArrayList<String>();
		
		// TODO
		
		if (viagem.getVeiculo() == null) {
			erros.add("Ve�culo da viagem � obrigat�rio");
		}
		if (viagem.getMotorista() == null) {
			erros.add("Motorista da viagem � obrigat�rio");
		}
		if (viagem.getEtapas() == null || viagem.getEtapas().isEmpty()) {
			erros.add("Deve ser acrescentada ao menos uma demanda para a viagem");
		} else {
			for (EtapaEntrega etapa: viagem.getEtapas()) {
				if (etapa.getEntrega().getQuantidade() == null || etapa.getEntrega().getQuantidade() <= 0) {
					// TODO Retirar coment�rio abaixo para ativar valida��o.
					//erros.add("Quantidade de produto deve ser informada");
					break;
				}
			}
		}
		for (PontoViagem ponto: viagem.getPontos()) {
			if (ponto.getDataChegadaAcordada() == null) {
				// TODO Retirar coment�rio abaixo para ativar valida��o.
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
	
}