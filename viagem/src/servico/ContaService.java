package servico;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.ContaDao;
import dto.Listagem;
import enums.Perfil;
import exception.AppException;
import modelo.Conta;

@Stateless
public class ContaService {

	@EJB
	private ContaDao contaDao;
	
	public Conta salvar(Conta conta) throws AppException {
		Conta result = null;
		try {
			if (conta.getId() == null) {
				conta.setAtiva(true);
			}
			List<String> erros = validarConta(conta);
			if (erros.size() > 0) {
				throw new AppException(erros.toString());
			}
			result = contaDao.salvar(conta);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return result;
	}

	public void inativar(Long id) throws AppException {
		Conta result = null;
		try {
			result = contaDao.recuperar(id);
			result.setAtiva(false);
			result = contaDao.salvar(result);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	public List<Conta> listar() throws AppException {
		List<Conta> result = new ArrayList<Conta>();
		try {
			result = contaDao.listar();
		} catch(Exception e) {
			throw new AppException("Erro ao listar contas: " + e.getMessage(), e);
		}
		return result;
	}

	public List<Conta> listarPorNome(String chave, int rows) throws AppException {
		List<Conta> result = new ArrayList<Conta>();
		try {
			result = contaDao.listarPorNome(chave, rows);
		} catch(Exception e) {
			throw new AppException("Erro ao listar contas por nome: " + e.getMessage(), e);
		}
		return result;
	}

	public Conta recuperar(Long id) throws AppException {
		Conta result = null;
		try {
			result = contaDao.recuperar(id);
		} catch(Exception e) {
			throw new AppException("Erro ao recuperar conta: " + e.getMessage(), e);
		}
		return result;
	}
	
	private List<String> validarConta(Conta conta) {
		List<String> erros = new ArrayList<String>();
		if (conta.getNome() == null || conta.getNome().trim().length() < 5) {
			erros.add("Nome da conta deve ter no mínimo 5 caracteres");
		}
		if (conta.getPerfis() == null || conta.getPerfis().isEmpty()) {
			erros.add("Conta deve ter no mínimo um perfil");
		}
		return erros;
	}

	public Listagem<Conta> listarOrdenadoPorNome(int pagina, int tamanhoPagina)	
			throws AppException { 

		Listagem<Conta> listagem = new Listagem<Conta>();

		List<Conta> lista = new ArrayList<Conta>();
		if (pagina == 0) {
			pagina = 1;
		}
		if (tamanhoPagina == 0) {
			tamanhoPagina = 10;
		}
		try {
			lista = contaDao.listarOrdenadoPorNome(pagina, tamanhoPagina);
			Long count = contaDao.contar();
			listagem.set(pagina, lista, count);
		} catch(Exception e) {
			throw new AppException("Erro ao listar contas: " + e.getMessage(), e);
		}
		return listagem;
	}


	public Listagem<Conta> listarPorNomeOrdenadoPorNome(int pagina, int tamanhoPagina, String contendo)	
			throws AppException { 

		Listagem<Conta> listagem = new Listagem<Conta>();

		List<Conta> lista = new ArrayList<Conta>();
		if (contendo == null || contendo.trim() == "") {
			throw new AppException("Nome ou parte do nome da conta para pesquisa é obrigatório");
		}
		if (pagina == 0) {
			pagina = 1;
		}
		if (tamanhoPagina == 0) {
			tamanhoPagina = 10;
		}
		try {
			lista = contaDao.listarPorNomeOrdenadoPorNome(pagina, tamanhoPagina, contendo);
			Long count = contaDao.contarPorNome(contendo);
			listagem.set(pagina, lista, count);
		} catch(Exception e) {
			throw new AppException("Erro ao listar contas por nome: " + e.getMessage(), e);
		}
		return listagem;
	}

	public Listagem<Conta> listarPorPerfilOrdenadoPorNome(int pagina, int tamanhoPagina, Perfil perfil)	
			throws AppException { 

		Listagem<Conta> listagem = new Listagem<Conta>();

		List<Conta> lista = new ArrayList<Conta>();
		if (perfil == null) {
			throw new AppException("Perfil para pesquisa é obrigatório");
		}
		if (pagina == 0) {
			pagina = 1;
		}
		if (tamanhoPagina == 0) {
			tamanhoPagina = 10;
		}
		try {
			lista = contaDao.listarPorPerfilOrdenadoPorNome(pagina, tamanhoPagina, perfil);
			Long count = contaDao.contarPorPerfil(perfil);
			listagem.set(pagina, lista, count);
		} catch(Exception e) {
			throw new AppException("Erro ao listar contas por perfil: " + e.getMessage(), e);
		}
		return listagem;
	}

	public Listagem<Conta> listarPorNomePerfilOrdenadoPorNome(int pagina, int tamanhoPagina, String contendo, Perfil perfil)	
			throws AppException { 

		Listagem<Conta> listagem = new Listagem<Conta>();

		List<Conta> lista = new ArrayList<Conta>();
		if (contendo == null || contendo.trim() == "") {
			throw new AppException("Nome ou parte do nome da conta para pesquisa é obrigatório");
		}
		if (perfil == null) {
			throw new AppException("Perfil para pesquisa é obrigatório");
		}
		if (pagina == 0) {
			pagina = 1;
		}
		if (tamanhoPagina == 0) {
			tamanhoPagina = 10;
		}
		try {
			lista = contaDao.listarPorNomePerfilOrdenadoPorNome(pagina, tamanhoPagina, contendo, perfil);
			Long count = contaDao.contarPorNomePerfil(contendo, perfil);
			listagem.set(pagina, lista, count);
		} catch(Exception e) {
			throw new AppException("Erro ao listar contas por nome e perfil: " + e.getMessage(), e);
		}
		return listagem;
	}

}
