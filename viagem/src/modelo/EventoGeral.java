package modelo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import enums.OrigemEvento;
import enums.TipoEvento;

@Entity
public class EventoGeral {

	@Id
	@GeneratedValue
	private Long id;

	private OrigemEvento origem;

	@ManyToOne
	private Localizacao localizacao;
	
	private TipoEvento tipo;

	private Date data;
	
	@ManyToOne
	private Viagem viagem;
	
	@ManyToOne
	private PontoViagem pontoViagem;

	private Date previsao;

	private String mensagem;
	
	@ManyToOne
	private Dispositivo dispositivo;
	
	@ManyToOne
	private Motorista motorista;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrigemEvento getOrigem() {
		return origem;
	}

	public void setOrigem(OrigemEvento origem) {
		this.origem = origem;
	}

	public Localizacao getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(Localizacao localizacao) {
		this.localizacao = localizacao;
	}

	public TipoEvento getTipo() {
		return tipo;
	}

	public void setTipo(TipoEvento tipo) {
		this.tipo = tipo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Viagem getViagem() {
		return viagem;
	}

	public void setViagem(Viagem viagem) {
		this.viagem = viagem;
	}

	public PontoViagem getPontoViagem() {
		return pontoViagem;
	}

	public void setPontoViagem(PontoViagem pontoViagem) {
		this.pontoViagem = pontoViagem;
	}

	public Date getPrevisao() {
		return previsao;
	}

	public void setPrevisao(Date previsao) {
		this.previsao = previsao;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Dispositivo getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}
	
	
}
