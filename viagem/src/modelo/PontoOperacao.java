package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name="PontoOperacao")
@Entity
public class PontoOperacao {

	@Id
	private Long id;
	private Long estabelecimento_id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getEstabelecimento_id() {
		return estabelecimento_id;
	}
	public void setEstabelecimento_id(Long estabelecimento_id) {
		this.estabelecimento_id = estabelecimento_id;
	}
	
}
	
	
	
	
	
/*	@Id
	@ManyToOne
	private PontoViagem pontoViagem;
	
	@ManyToOne
	private Estabelecimento estabelecimento;
	
	private TipoOperacaoViagem tipo;
	
	private StatusOperacaoViagem status;
	
	private Long contagem;
	
	public PontoViagem getPontoViagem() {
		return pontoViagem;
	}

	public void setPontoViagem(PontoViagem pontoViagem) {
		this.pontoViagem = pontoViagem;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public TipoOperacaoViagem getTipo() {
		return tipo;
	}

	public void setTipo(TipoOperacaoViagem tipo) {
		this.tipo = tipo;
	}

	public StatusOperacaoViagem getStatus() {
		return status;
	}

	public void setStatus(StatusOperacaoViagem status) {
		this.status = status;
	}

	public Long getContagem() {
		return contagem;
	}

	public void setContagem(Long contagem) {
		this.contagem = contagem;
	}

	public PontoOperacao() {
		// TODO Auto-generated constructor stub
	}

*/
