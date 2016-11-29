package modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class EtapaEntrega {
	
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Entrega entrega;
	
	@ManyToOne
	private Estabelecimento origem;
	
	@ManyToOne
	private Estabelecimento destino;
	
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

}
