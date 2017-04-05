package managedbean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import modelo.UltimaLocalizacaoMotorista;
import servico.UltimaLocalizacaoMotoristaService;

@ManagedBean
@ViewScoped
public class LocalizacaoMotoristaMb {

	@EJB
	private UltimaLocalizacaoMotoristaService ultimaLocalizacaoMotoristaService;
	private MapModel mapModel;
	private List<UltimaLocalizacaoMotorista> localizacoes;

	public List<UltimaLocalizacaoMotorista> getLocalizacoes() throws Exception {
		return localizacoes;
	}

	public String getNome() {
		return "rogerio";
	}
	public void listar() throws Exception {
		localizacoes = ultimaLocalizacaoMotoristaService.listar();
	}

	public MapModel getMapModel() {
		if (mapModel == null) {
			mapModel = new DefaultMapModel();
		}
		if (localizacoes != null) {
			mapModel.getMarkers().clear();
			for (UltimaLocalizacaoMotorista localizacao: localizacoes) {
				LatLng latLng = new LatLng(localizacao.getLatitude(), localizacao.getLongitude());
				mapModel.addOverlay(new Marker(latLng, localizacao.getMotorista().getNome()));
			}
		}
		return mapModel;
	}

}
