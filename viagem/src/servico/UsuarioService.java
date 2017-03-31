package servico;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.AdminContaDao;
import dao.UsuarioContaDao;
import dao.UsuarioDao;
import dto.Listagem;
import enums.Crud;
import exception.AppException;
import modelo.AdminConta;
import modelo.Conta;
import modelo.Usuario;
import modelo.UsuarioConta;

@Stateless
public class UsuarioService {

	@EJB
	private UsuarioDao usuarioDao;
	@EJB
	private UsuarioContaDao usuarioContaDao;
	@EJB
	private SessaoService sessaoService;
	@EJB
	private AdminContaDao adminContaDao;
	
	public Usuario criar(Usuario usuario, String confirmacaoSenha) throws AppException {
		return criar(usuario, confirmacaoSenha, sessaoService.getConta());
	}
	
	public Usuario criar(Usuario usuario, String confirmacaoSenha, Conta conta) throws AppException {
		Usuario result = null;
		try {
			List<String> erros = validarUsuario(usuario, confirmacaoSenha, Crud.INCLUSAO);
			if (erros.size() > 0) {
				throw new AppException(erros.toString());
			}
			result = usuarioDao.salvar(usuario);
			
			// Vincula o usu�rio a conta.
			UsuarioConta usuarioConta = new UsuarioConta();
			usuarioConta.setUsuario(result);
			usuarioConta.setConta(conta);
			usuarioContaDao.salvar(usuarioConta);
			
			// Torna o usu�rio administrador da conta se ele � o primeiro a ser cadastrado na conta.
			if (adminContaDao.listarPorConta(conta).isEmpty()) {
				AdminConta adminConta = new AdminConta();
				adminConta.setUsuario(result);
				adminConta.setConta(conta);
				adminContaDao.salvar(adminConta);
			}
		} catch (Exception e) {
			throw new AppException("Erro ao criar usu�rio: " + e.getMessage(), e);
		}
		return result;
	}
	
	private List<String> validarUsuario(Usuario usuario, String confirmacaoSenha, Crud crud) throws Exception {
		List<String> erros = new ArrayList<String>();
		if (usuario.getId() != null && crud.equals(Crud.INCLUSAO)) {
			throw new AppException("Usu�rio a ser criado n�o pode ter um identificador do banco de dados");
		}
		if (usuario.getNome() == null || usuario.getLogin().trim().length() < 5) {
			erros.add("Nome do usu�rio deve ter no m�nimo 5 caracteres");
		}
		if (usuario.getLogin() == null || usuario.getLogin().trim().length() < 5) {
			erros.add("Login do usu�rio deve ter no m�nimo 5 caracteres");
		}
		if (usuario.getSenha() == null || usuario.getSenha().trim().length() < 5) {
			erros.add("Senha do usu�rio deve ter no m�nimo 5 caracteres");
		}
		
		// Verifica se login j� existe para evitar duplica��o de usu�rio.
		if (Crud.INCLUSAO.equals(crud)) {
			
			if (confirmacaoSenha == null || confirmacaoSenha.trim().length() == 0) {
				erros.add("Confirma��o da senha deve ser informada");
			} else {
				if (!usuario.getSenha().equals(confirmacaoSenha)) {
					erros.add("Confirma��o da senha n�o bate com a senha informada");
				}
				if (!usuario.getSenha().equals(usuario.getLogin())) {
					erros.add("Senha deve ser igual ao login na cria��o do usu�rio");
				}
			}
			Usuario usuarioPesquisa = usuarioDao.recuperarPeloLoginSeExistir(usuario.getLogin());
			if (usuarioPesquisa != null && !usuarioPesquisa.getId().equals(usuario.getId())) {
				erros.add("J� existe um usu�rio com este login");
			}
		}
		return erros;
	}
	
	public List<Usuario> listar() throws AppException {
		List<Usuario> result = new ArrayList<Usuario>();
		try {
			result = usuarioDao.listar();
		} catch(Exception e) {
			throw new AppException("Erro ao listar usu�rios: " + e.getMessage(), e);
		}
		return result;
	}
	
	public List<Usuario> listarPorNome(String chave, int rows) throws AppException {
		List<Usuario> result = new ArrayList<Usuario>();
		try {
			result = usuarioDao.listarPorNome(chave, rows);
		} catch(Exception e) {
			throw new AppException("Erro ao listar usu�rios por nome: " + e.getMessage(), e);
		}
		return result;
	}

	
	public Listagem<Usuario> listarOrdenadoPorNome(int pagina, int tamanhoPagina)	
			throws AppException { 

		Listagem<Usuario> listagem = new Listagem<Usuario>();

		List<Usuario> lista = new ArrayList<Usuario>();
		if (pagina == 0) {
			pagina = 1;
		}
		if (tamanhoPagina == 0) {
			tamanhoPagina = 10;
		}
		try {
			lista = usuarioDao.listarOrdenadoPorNome(pagina, tamanhoPagina);
			Long count = usuarioDao.contar();
			listagem.set(pagina, lista, count);
		} catch(Exception e) {
			throw new AppException("Erro ao listar usuários: " + e.getMessage(), e);
		}
		return listagem;
	}


	public Listagem<Usuario> listarPorNomeOrdenadoPorNome(int pagina, int tamanhoPagina, String contendo)	
			throws AppException { 

		Listagem<Usuario> listagem = new Listagem<Usuario>();

		List<Usuario> lista = new ArrayList<Usuario>();
		if (contendo == null || contendo.trim() == "") {
			throw new AppException("Nome ou parte do nome do usuário para pesquisa é obrigatório");
		}
		if (pagina == 0) {
			pagina = 1;
		}
		if (tamanhoPagina == 0) {
			tamanhoPagina = 10;
		}
		try {
			lista = usuarioDao.listarPorNomeOrdenadoPorNome(pagina, tamanhoPagina, contendo);
			Long count = usuarioDao.contarPorNome(contendo);
			listagem.set(pagina, lista, count);
		} catch(Exception e) {
			throw new AppException("Erro ao listar contas por nome: " + e.getMessage(), e);
		}
		return listagem;
	}

	public Usuario recuperar(Long id) throws AppException {
		try {
			return usuarioDao.recuperar(id);
		} catch (Exception e) {
			throw new AppException("Erro ao recupear usuário: " + e.getMessage(), e);
		}
	}


}


