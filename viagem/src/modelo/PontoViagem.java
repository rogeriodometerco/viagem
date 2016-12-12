package modelo;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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

	private Date dataHoraStatus;
	
	private Date dataHoraPrevistaChegada;
	
	private Date dataHoraChegada;
	
	private Date dataHoraSaida;
	
	@OneToMany(mappedBy="pontoViagem", fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
	private Set<OperacaoViagem> operacoes;
	
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

	public Date getDataHoraPrevistaChegada() {
		return dataHoraPrevistaChegada;
	}

	public void setDataHoraPrevistaChegada(Date dataHoraPrevistaChegada) {
		this.dataHoraPrevistaChegada = dataHoraPrevistaChegada;
	}

	public Date getDataHoraChegada() {
		return dataHoraChegada;
	}

	public void setDataHoraChegada(Date dataHoraChegada) {
		this.dataHoraChegada = dataHoraChegada;
	}

	public Date getDataHoraSaida() {
		return dataHoraSaida;
	}

	public void setDataHoraSaida(Date dataHoraSaida) {
		this.dataHoraSaida = dataHoraSaida;
	}

	public Set<OperacaoViagem> getOperacoes() {
		return operacoes;
	}

	public void setOperacoes(Set<OperacaoViagem> operacoes) {
		this.operacoes = operacoes;
	}

	public Date getDataHoraStatus() {
		return dataHoraStatus;
	}

	public void setDataHoraStatus(Date dataHoraStatus) {
		this.dataHoraStatus = dataHoraStatus;
	}

}
