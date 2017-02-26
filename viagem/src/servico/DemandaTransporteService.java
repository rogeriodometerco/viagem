package servico;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.DemandaTransporteDao;
import dto.Listagem;
import exception.AppException;
import modelo.DemandaTransporte;

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
			System.out.println(new Gson().toJsonTree(result).getAsJsonObject());
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
