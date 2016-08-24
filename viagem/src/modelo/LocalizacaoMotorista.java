package modelo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class LocalizacaoMotorista {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private Motorista motorista;
	
	private Date data;
	
	private Double lat;
	
	private Double lng;
	
	private Double velocidade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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

	public Double getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(Double velocidade) {
		this.velocidade = velocidade;
	}
}
