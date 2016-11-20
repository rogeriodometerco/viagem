package servico;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.ContaDao;
import exception.AppException;
import modelo.Conta;
import modelo.UF;

@Stateless
public class ContaService {

	@EJB
	private ContaDao contaDao;
	
	public Conta salvar(Conta conta) throws AppException {
		Conta result = null;
		try {
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
			erros.add("Nome da conta deve ter no m�nimo 5 caracteres");
		}
		if (conta.getPerfis() == null || conta.getPerfis().isEmpty()) {
			erros.add("Conta deve ter no m�nimo um perfil");
		}
		return erros;
	}


}
