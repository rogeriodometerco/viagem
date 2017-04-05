package servico;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.UltimaLocalizacaoMotoristaDao;
import exception.AppException;
import modelo.Conta;
import modelo.UltimaLocalizacaoMotorista;

@Stateless
public class UltimaLocalizacaoMotoristaService {

	@EJB
	private UltimaLocalizacaoMotoristaDao ultimaLocalizacaoMotoristaDao;

	
	public UltimaLocalizacaoMotorista salvar(UltimaLocalizacaoMotorista ultimaLocalizacao) throws AppException {
		UltimaLocalizacaoMotorista result = null;
		try {
			result = ultimaLocalizacaoMotoristaDao.salvar(ultimaLocalizacao);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return result;
	}

	public UltimaLocalizacaoMotorista recuperar(Conta motorista) throws AppException {
		UltimaLocalizacaoMotorista result = null;
		try {
			result = ultimaLocalizacaoMotoristaDao.recuperar(motorista);
		} catch (Exception e) {
			throw new AppException(e);
		}
		return result;
	}
	
	public List<UltimaLocalizacaoMotorista> listar() throws AppException {
		try {
			return ultimaLocalizacaoMotoristaDao.listar();
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
}
