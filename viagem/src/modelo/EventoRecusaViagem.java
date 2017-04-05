package modelo;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "RECUSA_VIAGEM")
public class EventoRecusaViagem extends Evento {

	@ManyToOne
	private Viagem viagem;
	
	private Date dataHoraRecusa;

	public Viagem getViagem() {
		return viagem;
	}

	public void setViagem(Viagem viagem) {
		this.viagem = viagem;
	}

	public Date getDataHoraRecusa() {
		return dataHoraRecusa;
	}

	public void setDataHoraRecusa(Date dataHoraRecusa) {
		this.dataHoraRecusa = dataHoraRecusa;
	}

}

