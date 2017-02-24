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

	private List<String> validarMunicipio(Municipio munic�pio) {
		List<String> erros = new ArrayList<String>();
		if (munic�pio.getNome() == null || munic�pio.getNome().trim().length() == 0) {
			erros.add("Nome do munic�pio � obrigat�rio");
		}
		if (munic�pio.getUf() == null) {
			erros.add("UF do munic�pio � obrigat�rio");
		}
		return erros;
	}

	public List<Municipio> listar() throws AppException {
		List<Municipio> result = new ArrayList<Municipio>();
		try {
			result = municipioDao.listar();
		} catch(Exception e) {
			throw new AppException("Erro ao listar munic�pio: " + e.getMessage(), e);
		}
		return result;
	}

	public void excluir(Long id) throws AppException {
		try {
			municipioDao.excluir(
					municipioDao.recuperar(id));
		} catch(Exception e) {
			throw new AppException("Erro ao excluir munic�pio: " + e.getMessage(), e);
		}
	}

	public List<Municipio> listarPorNome(String chave, int rows) throws AppException {
		List<Municipio> result = new ArrayList<Municipio>();
		try {
			result = municipioDao.listarPorNome(chave, rows);
		} catch(Exception e) {
			throw new AppException("Erro ao listar munic�pios por nome: " + e.getMessage(), e);
		}
		return result;
	}

	public Municipio recuperar(Long id) throws AppException {
		Municipio result = null;
		try {
			result = municipioDao.recuperar(id);
		} catch(Exception e) {
			throw new AppException("Erro ao recuperar munic�pio: " + e.getMessage(), e);
		}
		return result;
	}

	public List<Municipio> listarPorNomeOrdenadoPorNome(String chave, int pagina, int tamanhoPagina) 
			throws AppException {
		
		List<Municipio> result = new ArrayList<Municipio>();
		if (chave == null || chave.trim() == "") {
			throw new AppException("Par�metro para pesquisa � obrigat�rio");
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
			throw new AppException("Erro ao listar munic�pios por nome: " + e.getMessage(), e);
		}
		return result;
	}

}
