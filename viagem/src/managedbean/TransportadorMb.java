package managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import modelo.Conta;
import servico.TransportadorService;
import util.JsfUtil;

@ManagedBean
@ViewScoped
public class TransportadorMb implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private TransportadorService transportadorService;

	public List<Conta> autocomplete(String query) {
		List<Conta> result = new ArrayList<Conta>();
		try {
			result = transportadorService.listarPorNome(query, 10);
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
		return result;
	}

}