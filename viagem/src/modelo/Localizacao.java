package modelo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Localizacao {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private Dispositivo dispositivo;
	
	@ManyToOne
	private Motorista motorista;
	
	private Date data;
	
	private Double lat;
	
	private Double lng;
	
	private Double velocidade;
}
