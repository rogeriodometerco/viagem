package modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import enums.StatusPontoViagem;

@Entity
public class PontoViagem {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private Viagem viagem;
	
	private Double lat;
	
	private Double lng;
	
	private String descricao;

	private StatusPontoViagem status;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Viagem getViagem() {
		return viagem;
	}

	public void setViagem(Viagem viagem) {
		this.viagem = viagem;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public StatusPontoViagem getStatus() {
		return status;
	}

	public void setStatus(StatusPontoViagem status) {
		this.status = status;
	}
	
	
}
