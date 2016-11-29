package servico;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.EstabelecimentoDao;
import exception.AppException;
import modelo.Estabelecimento;

@Stateless
public class EstabelecimentoService {

	@EJB
	private EstabelecimentoDao estabelecimentoDao;
	
	public Estabelecimento salvar(Estabelecimento estabelecimento) throws AppException {
		Estabelecimento result = null;
		try {
			List<String> erros = validarEstabelecimento(estabelecimento);
			if (erros.size() > 0) {
				throw new AppException(erros.toString());
			}
			result = estabelecimentoDao.salvar(estabelecimento);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return result;
	}

	private List<String> validarEstabelecimento(Estabelecimento estabelecimento) {
		List<String> erros = new ArrayList<String>();
		if (estabelecimento.getNome() == null || estabelecimento.getNome().trim().length() == 0) {
			erros.add("Nome do estabelecimento é obrigatório");
		}
		if (estabelecimento.getMunicipio() == null) {
			erros.add("Município do estabelecimento é obrigatório");
		}
		return erros;
	}

	public List<Estabelecimento> listar() throws AppException {
		List<Estabelecimento> result = new ArrayList<Estabelecimento>();
		try {
			result = estabelecimentoDao.listar();
		} catch(Exception e) {
			throw new AppException("Erro ao listar estabelecimentos: " + e.getMessage(), e);
		}
		return result;
	}

	public List<Estabelecimento> listarPorNome(String chave, int rows) throws AppException {
		List<Estabelecimento> result = new ArrayList<Estabelecimento>();
		try {
			result = estabelecimentoDao.listarPorNome(chave, rows);
		} catch(Exception e) {
			throw new AppException("Erro ao listar estabelecimentos por nome: " + e.getMessage(), e);
		}
		return result;
	}

	public Estabelecimento recuperar(Long id) throws AppException {
		Estabelecimento result = null;
		try {
			result = estabelecimentoDao.recuperar(id);
		} catch(Exception e) {
			throw new AppException("Erro ao recuperar estabelecimento: " + e.getMessage(), e);
		}
		return result;
	}

}
