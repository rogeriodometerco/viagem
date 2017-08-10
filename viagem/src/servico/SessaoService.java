package servico;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.PrePassivate;
import javax.ejb.Stateful;

import dao.ContaDao;
import dao.UsuarioDao;
import exception.AppException;
import modelo.Conta;
import modelo.Usuario;

@Stateful
public class SessaoService {

	@EJB
	private ContaDao contaDao;
	@EJB
	private UsuarioDao usuarioDao;
	
	private static Conta conta;
	private Usuario usuario;
	
	public Conta getConta() throws AppException {
		// TODO
		if (conta == null) {
			try {
				conta = contaDao.listar().get(0);
			} catch (Exception e) {
				throw new AppException(e);
			}
		}
		return conta;
	}
	
	public Usuario getUsuario() throws AppException {
		// TODO
		if (usuario == null) {
			try {
				usuario = usuarioDao.listar().get(0);
			} catch (Exception e) {
				throw new AppException(e);
			}
		}
		return usuario;
	}
	
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
	@PrePassivate
	public void prePassivate() {
		System.out.println("prePassivate() " + this);
	}
	
	@PreDestroy
	public void preDestroy() {
		System.out.println("preDestroy() " + this);
	}
	
}
