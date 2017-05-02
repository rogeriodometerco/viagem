package managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.DemandaTomadorViewCatalogo;
import dto.Filtro;
import enums.StatusDemandaTransporte;
import modelo.DemandaTomadorView;
import util.JsfUtil;

@ManagedBean
@ViewScoped
public class DemandaTomadorMb implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private DemandaTomadorViewCatalogo catalogo;
	private List<DemandaTomadorView> lista;
	
	@PostConstruct
	private void inicializar() {
		listar();
	}

	public void listar() {
		try {
			Filtro filtro = new Filtro();
			//filtro.igual(DemandaTomadorViewCatalogo.CAMPO_ID, 99l);
			filtro.igual(DemandaTomadorViewCatalogo.CAMPO_STATUS, StatusDemandaTransporte.FINALIZADA.ordinal());
			this.lista = catalogo.recuperarLista(filtro);
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}
	
	public List<DemandaTomadorView> getLista() {
		return lista;
	}

	
}
