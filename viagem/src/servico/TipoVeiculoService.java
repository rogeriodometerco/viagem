package servico;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.TipoVeiculoDao;
import dto.Listagem;
import exception.AppException;
import modelo.TipoVeiculo;

@Stateless
public class TipoVeiculoService {

	@EJB
	private TipoVeiculoDao tipoVeiculoDao;
	
	public TipoVeiculo salvar(TipoVeiculo tipoVeiculo) throws AppException {
		TipoVeiculo result = null;
		try {
			List<String> erros = validarTipoVeiculo(tipoVeiculo);
			if (erros.size() > 0) {
				throw new AppException(erros.toString());
			}
			result = tipoVeiculoDao.salvar(tipoVeiculo);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return result;
	}

	private List<String> validarTipoVeiculo(TipoVeiculo tipoVeiculo) {
		List<String> erros = new ArrayList<String>();
		if (tipoVeiculo.getNome() == null || tipoVeiculo.getNome().trim().length() == 0) {
			erros.add("Nome do tipo de veículo é obrigatório");
		}
		return erros;
	}

	public List<TipoVeiculo> listar() throws AppException {
		List<TipoVeiculo> result = new ArrayList<TipoVeiculo>();
		try {
			result = tipoVeiculoDao.listar();
		} catch(Exception e) {
			throw new AppException("Erro ao listar tipos de veículo: " + e.getMessage(), e);
		}
		return result;
	}

	public TipoVeiculo recuperar(Long id) throws AppException {
		TipoVeiculo result = null;
		try {
			result = tipoVeiculoDao.recuperar(id);
		} catch(Exception e) {
			throw new AppException("Erro ao recuperar tipo de veículo: " + e.getMessage(), e);
		}
		return result;
	}

	public Listagem<TipoVeiculo> listarOrdenadoPorNome(int pagina, int tamanhoPagina)	
			throws AppException { 

		Listagem<TipoVeiculo> listagem = new Listagem<TipoVeiculo>();

		List<TipoVeiculo> lista = new ArrayList<TipoVeiculo>();
		if (pagina == 0) {
			pagina = 1;
		}
		if (tamanhoPagina == 0) {
			tamanhoPagina = 10;
		}
		try {
			lista = tipoVeiculoDao.listarOrdenadoPorNome(pagina, tamanhoPagina);
			Long count = tipoVeiculoDao.contar();
			listagem.set(pagina, lista, count);
		} catch(Exception e) {
			throw new AppException("Erro ao listar tipos de veículo: " + e.getMessage(), e);
		}
		return listagem;
	}


	public Listagem<TipoVeiculo> listarPorNomeOrdenadoPorNome(int pagina, int tamanhoPagina, String iniciandoPor)	
			throws AppException { 

		Listagem<TipoVeiculo> listagem = new Listagem<TipoVeiculo>();

		List<TipoVeiculo> lista = new ArrayList<TipoVeiculo>();
		if (iniciandoPor == null || iniciandoPor.trim() == "") {
			throw new AppException("Nome ou parte do nome do tipo de veículo para pesquisa é obrigatório");
		}
		if (pagina == 0) {
			pagina = 1;
		}
		if (tamanhoPagina == 0) {
			tamanhoPagina = 10;
		}
		try {
			lista = tipoVeiculoDao.listarPorNomeOrdenadoPorNome(pagina, tamanhoPagina, iniciandoPor);
			Long count = tipoVeiculoDao.contarPorNome(iniciandoPor);
			listagem.set(pagina, lista, count);
		} catch(Exception e) {
			throw new AppException("Erro ao listar tipos de veículo por nome: " + e.getMessage(), e);
		}
		return listagem;
	}

}
