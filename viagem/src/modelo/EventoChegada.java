package modelo;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "CHEGADA")
public class EventoChegada extends Evento {

	@ManyToOne
	private PontoViagem pontoViagem;
	
	private Date dataHoraChegada;

	public PontoViagem getPontoViagem() {
		return pontoViagem;
	}

	public void setPontoViagem(PontoViagem pontoViagem) {
		this.pontoViagem = pontoViagem;
	}

	public Date getDataHoraChegada() {
		return dataHoraChegada;
	}

	public void setDataHoraChegada(Date dataHoraChegada) {
		this.dataHoraChegada = dataHoraChegada;
	}
}

