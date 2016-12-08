package modelo;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import enums.TipoTerminoOperacao;

@Entity
@DiscriminatorValue(value = "TERMINO_OPERACAO")
public class EventoTerminoOperacao extends Evento {

	@ManyToOne
	private OperacaoEtapa operacao;
	
	private Date dataHoraTermino;
	
	private TipoTerminoOperacao tipoTermino;

	public OperacaoEtapa getOperacaoEtapa() {
		return operacao;
	}

	public void setOperacaoEtapa(OperacaoEtapa operacao) {
		this.operacao = operacao;
	}

	public Date getDataHoraTermino() {
		return dataHoraTermino;
	}

	public void setDataHoraTermino(Date dataHoraTermino) {
		this.dataHoraTermino = dataHoraTermino;
	}

	public TipoTerminoOperacao getTipoTermino() {
		return tipoTermino;
	}

	public void setTipoTermino(TipoTerminoOperacao tipoTermino) {
		this.tipoTermino = tipoTermino;
	}
}

