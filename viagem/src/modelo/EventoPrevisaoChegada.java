package modelo;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "PREVISAO_CHEGADA")
public class EventoPrevisaoChegada extends Evento {

	@ManyToOne
	private PontoViagem pontoViagem;
	
	private Date dataHoraPrevista;

	public PontoViagem getPontoViagem() {
		return pontoViagem;
	}

	public void setPontoViagem(PontoViagem pontoViagem) {
		this.pontoViagem = pontoViagem;
	}

	public Date getDataHoraPrevista() {
		return dataHoraPrevista;
	}

	public void setDataHoraPrevista(Date dataHoraPrevista) {
		this.dataHoraPrevista = dataHoraPrevista;
	}

}

