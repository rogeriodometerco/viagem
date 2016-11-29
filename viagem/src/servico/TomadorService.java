package servico;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.ContaDao;
import enums.Perfil;
import exception.AppException;
import modelo.Conta;

@Stateless
public class TomadorService {

	@EJB
	private ContaDao contaDao;
	
	public List<Conta> listarPorNome(String chave, int rows) throws AppException {
		List<Conta> result = new ArrayList<Conta>();
		try {
			result = contaDao.listarPorNome(Perfil.TOMADOR, chave, rows);
		} catch(Exception e) {
			throw new AppException("Erro ao listar tomadores por nome: " + e.getMessage(), e);
		}
		return result;
	}
	
}
