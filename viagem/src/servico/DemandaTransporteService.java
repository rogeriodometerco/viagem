package servico;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.DemandaTransporteDao;
import enums.Crud;
import enums.StatusDemandaTransporte;
import exception.AppException;
import modelo.DemandaTransporte;
import modelo.TransportadorDemandaAutorizado;

@Stateless
public class DemandaTransporteService {

	@EJB
	private DemandaTransporteDao demandaTransporteDao;

	public DemandaTransporte salvar(DemandaTransporte demandaTransporte) throws AppException {
		DemandaTransporte result = null;
		try {
			List<String> erros = validarDemandaTransporte(demandaTransporte);
			if (erros.size() > 0) {
				throw new AppException(erros.toString());
			}
			result = demandaTransporteDao.salvar(demandaTransporte);
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

	private List<String> validarDemandaTransporte(DemandaTransporte demandaTransporte) {
		List<String> erros = new ArrayList<String>();
		if (demandaTransporte.getOrigem() == null) {
			erros.add("Origem da demanda de transporte deve ser informada");
		}
		if (demandaTransporte.getDestino() == null) {
			erros.add("Destino da demanda de transporte deve ser informado");
		}
		if (demandaTransporte.getProduto() == null || demandaTransporte.getProduto().trim().length() < 3) {
			erros.add("Produto da demanda de transporte deve ter no mínimo 3 caracteres");
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


}
