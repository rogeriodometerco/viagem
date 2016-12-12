package modelo;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "CRIACAO_VIAGEM")
public class EventoCriacaoViagem extends Evento {

	@ManyToOne
	private Viagem viagem;
	
	// TODO Avaliar se este atributo está redundante em relação a data de registro do evento.
	private Date dataHoraCriacao;

	public Viagem getViagem() {
		return viagem;
	}

	public void setViagem(Viagem viagem) {
		this.viagem = viagem;
	}

	public Date getDataHoraCriacao() {
		return dataHoraCriacao;
	}

	public void setDataHoraCriacao(Date dataHoraCriacao) {
		this.dataHoraCriacao = dataHoraCriacao;
	}
}

