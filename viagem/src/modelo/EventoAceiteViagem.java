package modelo;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "ACEITE_VIAGEM")
public class EventoAceiteViagem extends Evento {

	@ManyToOne
	private Viagem viagem;
	
	// TODO Avaliar se este atributo está redundante em relação a data de registro do evento.
	private Date dataHoraAceite;

	public Viagem getViagem() {
		return viagem;
	}

	public void setViagem(Viagem viagem) {
		this.viagem = viagem;
	}

	public Date getDataHoraAceite() {
		return dataHoraAceite;
	}

	public void setDataHoraAceite(Date dataHoraAceite) {
		this.dataHoraAceite = dataHoraAceite;
	}

}

