package servico;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.UfDao;
import exception.AppException;
import modelo.UF;

@Stateless
public class UfService {

	@EJB
	private UfDao ufDao;
	
	public UF salvar(UF uf) throws AppException {
		UF result = null;
		try {
			List<String> erros = validarUf(uf);
			if (erros.size() > 0) {
				throw new AppException(erros.toString());
			}
			result = ufDao.salvar(uf);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return result;
	}

	private List<String> validarUf(UF uf) {
		List<String> erros = new ArrayList<String>();
		if (uf.getNome() == null || uf.getNome().trim().length() == 0) {
			erros.add("Nome da UF é obrigatório");
		}
		if (uf.getAbreviatura() == null || uf.getAbreviatura().trim().length() == 0) {
			erros.add("Abreviatura da UF é obrigatório");
		}
		return erros;
	}

	public List<UF> listar() throws AppException {
		List<UF> result = new ArrayList<UF>();
		try {
			result = ufDao.listar();
		} catch(Exception e) {
			throw new AppException("Erro ao listar UF's: " + e.getMessage(), e);
		}
		return result;
	}

	public List<UF> listarPorNome(String chave, int rows) throws AppException {
		List<UF> result = new ArrayList<UF>();
		try {
			result = ufDao.listarPorNome(chave, rows);
		} catch(Exception e) {
			throw new AppException("Erro ao listar UF's por nome: " + e.getMessage(), e);
		}
		return result;
	}

	public UF recuperar(Long id) throws AppException {
		UF result = null;
		try {
			result = ufDao.recuperar(id);
		} catch(Exception e) {
			throw new AppException("Erro ao recuperar uf: " + e.getMessage(), e);
		}
		return result;
	}

	public UF recuperarPelaAbreviaturaSeExistir(String abreviatura) throws AppException {
		UF result = null;
		try {
			result = ufDao.recuperarPelaAbreviaturaSeExistir(abreviatura);
		} catch(Exception e) {
			throw new AppException("Erro ao recuperar UF: " + e.getMessage(), e);
		}
		return result;
	}
	
}
