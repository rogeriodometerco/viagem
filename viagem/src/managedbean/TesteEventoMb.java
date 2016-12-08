package managedbean;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.ViagemDao;
import modelo.EventoInicioViagem;
import modelo.Viagem;
import servico.EventoService;
import util.Ejb;
import util.JsfUtil;

@ManagedBean
@ViewScoped
public class TesteEventoMb implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private EventoService eventoService;

	public void testar() {
		try {
			ViagemDao viagemDao = Ejb.lookup(ViagemDao.class);
			Viagem viagem = viagemDao.listar().get(0);
			EventoInicioViagem evento = new EventoInicioViagem();
			evento.setViagem(viagem);
			evento.setDataHoraInicio(new Date());
			evento.setDataHoraRegistro(new Date());
			eventoService.registrarEvento(evento);
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}

}
