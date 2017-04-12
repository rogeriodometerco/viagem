package servico;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.ComponenteVeiculoDao;
import dao.VeiculoDao;
import dto.Listagem;
import exception.AppException;
import modelo.ComponenteVeiculo;
import modelo.Veiculo;

@Stateless
public class VeiculoService {

	@EJB
	private VeiculoDao veiculoDao;
	@EJB
	private ComponenteVeiculoDao componenteVeiculoDao; 

	public Veiculo salvar(Veiculo veiculo) throws AppException {
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
		if (veiculo.getComponentes() == null || veiculo.getComponentes().isEmpty()) {
			erros.add("O veículo deve possuir pelo menos um componente");
		}
		if (veiculo.getTipo() == null) {
			erros.add("O tipo do veículo deve ser informado");
		}
		for (ComponenteVeiculo componente: veiculo.getComponentes()) {
			if (componente.getPlaca() == null) {
				erros.add("A placa do veículo e de todos os seus componentes deve ser informada");
			}
			if (componente.getPosicaoNoVeiculo() == 0) {
				erros.add("A posição do componente no veículo deve ser informada");
			}
			if (componente.getQuantidadeEixos() < 2) {
				erros.add("A quantidade de eixos do componente do veículo deve maior ou igual a 2");
			}
			if (componente.getTipoCarroceria() == null && 
					(veiculo.getComponentes().size() > 1 || componente.getPosicaoNoVeiculo() > 1)) {
				erros.add("O tipo de carroceria deve ser informada para a placa " + componente.getPlaca());
			}
			if (componente.getId() != null) {
				ComponenteVeiculo outro = componenteVeiculoDao.recuperarPelaPlacaSeExistir(componente.getPlaca());
				if (outro != null && !outro.getId().equals(componente.getId())) {
					erros.add("Já existe um veículo com a placa " + componente.getPlaca() + " cadastrada");
				}
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

	public Veiculo recuperarPelaPlaca(String placa) throws AppException {
		Veiculo result = null;
		try {
			result = veiculoDao.recuperarPelaPlaca(placa);
		} catch(Exception e) {
			throw new AppException("Erro ao recuperar veículo pela placa " + placa + ": " + e.getMessage(), e);
		}
		return result;
	}

	public Veiculo recuperarPelaPlacaSeExistir(String placa) throws AppException {
		Veiculo result = null;
		try {
			result = veiculoDao.recuperarPelaPlacaSeExistir(placa);
		} catch(Exception e) {
			throw new AppException("Erro ao recuperar veículo pela placa " + placa + ": " + e.getMessage(), e);
		}
		return result;
	}


	public List<Veiculo> listarPelaPlaca(String query, int rows) throws AppException {
		List<Veiculo> result = null;
		try {
			result = veiculoDao.listarPorPlacaOrdenadoPorPlaca(1, rows, query);
		} catch(Exception e) {
			throw new AppException("Erro ao listar veículos: " + e.getMessage(), e);
		}
		return result;
	}

	public Listagem<Veiculo> listarOrdenadoPorPlaca(int pagina, int tamanhoPagina)	
			throws AppException { 

		Listagem<Veiculo> listagem = new Listagem<Veiculo>();

		List<Veiculo> lista = new ArrayList<Veiculo>();
		if (pagina == 0) {
			pagina = 1;
		}
		if (tamanhoPagina == 0) {
			tamanhoPagina = 10;
		}
		try {
			lista = veiculoDao.listarOrdenadoPorPlaca(pagina, tamanhoPagina);
			Long count = veiculoDao.contar();
			listagem.set(pagina, lista, count);
		} catch(Exception e) {
			throw new AppException("Erro ao listar veículos: " + e.getMessage(), e);
		}
		return listagem;
	}


	public Listagem<Veiculo> listarPorPlacaOrdenadoPorPlaca(int pagina, int tamanhoPagina, String placaContendo)	
			throws AppException { 

		Listagem<Veiculo> listagem = new Listagem<Veiculo>();

		List<Veiculo> lista = new ArrayList<Veiculo>();
		if (placaContendo == null || placaContendo.trim() == "") {
			throw new AppException("Parte da placa para pesquisa é obrigatório");
		}
		if (pagina == 0) {
			pagina = 1;
		}
		if (tamanhoPagina == 0) {
			tamanhoPagina = 10;
		}
		try {
			lista = veiculoDao.listarPorPlacaOrdenadoPorPlaca(pagina, tamanhoPagina, placaContendo);
			Long count = veiculoDao.contarPorPlaca(placaContendo);
			listagem.set(pagina, lista, count);
		} catch(Exception e) {
			throw new AppException("Erro ao listar veículos pela placa: " + e.getMessage(), e);
		}
		return listagem;
	}	

}