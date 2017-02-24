package servico;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.MunicipioDao;
import exception.AppException;
import modelo.Municipio;
import modelo.UF;

@Stateless
public class MunicipioService {

	@EJB
	private MunicipioDao municipioDao;
	
	public Municipio salvar(Municipio municipio) throws AppException {
		Municipio result = null;
		try {
			List<String> erros = validarMunicipio(municipio);
			if (erros.size() > 0) {
				throw new AppException(erros.toString());
			}
			result = municipioDao.salvar(municipio);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return result;
	}

	private List<String> validarMunicipio(Municipio município) {
		List<String> erros = new ArrayList<String>();
		if (município.getNome() == null || município.getNome().trim().length() == 0) {
			erros.add("Nome do município é obrigatório");
		}
		if (município.getUf() == null) {
			erros.add("UF do município é obrigatório");
		}
		return erros;
	}

	public List<Municipio> listar() throws AppException {
		List<Municipio> result = new ArrayList<Municipio>();
		try {
			result = municipioDao.listar();
		} catch(Exception e) {
			throw new AppException("Erro ao listar município: " + e.getMessage(), e);
		}
		return result;
	}

	public void excluir(Long id) throws AppException {
		try {
			municipioDao.excluir(
					municipioDao.recuperar(id));
		} catch(Exception e) {
			throw new AppException("Erro ao excluir município: " + e.getMessage(), e);
		}
	}

	public List<Municipio> listarPorNome(String chave, int rows) throws AppException {
		List<Municipio> result = new ArrayList<Municipio>();
		try {
			result = municipioDao.listarPorNome(chave, rows);
		} catch(Exception e) {
			throw new AppException("Erro ao listar municípios por nome: " + e.getMessage(), e);
		}
		return result;
	}

	public Municipio recuperar(Long id) throws AppException {
		Municipio result = null;
		try {
			result = municipioDao.recuperar(id);
		} catch(Exception e) {
			throw new AppException("Erro ao recuperar município: " + e.getMessage(), e);
		}
		return result;
	}

	public List<Municipio> listarPorNomeOrdenadoPorNome(String chave, int pagina, int tamanhoPagina) 
			throws AppException {
		
		List<Municipio> result = new ArrayList<Municipio>();
		if (chave == null || chave.trim() == "") {
			throw new AppException("Parâmetro para pesquisa é obrigatório");
		}
		if (pagina == 0) {
			pagina = 1;
		}
		if (tamanhoPagina == 0) {
			tamanhoPagina = 10;
		}
		try {
			result = municipioDao.listarPorNome(chave, tamanhoPagina);
		} catch(Exception e) {
			throw new AppException("Erro ao listar municípios por nome: " + e.getMessage(), e);
		}
		return result;
	}

}
