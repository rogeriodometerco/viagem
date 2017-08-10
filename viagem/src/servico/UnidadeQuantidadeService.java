package servico;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.UnidadeQuantidadeDao;
import dto.Listagem;
import exception.AppException;
import modelo.UnidadeQuantidade;

@Stateless
public class UnidadeQuantidadeService {

	@EJB
	private UnidadeQuantidadeDao unidadeQuantidadeDao;
	
	public UnidadeQuantidade salvar(UnidadeQuantidade unidadeQuantidade) throws AppException {
		UnidadeQuantidade result = null;
		try {
			List<String> erros = validarUnidadeQuantidade(unidadeQuantidade);
			if (erros.size() > 0) {
				throw new AppException(erros.toString());
			}
			result = unidadeQuantidadeDao.salvar(unidadeQuantidade);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return result;
	}

	private List<String> validarUnidadeQuantidade(UnidadeQuantidade unidadeQuantidade) {
		List<String> erros = new ArrayList<String>();
		if (unidadeQuantidade.getNome() == null || unidadeQuantidade.getNome().trim().length() == 0) {
			erros.add("Nome da unidade de quantidade é obrigatório");
		}
		if (unidadeQuantidade.getAbreviacao() == null || unidadeQuantidade.getAbreviacao().trim().length() == 0) {
			erros.add("Abreviação da unidade de quantidade é obrigatório");
		}
		return erros;
	}

	public UnidadeQuantidade recuperar(Long id) throws AppException {
		UnidadeQuantidade result = null;
		try {
			result = unidadeQuantidadeDao.recuperar(id);
		} catch(Exception e) {
			throw new AppException("Erro ao recuperar unidade de quantidade: " + e.getMessage(), e);
		}
		return result;
	}
	
	public void excluir(Long id) throws AppException {
		try {
			unidadeQuantidadeDao.excluir(
					unidadeQuantidadeDao.recuperar(id));
		} catch(Exception e) {
			throw new AppException("Erro ao excluir unidade de quantidade: " + e.getMessage(), e);
		}
	}

	public UnidadeQuantidade recuperarPelaAbreviacaoSeExistir(String abreviatura) throws AppException {
		UnidadeQuantidade result = null;
		try {
			result = unidadeQuantidadeDao.recuperarPelaAbreviacaoSeExistir(abreviatura);
		} catch(Exception e) {
			throw new AppException("Erro ao recuperar unidade de quantidade: " + e.getMessage(), e);
		}
		return result;
	}
	
	public Listagem<UnidadeQuantidade> listarOrdenadoPorAbreviacao(int pagina, int tamanhoPagina) throws AppException {
		Listagem<UnidadeQuantidade> listagem = new Listagem<UnidadeQuantidade>();
		try {
			List<UnidadeQuantidade> lista = unidadeQuantidadeDao.listarOrdenadoPorAbreviacao(pagina, tamanhoPagina);
			Long count = unidadeQuantidadeDao.contar();
			listagem.set(pagina, lista, count);
		} catch (Exception e) {
			throw new AppException("Erro ao listar unidades de quantidade: " + e.getMessage(), e);
		}
		return listagem;
	}
	
	public List<UnidadeQuantidade> listar() throws AppException {
		List<UnidadeQuantidade> result = new ArrayList<UnidadeQuantidade>();
		try {
			result = unidadeQuantidadeDao.listar();
		} catch(Exception e) {
			throw new AppException("Erro ao listar unidades de quantidade: " + e.getMessage(), e);
		}
		return result;
	}

	public List<UnidadeQuantidade> listarPorNomeOuAbreviacaoOrdenadoPorAbreviacao(
			int pagina, int tamanhoPagina, String chave) throws AppException {

		List<UnidadeQuantidade> result = new ArrayList<UnidadeQuantidade>();
		try {
			result = unidadeQuantidadeDao.listarPorNomeOuAbreviacaoOrdenadoPorAbreviacao(pagina, tamanhoPagina, chave);
		} catch(Exception e) {
			throw new AppException("Erro ao listar unidades de quantidade: " + e.getMessage(), e);
		}
		return result;
	}
}
 