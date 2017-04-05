package modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import enums.StatusDemandaTransporte;

@Entity
public class DemandaTransporte {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Estabelecimento origem;

	@ManyToOne
	private Estabelecimento destino;

	@ManyToOne
	private Produto produto;

	private Integer quantidade;

	@ManyToOne
	private UnidadeQuantidade unidadeQuantidade;

	private StatusDemandaTransporte status;

	@ManyToOne
	private Conta tomador;

	@OneToMany(mappedBy="demanda", fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
	private Set<TransportadorDemandaAutorizado> transportadores;

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

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public UnidadeQuantidade getUnidadeQuantidade() {
		return unidadeQuantidade;
	}

	public void setUnidadeQuantidade(UnidadeQuantidade unidadeQuantidade) {
		this.unidadeQuantidade = unidadeQuantidade;
	}

	public StatusDemandaTransporte getStatus() {
		return status;
	}

	public void setStatus(StatusDemandaTransporte status) {
		this.status = status;
	}

	public Conta getTomador() {
		return tomador;
	}

	public void setTomador(Conta tomador) {
		this.tomador = tomador;
	}

	public Set<TransportadorDemandaAutorizado> getTransportadores() {
		return transportadores;
	}

	public void setTransportadores(Set<TransportadorDemandaAutorizado> transportadores) {
		this.transportadores = transportadores;
	}

	public void adicionarTransportador(Conta transportador) {
		if (this.transportadores == null) {
			this.transportadores = new HashSet<TransportadorDemandaAutorizado>();
		}
		boolean ativo = false;
		for (TransportadorDemandaAutorizado pesquisa: transportadores) {
			if (pesquisa.getAtivo() && pesquisa.getTransportador().getId().equals(transportador.getId())) {
				ativo = true;
				break;
			}
		}
		if (!ativo) {
			TransportadorDemandaAutorizado novo = new TransportadorDemandaAutorizado();
			novo.setDemanda(this);
			novo.setTransportador(transportador);
			novo.setAtivo(true);
			this.transportadores.add(novo);
		}
	}

	/*
	 * Marca transportadores autorizados como inativos.
	 */
	public void inativarTransportador(TransportadorDemandaAutorizado transportador) {
		for (TransportadorDemandaAutorizado ativo: transportadores) {
			if (transportador.getId().equals(ativo.getId())) {
				ativo.setAtivo(false);
				break;
			}
		}
	}
}
