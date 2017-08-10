package managedbean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.SelectEvent;

import modelo.Conta;
import servico.SessaoService;
import util.JsfUtil;

@ManagedBean
@SessionScoped
public class SessaoMb implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private SessaoService sessaoService;

	public Boolean isAdministrador() {
		// TODO
		return true;
	}

	public void contaSelecionada(SelectEvent event) {
		sessaoService.setConta((Conta)event.getObject());
	}

	public void setConta(Conta conta) {
		sessaoService.setConta(conta);
	}

	public Conta getConta() {
		try {
			return sessaoService.getConta();
		} catch (Exception e) {
			JsfUtil.addMsgErro("Erro ao recuperar conta do usu�rio: " + e.getMessage());
		}
		return null;
	}

}
