package modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Entrega {
	
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private DemandaTransporte demanda;
	
	@ManyToOne
	private Estabelecimento origem;
	
	@ManyToOne
	private Estabelecimento destino;
	
	private String produto;
	
	private Integer quantidade;
	
	private String unidadeQuantidade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Estabelecimento getOrigem() {
		return origem;
	}

	public void setOrigem(Estabelecimento origem) {
		this.origem = origem;
	}

	public Estabelecimento getDestino() {
		return destino;
	}

	public void setDestino(Estabelecimento destino) {
		this.destino = destino;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public String getUnidadeQuantidade() {
		return unidadeQuantidade;
	}

	public void setUnidadeQuantidade(String unidadeQuantidade) {
		this.unidadeQuantidade = unidadeQuantidade;
	}

}
