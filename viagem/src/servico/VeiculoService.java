package servico;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.VeiculoDao;
import exception.AppException;
import modelo.Veiculo;

@Stateless
public class VeiculoService {

	@EJB
	private VeiculoDao veiculoDao;
	
	public Veiculo salvar(Veiculo veiculo) throws AppException {
		// Transforma identificação do veículo em maiúsculas.
		if (veiculo.getIdentificacao() != null) {
			veiculo.setIdentificacao(veiculo.getIdentificacao().toUpperCase());
		}
		
		Veiculo result = null;
		try {
			List<String> erros = validarVeiculo(veiculo);
			if (erros.size() > 0) {
				throw new AppException(erros.toString());
			}
			result = veiculoDao.salvar(veiculo);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return result;
	}

	private List<String> validarVeiculo(Veiculo veiculo) throws Exception {
		List<String> erros = new ArrayList<String>();
		if (veiculo.getIdentificacao() == null || veiculo.getIdentificacao().trim().length() == 0) {
			erros.add("Identificação do veículo é obrigatório");
		}
		if (veiculo.getId() != null) {
			Veiculo outro = veiculoDao.recuperarPelaIdentificacao(veiculo.getIdentificacao());
			if (!veiculo.getId().equals(outro.getId())) {
				erros.add("Já existe um veículo cadastrado com esta identificação");
			}
		}
		return erros;
	}

	public List<Veiculo> listar() throws AppException {
		List<Veiculo> result = new ArrayList<Veiculo>();
		try {
			result = veiculoDao.listar();
		} catch(Exception e) {
			throw new AppException("Erro ao listar veículos: " + e.getMessage(), e);
		}
		return result;
	}

	public Veiculo recuperar(Long id) throws AppException {
		Veiculo result = null;
		try {
			result = veiculoDao.recuperar(id);
		} catch(Exception e) {
			throw new AppException("Erro ao recuperar veículo: " + e.getMessage(), e);
		}
		return result;
	}

	public Veiculo recuperarPelaIdentificacao(String identificacao) throws AppException {
		Veiculo result = null;
		try {
			result = veiculoDao.recuperarPelaIdentificacao(identificacao);
		} catch(Exception e) {
			throw new AppException("Erro ao recuperar veículo: " + e.getMessage(), e);
		}
		return result;
	}

	public List<Veiculo> listarPelaIdentificacao(String query, int rows) throws AppException {
		List<Veiculo> result = null;
		try {
			result = veiculoDao.listarPelaIdentificacao(query, rows);
		} catch(Exception e) {
			throw new AppException("Erro ao listar veículos: " + e.getMessage(), e);
		}
		return result;
	}
	
}
