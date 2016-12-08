package teste;

import java.util.Date;

import dao.ViagemDao;
import modelo.EventoInicioViagem;
import modelo.Viagem;
import servico.EventoService;
import servico.ProcessadorEventoInicioViagem;
import util.Ejb;

public class TesteEvento {

	public static void main(String[] args) throws Exception {
		TesteEvento testeEvento = new TesteEvento();
		testeEvento.testar();
	}

	public void testar() {
		try {
			EventoService servico = Ejb.lookup(EventoService.class);
			ViagemDao viagemDao = Ejb.lookup(ViagemDao.class);
			Viagem viagem = viagemDao.listar().get(0);
			EventoInicioViagem evento = new EventoInicioViagem();
			evento.setViagem(viagem);
			evento.setDataHoraInicio(new Date());
			evento.setDataHoraRegistro(new Date());
			servico.registrarEvento(evento);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
