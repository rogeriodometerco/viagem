package servico;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.MunicipioDao;
import dto.Listagem;
import dto.ParametrosListagem;
import exception.AppException;
import modelo.Municipio;

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

	private List<String> validarMunicipio(Municipio municipio) {
		List<String> erros = new ArrayList<String>();
		if (municipio.getNome() == null || municipio.getNome().trim().length() == 0) {
			erros.add("Nome do municipio � obrigat�rio");
		}
		if (municipio.getUf() == null) {
			erros.add("UF do municipio � obrigat�rio");
		}
		return erros;
	}

	public void excluir(Long id) throws AppException {
		try {
			municipioDao.excluir(
					municipioDao.recuperar(id));
		} catch(Exception e) {
			throw new AppException("Erro ao excluir municipio: " + e.getMessage(), e);
		}
	}

	public Municipio recuperar(Long id) throws AppException {
		Municipio result = null;
		try {
			result = municipioDao.recuperar(id);
		} catch(Exception e) {
			throw new AppException("Erro ao recuperar municipio: " + e.getMessage(), e);
		}
		return result;
	}

	public Listagem<Municipio> listarOrdenadoPorNome(int pagina, int tamanhoPagina)	
			throws AppException { 

		Listagem<Municipio> listagem = new Listagem<Municipio>();

		List<Municipio> lista = new ArrayList<Municipio>();
		if (pagina == 0) {
			pagina = 1;
		}
		if (tamanhoPagina == 0) {
			tamanhoPagina = 10;
		}
		try {
			lista = municipioDao.listarOrdenadoPorNome(pagina, tamanhoPagina);
			Long count = municipioDao.contar();
			listagem.set(pagina, lista, count);
		} catch(Exception e) {
			throw new AppException("Erro ao listar municipios: " + e.getMessage(), e);
		}
		return listagem;
	}


	public Listagem<Municipio> listarPorNomeOrdenadoPorNome(int pagina, int tamanhoPagina, String iniciandoPor)	
			throws AppException { 

		Listagem<Municipio> listagem = new Listagem<Municipio>();

		List<Municipio> lista = new ArrayList<Municipio>();
		if (iniciandoPor == null || iniciandoPor.trim() == "") {
			throw new AppException("Nome ou parte do nome do municipio para pesquisa � obrigat�rio");
		}
		if (pagina == 0) {
			pagina = 1;
		}
		if (tamanhoPagina == 0) {
			tamanhoPagina = 10;
		}
		try {
			lista = municipioDao.listarPorNomeOrdenadoPorNome(pagina, tamanhoPagina, iniciandoPor);
			Long count = municipioDao.contarPorNome(iniciandoPor);
			listagem.set(pagina, lista, count);
		} catch(Exception e) {
			throw new AppException("Erro ao listar municípios por nome: " + e.getMessage(), e);
		}
		return listagem;
	}
	
	
/*
	public List<Municipio> listar(ParametrosListagem params)	
			throws AppException {

		try {
			return municipioDao.listar(params);
		} catch(Exception e) {
			throw new AppException("Erro ao listar municipios: " + e.getMessage(), e);
		}
	}
*/
}
