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
	
	@ManyToOne
	private Estabelecimento estabelecimento;
	
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

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public StatusPontoViagem getStatus() {
		return status;
	}

	public void setStatus(StatusPontoViagem status) {
		this.status = status;
	}
}
