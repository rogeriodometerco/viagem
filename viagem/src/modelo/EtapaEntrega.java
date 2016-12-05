package modelo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import enums.StatusEtapaEntrega;

@Entity
public class EtapaEntrega {
	
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(cascade=CascadeType.ALL)
	private Entrega entrega;
	
	@ManyToOne
	private Estabelecimento origem;
	
	@ManyToOne
	private Estabelecimento destino;
	
	@ManyToOne
	private Viagem viagem;
	
	private StatusEtapaEntrega status;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Entrega getEntrega() {
		return entrega;
	}

	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
	}

	public Viagem getViagem() {
		return viagem;
	}

	public void setViagem(Viagem viagem) {
		this.viagem = viagem;
	}

	public StatusEtapaEntrega getStatus() {
		return status;
	}

	public void setStatus(StatusEtapaEntrega status) {
		this.status = status;
	}

}
