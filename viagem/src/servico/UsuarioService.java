package servico;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.AdminContaDao;
import dao.UsuarioContaDao;
import dao.UsuarioDao;
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
			
			// Vincula o usuário a conta.
			UsuarioConta usuarioConta = new UsuarioConta();
			usuarioConta.setUsuario(result);
			usuarioConta.setConta(conta);
			usuarioContaDao.salvar(usuarioConta);
			
			// Torna o usuário administrador da conta se ele é o primeiro a ser cadastrado na conta.
			if (adminContaDao.listarPorConta(conta).isEmpty()) {
				AdminConta adminConta = new AdminConta();
				adminConta.setUsuario(result);
				adminConta.setConta(conta);
				adminContaDao.salvar(adminConta);
			}
		} catch (Exception e) {
			throw new AppException("Erro ao criar usuário: " + e.getMessage(), e);
		}
		return result;
	}
	
	private List<String> validarUsuario(Usuario usuario, String confirmacaoSenha, Crud crud) throws Exception {
		List<String> erros = new ArrayList<String>();
		if (usuario.getId() != null && crud.equals(Crud.INCLUSAO)) {
			throw new AppException("Usuário a ser criado não pode ter um identificador do banco de dados");
		}
		if (usuario.getNome() == null || usuario.getLogin().trim().length() < 5) {
			erros.add("Nome do usuário deve ter no mínimo 5 caracteres");
		}
		if (usuario.getLogin() == null || usuario.getLogin().trim().length() < 5) {
			erros.add("Login do usuário deve ter no mínimo 5 caracteres");
		}
		if (usuario.getSenha() == null || usuario.getSenha().trim().length() < 5) {
			erros.add("Senha do usuário deve ter no mínimo 5 caracteres");
		}
		
		// Verifica se login já existe para evitar duplicação de usuário.
		if (Crud.INCLUSAO.equals(crud)) {
			
			if (confirmacaoSenha == null || confirmacaoSenha.trim().length() == 0) {
				erros.add("Confirmação da senha deve ser informada");
			} else {
				if (!usuario.getSenha().equals(confirmacaoSenha)) {
					erros.add("Confirmação da senha não bate com a senha informada");
				}
				if (!usuario.getSenha().equals(usuario.getLogin())) {
					erros.add("Senha deve ser igual ao login na criação do usuário");
				}
			}
			Usuario usuarioPesquisa = usuarioDao.recuperarPeloLoginSeExistir(usuario.getLogin());
			if (usuarioPesquisa != null && !usuarioPesquisa.getId().equals(usuario.getId())) {
				erros.add("Já existe um usuário com este login");
			}
		}
		return erros;
	}
	
	public List<Usuario> listar() throws AppException {
		List<Usuario> result = new ArrayList<Usuario>();
		try {
			result = usuarioDao.listar();
		} catch(Exception e) {
			throw new AppException("Erro ao listar usuários: " + e.getMessage(), e);
		}
		return result;
	}
	
	public List<Usuario> listarPorNome(String chave, int rows) throws AppException {
		List<Usuario> result = new ArrayList<Usuario>();
		try {
			result = usuarioDao.listarPorNome(chave, rows);
		} catch(Exception e) {
			throw new AppException("Erro ao listar usuários por nome: " + e.getMessage(), e);
		}
		return result;
	}
	
}


