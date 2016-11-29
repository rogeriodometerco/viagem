package modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TransportadorDemandaAutorizado {
	
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private DemandaTransporte demanda;
	
	@ManyToOne
	private Conta transportador;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DemandaTransporte getDemanda() {
		return demanda;
	}

	public void setDemanda(DemandaTransporte demanda) {
		this.demanda = demanda;
	}

	public Conta getTransportador() {
		return transportador;
	}

	public void setTransportador(Conta transportador) {
		this.transportador = transportador;
	}
	
}
