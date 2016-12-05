package modelo;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import enums.StatusEntrega;

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
	
	@OneToMany(mappedBy="entrega", fetch=FetchType.EAGER)
	private Set<EtapaEntrega> etapas;
	
	private String produto;
	
	private Integer quantidade;
	
	private String unidadeQuantidade;
	
	private StatusEntrega status;
	

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

	public DemandaTransporte getDemanda() {
		return demanda;
	}

	public void setDemanda(DemandaTransporte demanda) {
		this.demanda = demanda;
	}

	public Set<EtapaEntrega> getEtapas() {
		return etapas;
	}

	public void setEtapas(Set<EtapaEntrega> etapas) {
		this.etapas = etapas;
	}

	public StatusEntrega getStatus() {
		return status;
	}

	public void setStatus(StatusEntrega status) {
		this.status = status;
	}

}
