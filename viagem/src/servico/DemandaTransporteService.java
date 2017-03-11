package servico;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.ContaDao;
import dao.DemandaTransporteDao;
import dto.Listagem;
import exception.AppException;
import modelo.Conta;
import modelo.DemandaTransporte;
import modelo.TransportadorDemandaAutorizado;

@Stateless
public class DemandaTransporteService {

	private static final String INCLUSAO = "INCLUSAO";
	private static final String ALTERACAO = "ALTERACAO";
	
	@EJB
	private DemandaTransporteDao demandaTransporteDao;
	@EJB
	private ContaDao contaDao;
	
	public DemandaTransporte criar(DemandaTransporte demandaTransporte) throws AppException {
		DemandaTransporte result = null;
		try {
			List<String> erros = validarDemandaTransporte(INCLUSAO, demandaTransporte);
			if (erros.size() > 0) {
				throw new AppException(erros.toString());
			}
			for (TransportadorDemandaAutorizado autorizado: demandaTransporte.getTransportadores()) {
				autorizado.setAtivo(true);
			}
			result = demandaTransporteDao.salvar(demandaTransporte);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return result;
	}

	// Não utilizar, apenas teste.
	public DemandaTransporte alterar(DemandaTransporte dto) throws AppException {
		DemandaTransporte result = null;
		try {
			result = demandaTransporteDao.recuperar(dto.getId());
			// Altera apenas quantidade.
			// TODO Pensar num método mais coeso. Ex.: alterarQuantidade(demanda).
			result.setQuantidade(dto.getQuantidade());
			List<String> erros = validarDemandaTransporte(ALTERACAO, dto);
			if (erros.size() > 0) {
				throw new AppException(erros.toString());
			}
			result = demandaTransporteDao.salvar(dto);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return result;
	}

	public List<DemandaTransporte> listar() throws AppException {
		List<DemandaTransporte> result = new ArrayList<DemandaTransporte>();
		try {
			result = demandaTransporteDao.listar();
		} catch(Exception e) {
			throw new AppException("Erro ao listar demandas de transporte: " + e.getMessage(), e);
		}
		return result;
	}

	public DemandaTransporte recuperar(Long id) throws AppException {
		DemandaTransporte result = null;
		try {
			result = demandaTransporteDao.recuperar(id);
		} catch(Exception e) {
			throw new AppException("Erro ao recuperar demanda de transporte: " + e.getMessage(), e);
		}
		return result;
	}


	public DemandaTransporte alterarQuantidade(Long demandaId, Integer novaQuantidade) throws AppException {
		DemandaTransporte result = null;
		// TODO Implementar regras específicas desta funcionalidade, caso existam.
		try {
			result = demandaTransporteDao.recuperar(demandaId);
			result.setQuantidade(novaQuantidade);
			List<String> erros = validarDemandaTransporte(ALTERACAO, result);
			if (erros.size() > 0) {
				throw new AppException(erros.toString());
			}
			result = demandaTransporteDao.salvar(result);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return result;
	}

	public Set<TransportadorDemandaAutorizado> adicionarTransportadores(Long demandaId, List<Conta> transportadores) throws AppException {
		DemandaTransporte demanda = null;
		// TODO Validar se a Conta é de transportador.
		try {
			demanda = demandaTransporteDao.recuperar(demandaId);

			for (Conta contaTransportador: transportadores) {
				
				demanda.adicionarTransportador(contaTransportador);
			}
			
			List<String> erros = validarDemandaTransporte(ALTERACAO, demanda);
			if (erros.size() > 0) {
				throw new AppException(erros.toString());
			}
			demanda = demandaTransporteDao.salvar(demanda);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return demanda.getTransportadores();
	}

	public void inativarTransportadores(Long demandaId, 
			List<TransportadorDemandaAutorizado> transportadores) throws AppException {
		DemandaTransporte result = null;
		// TODO Implementar regras específicas desta funcionalidade, caso existam.
		try {
			result = demandaTransporteDao.recuperar(demandaId);
			for (TransportadorDemandaAutorizado transportador: transportadores) {
				result.inativarTransportador(transportador);
			}
			List<String> erros = validarDemandaTransporte(ALTERACAO, result);
			if (erros.size() > 0) {
				throw new AppException(erros.toString());
			}
			result = demandaTransporteDao.salvar(result);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	private List<String> validarDemandaTransporte(String operacao, DemandaTransporte demandaTransporte) {
		List<String> erros = new ArrayList<String>();

		if (operacao.equals(INCLUSAO) && demandaTransporte.getId() != null) {
			erros.add("ID da demanda de transporte deve ser nula para inclusão");
		}
		if (demandaTransporte.getOrigem() == null) {
			erros.add("Origem da demanda de transporte deve ser informada");
		}
		if (demandaTransporte.getDestino() == null) {
			erros.add("Destino da demanda de transporte deve ser informado");
		}
		if (demandaTransporte.getProduto() == null || demandaTransporte.getProduto().trim().length() < 3) {
			erros.add("Produto da demanda de transporte deve ter no m�nimo 3 caracteres");
		}
		if (demandaTransporte.getQuantidade() == null || demandaTransporte.getQuantidade() <= 0) {
			erros.add("Quantidade de produto da demanda de transporte deve ser informada e maior que zero");
		}
		if (demandaTransporte.getUnidadeQuantidade() == null 
				|| demandaTransporte.getUnidadeQuantidade().trim().length() == 0) {
			erros.add("Unidade de quantidade de produto da demanda de transporte deve ser informada");
		}
		if (demandaTransporte.getTransportadores() == null || demandaTransporte.getTransportadores().isEmpty()) {
			erros.add("Transportadores autorizados a atender a demanda devem ser informados");
		}
		return erros;
	}

	public Listagem<DemandaTransporte> listarOrdenadoPorIdDescendente(int pagina, int tamanhoPagina)	
			throws AppException { 

		Listagem<DemandaTransporte> listagem = new Listagem<DemandaTransporte>();

		List<DemandaTransporte> lista = new ArrayList<DemandaTransporte>();
		if (pagina == 0) {
			pagina = 1;
		}
		if (tamanhoPagina == 0) {
			tamanhoPagina = 10;
		}
		try {
			lista = demandaTransporteDao.listarOrdenadoPorIdDescendente(pagina, tamanhoPagina);
			Long count = demandaTransporteDao.contar();
			listagem.set(pagina, lista, count);
		} catch(Exception e) {
			throw new AppException("Erro ao listar demandas de transporte: " + e.getMessage(), e);
		}
		return listagem;
	}


}
