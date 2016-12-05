package modelo;

import java.util.Date;

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
	
	private Date dataChegadaAcordada;
	
	private Integer ordem;
	
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

	public Date getDataChegadaAcordada() {
		return dataChegadaAcordada;
	}

	public void setDataChegadaAcordada(Date dataChegadaAcordada) {
		this.dataChegadaAcordada = dataChegadaAcordada;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

}
