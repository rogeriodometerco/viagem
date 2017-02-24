package servico;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.MunicipioDao;
import exception.AppException;
import modelo.Municipio;
import modelo.UF;
import util.Listagem;

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

	public void excluir(Long id) throws AppException {
		try {
			municipioDao.excluir(
					municipioDao.recuperar(id));
		} catch(Exception e) {
			throw new AppException("Erro ao excluir município: " + e.getMessage(), e);
		}
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
			throw new AppException("Erro ao listar municípios: " + e.getMessage(), e);
		}
		return listagem;
	}

	
	public Listagem<Municipio> listarPorNomeOrdenadoPorNome(String iniciandoPor, int pagina, int tamanhoPagina)	
			throws AppException { 
		
		Listagem<Municipio> listagem = new Listagem<Municipio>();
		
		List<Municipio> lista = new ArrayList<Municipio>();
		if (iniciandoPor == null || iniciandoPor.trim() == "") {
			throw new AppException("Nome ou parte do nome do município para pesquisa é obrigatório");
		}
		if (pagina == 0) {
			pagina = 1;
		}
		if (tamanhoPagina == 0) {
			tamanhoPagina = 10;
		}
		try {
			lista = municipioDao.listarPorNomeOrdenadoPorNome(iniciandoPor, pagina, tamanhoPagina);
			Long count = municipioDao.contarPorNome(iniciandoPor);
			listagem.set(pagina, lista, count);
		} catch(Exception e) {
			throw new AppException("Erro ao listar municípios por nome: " + e.getMessage(), e);
		}
		return listagem;
	}

}
