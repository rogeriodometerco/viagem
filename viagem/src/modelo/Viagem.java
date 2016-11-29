package modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Viagem {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private Conta transportador;
	
	@ManyToOne
	private Conta motorista;
	
	private String veiculo;
	
	
}
