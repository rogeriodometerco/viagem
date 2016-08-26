package servico;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.LocalizacaoMotoristaDao;
import exception.AppException;
import modelo.Localizacao;
import modelo.LocalizacaoMotorista;

@Stateless
public class LocalizacaoMotoristaService {

	@EJB
	private LocalizacaoMotoristaDao localizacaoMotoristaDao;

	public void localizacaoRegistrada(Localizacao localizacao) 
			throws AppException {

		try {
			LocalizacaoMotorista localizacaoMotorista = localizacaoMotoristaDao
					.recuperar(localizacao.getMotorista());
			
			if (localizacaoMotorista == null 
					|| localizacao.getData().after(localizacaoMotorista.getData())) {
				
				if (localizacaoMotorista == null) {
					localizacaoMotorista = new LocalizacaoMotorista();
					localizacaoMotorista.setMotorista(localizacao.getMotorista());
				}
				localizacaoMotorista.setData(localizacao.getData());
				localizacaoMotorista.setLat(localizacao.getLat());
				localizacaoMotorista.setLng(localizacao.getLng());
				localizacaoMotorista.setVelocidade(localizacao.getVelocidade());
				
				localizacaoMotoristaDao.salvar(localizacaoMotorista);
			}
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
	
	public List<LocalizacaoMotorista> listar() throws AppException {
		try {
			return localizacaoMotoristaDao.listar();
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
}
