package modelo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import enums.StatusOperacaoViagem;
import enums.TipoOperacaoViagem;

@Entity
public class OperacaoViagem {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private PontoViagem pontoViagem;
	
	@ManyToOne
	private EtapaEntrega etapaEntrega;
	
	private TipoOperacaoViagem tipo;
	
	private StatusOperacaoViagem status;

	private Date dataHoraStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PontoViagem getPontoViagem() {
		return pontoViagem;
	}

	public void setPontoViagem(PontoViagem pontoViagem) {
		this.pontoViagem = pontoViagem;
	}

	public EtapaEntrega getEtapaEntrega() {
		return etapaEntrega;
	}

	public void setEtapaEntrega(EtapaEntrega etapaEntrega) {
		this.etapaEntrega = etapaEntrega;
	}
	
	public StatusOperacaoViagem getStatus() {
		return status;
	}

	public void setStatus(StatusOperacaoViagem status) {
		this.status = status;
	}

	public Date getDataHoraStatus() {
		return dataHoraStatus;
	}

	public void setDataHoraStatus(Date dataHoraStatus) {
		this.dataHoraStatus = dataHoraStatus;
	}

	public TipoOperacaoViagem getTipo() {
		return tipo;
	}

	public void setTipo(TipoOperacaoViagem tipo) {
		this.tipo = tipo;
	}
}
