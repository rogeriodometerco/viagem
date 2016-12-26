package managedbean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.EventoDao;
import estatistica.Bkp_FiltroOperacaoViagem;
import modelo.OperacaoViagem;
import servico.EventoService;
import util.JsfUtil;

@ManagedBean
@ViewScoped
public class TesteEventoMb implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private EventoService eventoService;
	@EJB
	private EventoDao eventoDao;
	private Bkp_FiltroOperacaoViagem filtro = new Bkp_FiltroOperacaoViagem();

	public void testar() {
		try {
			List<OperacaoViagem> operacoes = eventoDao.listarOperacoesProgramadas(new Date());
			System.out.println("recuperados: " + operacoes.size());
			for (OperacaoViagem operacao: operacoes) {
				System.out.println(operacao.getId() + " " + operacao.getDataHoraStatus());
			}
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}

	public void listarTerminoOperacoes() {
		try {
			List<OperacaoViagem> operacoes = eventoDao.listarTerminoOperacoes(new Date());
			filtro = new Bkp_FiltroOperacaoViagem(operacoes);
			System.out.println("recuperados: " + operacoes.size());
			for (OperacaoViagem operacao: operacoes) {
				System.out.println(operacao.getId() + " " + operacao.getDataHoraStatus());
			}
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
	}
	
	public Bkp_FiltroOperacaoViagem getFiltro() {
		return filtro;
	}
}
