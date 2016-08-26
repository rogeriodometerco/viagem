package managedbean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import modelo.LocalizacaoMotorista;
import servico.LocalizacaoMotoristaService;

@ManagedBean
@ViewScoped
public class LocalizacaoMotoristaMb {

	@EJB
	private LocalizacaoMotoristaService localizacaoMotoristaService;

	private List<LocalizacaoMotorista> localizacoes;

	public List<LocalizacaoMotorista> getLocalizacoes() throws Exception {
		if (localizacoes == null) {
			localizacoes = localizacaoMotoristaService.listar();
		}
		return localizacoes;
	}


}
