package modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import enums.StatusViagem;

@Entity
public class Viagem {

	private Long id;
	
	@ManyToOne
	private Transportador transportador;
	
	@ManyToOne
	private Motorista motorista;
	
	@OneToMany(mappedBy="viagem")
	private List<PontoViagem> pontos;
	
	private StatusViagem status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Transportador getTransportador() {
		return transportador;
	}

	public void setTransportador(Transportador transportador) {
		this.transportador = transportador;
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	public List<PontoViagem> getPontos() {
		return pontos;
	}

	public void setPontos(List<PontoViagem> pontos) {
		this.pontos = pontos;
	}

	public StatusViagem getStatus() {
		return status;
	}

	public void setStatus(StatusViagem status) {
		this.status = status;
	}
	
}
