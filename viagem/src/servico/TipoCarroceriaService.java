package servico;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.TipoCarroceriaDao;
import dto.Listagem;
import exception.AppException;
import modelo.TipoCarroceria;

@Stateless
public class TipoCarroceriaService {

	@EJB
	private TipoCarroceriaDao tipoCarroceriaDao;
	
	public TipoCarroceria salvar(TipoCarroceria tipoCarroceria) throws AppException {
		TipoCarroceria result = null;
		try {
			List<String> erros = validarTipoCarroceria(tipoCarroceria);
			if (erros.size() > 0) {
				throw new AppException(erros.toString());
			}
			result = tipoCarroceriaDao.salvar(tipoCarroceria);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return result;
	}

	private List<String> validarTipoCarroceria(TipoCarroceria tipoCarroceria) {
		List<String> erros = new ArrayList<String>();
		if (tipoCarroceria.getNome() == null || tipoCarroceria.getNome().trim().length() == 0) {
			erros.add("Nome do tipo de carroceria é obrigatório");
		}
		return erros;
	}

	public List<TipoCarroceria> listar() throws AppException {
		List<TipoCarroceria> result = new ArrayList<TipoCarroceria>();
		try {
			result = tipoCarroceriaDao.listar();
		} catch(Exception e) {
			throw new AppException("Erro ao listar tipos de veículo: " + e.getMessage(), e);
		}
		return result;
	}

	public TipoCarroceria recuperar(Long id) throws AppException {
		TipoCarroceria result = null;
		try {
			result = tipoCarroceriaDao.recuperar(id);
		} catch(Exception e) {
			throw new AppException("Erro ao recuperar tipo de carroceria: " + e.getMessage(), e);
		}
		return result;
	}

	public Listagem<TipoCarroceria> listarOrdenadoPorNome(int pagina, int tamanhoPagina)	
			throws AppException { 

		Listagem<TipoCarroceria> listagem = new Listagem<TipoCarroceria>();

		List<TipoCarroceria> lista = new ArrayList<TipoCarroceria>();
		if (pagina == 0) {
			pagina = 1;
		}
		if (tamanhoPagina == 0) {
			tamanhoPagina = 10;
		}
		try {
			lista = tipoCarroceriaDao.listarOrdenadoPorNome(pagina, tamanhoPagina);
			Long count = tipoCarroceriaDao.contar();
			listagem.set(pagina, lista, count);
		} catch(Exception e) {
			throw new AppException("Erro ao listar tipos de veículo: " + e.getMessage(), e);
		}
		return listagem;
	}


	public Listagem<TipoCarroceria> listarPorNomeOrdenadoPorNome(int pagina, int tamanhoPagina, String iniciandoPor)	
			throws AppException { 

		Listagem<TipoCarroceria> listagem = new Listagem<TipoCarroceria>();

		List<TipoCarroceria> lista = new ArrayList<TipoCarroceria>();
		if (iniciandoPor == null || iniciandoPor.trim() == "") {
			throw new AppException("Nome ou parte do nome do tipo de carroceria para pesquisa é obrigatório");
		}
		if (pagina == 0) {
			pagina = 1;
		}
		if (tamanhoPagina == 0) {
			tamanhoPagina = 10;
		}
		try {
			lista = tipoCarroceriaDao.listarPorNomeOrdenadoPorNome(pagina, tamanhoPagina, iniciandoPor);
			Long count = tipoCarroceriaDao.contarPorNome(iniciandoPor);
			listagem.set(pagina, lista, count);
		} catch(Exception e) {
			throw new AppException("Erro ao listar tipos de carroceria por nome: " + e.getMessage(), e);
		}
		return listagem;
	}

}
