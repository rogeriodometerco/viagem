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
		// Transforma identifica��o do ve�culo em mai�sculas.
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
			erros.add("Identifica��o do ve�culo � obrigat�rio");
		}
		if (veiculo.getId() != null) {
			Veiculo outro = veiculoDao.recuperarPelaIdentificacao(veiculo.getIdentificacao());
			if (!veiculo.getId().equals(outro.getId())) {
				erros.add("J� existe um ve�culo cadastrado com esta identifica��o");
			}
		}
		return erros;
	}

	public List<Veiculo> listar() throws AppException {
		List<Veiculo> result = new ArrayList<Veiculo>();
		try {
			result = veiculoDao.listar();
		} catch(Exception e) {
			throw new AppException("Erro ao listar ve�culos: " + e.getMessage(), e);
		}
		return result;
	}

	public Veiculo recuperar(Long id) throws AppException {
		Veiculo result = null;
		try {
			result = veiculoDao.recuperar(id);
		} catch(Exception e) {
			throw new AppException("Erro ao recuperar ve�culo: " + e.getMessage(), e);
		}
		return result;
	}

	public Veiculo recuperarPelaIdentificacao(String identificacao) throws AppException {
		Veiculo result = null;
		try {
			result = veiculoDao.recuperarPelaIdentificacao(identificacao);
		} catch(Exception e) {
			throw new AppException("Erro ao recuperar ve�culo: " + e.getMessage(), e);
		}
		return result;
	}

	public List<Veiculo> listarPelaIdentificacao(String query, int rows) throws AppException {
		List<Veiculo> result = null;
		try {
			result = veiculoDao.listarPelaIdentificacao(query, rows);
		} catch(Exception e) {
			throw new AppException("Erro ao listar ve�culos: " + e.getMessage(), e);
		}
		return result;
	}
	
}
