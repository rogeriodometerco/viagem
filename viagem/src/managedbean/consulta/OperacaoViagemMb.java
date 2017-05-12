package managedbean.consulta;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.PontoOperacaoDao;
import dto.Filtro;
import dto.OperacaoViagemItemList;
import modelo.Estabelecimento;
import util.JsfUtil;

@ManagedBean
@ViewScoped
public class OperacaoViagemMb implements Serializable {

	private static final long serialVersionUID = 1L;
	private Estabelecimento estabelecimento;
	private Long demandaId;
	private Estabelecimento origem;
	private Estabelecimento destino;
	
	@EJB
	private PontoOperacaoDao pontoOperacaoDao;
	private List<OperacaoViagemItemList> lista;
	private String sql;
	private Long tempo;
	
	
	public Long getTempo() {
		return tempo;
	}

	public void setTempo(Long tempo) {
		this.tempo = tempo;
	}


	@PostConstruct
	private void inicializar() {
		listar();
	}

	public void listar() {
		try {
			Filtro filtro = new Filtro();
			if (estabelecimento != null) {
				filtro.igual("estabelecimentoId", estabelecimento.getId());
			}
			if (demandaId != null) {
				filtro.igual("demandaId", demandaId);
			}
			if (origem != null) {
				filtro.igual("origemId", origem.getId());
			}
			if (destino != null) {
				filtro.igual("destinoId", destino.getId());
			}
			Long t1 = System.currentTimeMillis();
			this.lista = pontoOperacaoDao.listar(filtro);
			Long t2 = System.currentTimeMillis();
			tempo = t2 - t1;
		} catch (Exception e) {
			JsfUtil.addMsgErro(e.getMessage());
		}
 	}
	
	public List<OperacaoViagemItemList> getLista() {
		return lista;
	}

	public String getSql() {
		return sql;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Long getDemandaId() {
		return demandaId;
	}

	public void setDemandaId(Long demandaId) {
		this.demandaId = demandaId;
	}

	public Estabelecimento getOrigem() {
		return origem;
	}

	public void setOrigem(Estabelecimento origem) {
		this.origem = origem;
	}

	public Estabelecimento getDestino() {
		return destino;
	}

	public void setDestino(Estabelecimento destino) {
		this.destino = destino;
	}
	
}
