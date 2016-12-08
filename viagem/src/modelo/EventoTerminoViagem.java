package modelo;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "TERMINO_VIAGEM")
public class EventoTerminoViagem extends Evento {

	@ManyToOne
	private Viagem viagem;
	
	private Date dataHoraTermino;

	public Viagem getViagem() {
		return viagem;
	}

	public void setViagem(Viagem viagem) {
		this.viagem = viagem;
	}

	public Date getDataHoraTermino() {
		return dataHoraTermino;
	}

	public void setDataHoraTermino(Date dataHoraTermino) {
		this.dataHoraTermino = dataHoraTermino;
	}
}

