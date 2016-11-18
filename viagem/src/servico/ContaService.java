package servico;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.ContaDao;
import exception.AppException;
import modelo.Conta;

@Stateless
public class ContaService {

	@EJB
	private ContaDao contaDao;
	
	public Conta salvar(Conta conta) throws AppException {
		Conta result = null;
		try {
			result = contaDao.salvar(conta);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return result;
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

}
