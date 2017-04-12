package servico;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.OperacaoViagemDao;
import exception.AppException;
import modelo.Estabelecimento;
import modelo.OperacaoViagem;

@Stateless
public class OperacaoViagemService {
	
	@EJB
	private OperacaoViagemDao operacaoViagemDao;

	public List<OperacaoViagem> listarPorDataEEstabelecimento(
			Date data, Estabelecimento estabelecimento) throws AppException {
		
		List<OperacaoViagem> result = new ArrayList<OperacaoViagem>();
		try {
			result = operacaoViagemDao.listarPorDataEEstabelecimento(data, estabelecimento);
			System.out.println("result: " + result.size());
		} catch(Exception e) {
			throw new AppException("Erro ao listar operações: " + e.getMessage(), e);
		}
		return result;
	}
}
