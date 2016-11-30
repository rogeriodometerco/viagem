package modelo;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Viagem {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private Conta transportador;
	
	@ManyToOne
	private Conta motorista;
	
	@ManyToOne
	private Veiculo veiculo;
	
	@OneToMany(mappedBy="viagem", fetch=FetchType.EAGER)
	private Set<EtapaEntrega> etapas;
	
	@OneToMany(mappedBy="viagem", fetch=FetchType.EAGER)
	private Set<PontoViagem> pontos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Conta getTransportador() {
		return transportador;
	}

	public void setTransportador(Conta transportador) {
		this.transportador = transportador;
	}

	public Conta getMotorista() {
		return motorista;
	}

	public void setMotorista(Conta motorista) {
		this.motorista = motorista;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Set<EtapaEntrega> getEtapas() {
		return etapas;
	}

	public void setEtapas(Set<EtapaEntrega> etapas) {
		this.etapas = etapas;
	}

	public Set<PontoViagem> getPontos() {
		return pontos;
	}

	public void setPontos(Set<PontoViagem> pontos) {
		this.pontos = pontos;
	}
	
	
}
