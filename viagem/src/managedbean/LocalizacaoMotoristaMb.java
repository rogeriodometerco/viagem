package managedbean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import modelo.LocalizacaoMotorista;
import servico.LocalizacaoMotoristaService;

@ManagedBean
@ViewScoped
public class LocalizacaoMotoristaMb {

	@EJB
	private LocalizacaoMotoristaService localizacaoMotoristaService;
	private MapModel mapModel;
	private List<LocalizacaoMotorista> localizacoes;

	public List<LocalizacaoMotorista> getLocalizacoes() throws Exception {
		return localizacoes;
	}

	public String getNome() {
		return "rogerio";
	}
	public void listar() throws Exception {
		localizacoes = localizacaoMotoristaService.listar();
	}

	public MapModel getMapModel() {
		if (mapModel == null) {
			mapModel = new DefaultMapModel();
		}
		if (localizacoes != null) {
			mapModel.getMarkers().clear();
			for (LocalizacaoMotorista localizacao: localizacoes) {
				LatLng latLng = new LatLng(localizacao.getLat(), localizacao.getLng());
				mapModel.addOverlay(new Marker(latLng, localizacao.getMotorista().getNome()));
			}
		}
		return mapModel;
	}

}
