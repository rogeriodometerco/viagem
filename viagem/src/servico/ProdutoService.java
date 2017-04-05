package servico;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.ProdutoDao;
import dto.Listagem;
import exception.AppException;
import modelo.Produto;

@Stateless
public class ProdutoService {

	@EJB
	private ProdutoDao produtoDao;
	
	public Produto salvar(Produto produto) throws AppException {
		Produto result = null;
		try {
			List<String> erros = validarProduto(produto);
			if (erros.size() > 0) {
				throw new AppException(erros.toString());
			}
			result = produtoDao.salvar(produto);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return result;
	}

	private List<String> validarProduto(Produto produto) {
		List<String> erros = new ArrayList<String>();
		if (produto.getNome() == null || produto.getNome().trim().length() == 0) {
			erros.add("Nome do produto é obrigatório");
		}
		return erros;
	}

	public List<Produto> listar() throws AppException {
		List<Produto> result = new ArrayList<Produto>();
		try {
			result = produtoDao.listar();
		} catch(Exception e) {
			throw new AppException("Erro ao listar produtos: " + e.getMessage(), e);
		}
		return result;
	}

	public Produto recuperar(Long id) throws AppException {
		Produto result = null;
		try {
			result = produtoDao.recuperar(id);
		} catch(Exception e) {
			throw new AppException("Erro ao recuperar produto: " + e.getMessage(), e);
		}
		return result;
	}

	public Listagem<Produto> listarOrdenadoPorNome(int pagina, int tamanhoPagina)	
			throws AppException { 

		Listagem<Produto> listagem = new Listagem<Produto>();

		List<Produto> lista = new ArrayList<Produto>();
		if (pagina == 0) {
			pagina = 1;
		}
		if (tamanhoPagina == 0) {
			tamanhoPagina = 10;
		}
		try {
			lista = produtoDao.listarOrdenadoPorNome(pagina, tamanhoPagina);
			Long count = produtoDao.contar();
			listagem.set(pagina, lista, count);
		} catch(Exception e) {
			throw new AppException("Erro ao listar produtos: " + e.getMessage(), e);
		}
		return listagem;
	}


	public Listagem<Produto> listarPorNomeOrdenadoPorNome(int pagina, int tamanhoPagina, String iniciandoPor)	
			throws AppException { 

		Listagem<Produto> listagem = new Listagem<Produto>();

		List<Produto> lista = new ArrayList<Produto>();
		if (iniciandoPor == null || iniciandoPor.trim() == "") {
			throw new AppException("Nome ou parte do nome do produto para pesquisa é obrigatório");
		}
		if (pagina == 0) {
			pagina = 1;
		}
		if (tamanhoPagina == 0) {
			tamanhoPagina = 10;
		}
		try {
			lista = produtoDao.listarPorNomeOrdenadoPorNome(pagina, tamanhoPagina, iniciandoPor);
			Long count = produtoDao.contarPorNome(iniciandoPor);
			listagem.set(pagina, lista, count);
		} catch(Exception e) {
			throw new AppException("Erro ao listar produtos por nome: " + e.getMessage(), e);
		}
		return listagem;
	}

}
