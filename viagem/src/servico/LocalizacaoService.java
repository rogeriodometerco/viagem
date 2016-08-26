package servico;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.LocalizacaoDao;
import exception.AppException;
import modelo.Localizacao;

@Stateless
public class LocalizacaoService {

	@EJB
	private LocalizacaoDao localizacaoDao;
	@EJB
	private LocalizacaoMotoristaService localizacaoMotoristaService;
	
	public Localizacao registrar(Localizacao localizacao) throws AppException {
		Localizacao result = null;
		try {
			result = localizacaoDao.salvar(localizacao);
			localizacaoMotoristaService.localizacaoRegistrada(result);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return result;
	}
}
