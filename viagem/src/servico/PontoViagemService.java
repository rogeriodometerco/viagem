package servico;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.PontoViagemDao;
import exception.AppException;
import modelo.PontoViagem;

@Stateless
public class PontoViagemService {

	@EJB
	private PontoViagemDao pontoViagemDao;
	
	public PontoViagem recuperar(Long id) throws AppException {
		PontoViagem result = null;
		try {
			result = pontoViagemDao.recuperar(id);
		} catch(Exception e) {
			throw new AppException("Erro ao recuperar ponto da viagem: " + e.getMessage(), e);
		}
		return result;
	}

}
