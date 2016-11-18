package servico;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.UsuarioDao;
import enums.Crud;
import exception.AppException;
import modelo.Usuario;

@Stateless
public class UsuarioService {

	@EJB
	private UsuarioDao usuarioDao;
	
	public Usuario criar(Usuario usuario, String confirmacaoSenha) throws AppException {
		// Este m�todo s� deve receber usu�rios para cria��o.
		if (usuario.getId() != null) {
			throw new AppException("Usu�rio a ser criado n�o pode ter um identificador do banco de dados");
		}
		Usuario result = null;
		try {
			List<String> erros = validarUsuario(usuario, confirmacaoSenha, Crud.INCLUSAO);
			if (erros.size() > 0) {
				throw new AppException(erros.toString());
			}
			result = usuarioDao.salvar(usuario);
		} catch (Exception e) {
			throw new AppException("Erro ao criar usu�rio: " + e.getMessage(), e);
		}
		return result;
	}
	
	private List<String> validarUsuario(Usuario usuario, String confirmacaoSenha, Crud crud) throws Exception {
		List<String> erros = new ArrayList<String>();
		if (usuario.getNome() == null || usuario.getLogin().length() < 5) {
			erros.add("Nome do usu�rio deve ter no m�nimo 5 caracteres");
		}
		if (usuario.getLogin() == null || usuario.getLogin().length() < 5) {
			erros.add("Login do usu�rio deve ter no m�nimo 5 caracteres");
		}
		if (usuario.getSenha() == null || usuario.getSenha().length() < 5) {
			erros.add("Senha do usu�rio deve ter no m�nimo 5 caracteres");
		}
		
		// Verifica se login j� existe para evitar duplica��o de usu�rio.
		if (Crud.INCLUSAO.equals(crud)) {
			
			if (confirmacaoSenha == null || confirmacaoSenha.length() == 0) {
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
}


