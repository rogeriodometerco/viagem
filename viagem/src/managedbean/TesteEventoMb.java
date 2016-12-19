package managedbean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.EventoDao;
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

}
