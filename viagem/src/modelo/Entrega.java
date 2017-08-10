package modelo;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import dto.StatusEntregaDecorator;
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
	
	@ManyToOne
	private Produto produto;
	
	private Integer quantidade;
	
	@ManyToOne
	private UnidadeQuantidade unidadeQuantidade;
	
	private StatusEntrega status;
	
	private Date dataHoraStatus;

	@Transient
	private StatusEntregaDecorator statusDecorator;
	
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

	public Date getDataHoraStatus() {
		return dataHoraStatus;
	}

	public void setDataHoraStatus(Date dataHoraStatus) {
		this.dataHoraStatus = dataHoraStatus;
	}
	
	// Por enquanto, apenas uma etapa por entrega.
	// Método criado para simplificar a obtenção da etapa da entrega.
	public EtapaEntrega getEtapa() {
		return etapas.iterator().next();
	}
	
	public StatusEntregaDecorator getStatusDecorator() {
		if (this.statusDecorator == null) {
			this.statusDecorator = new StatusEntregaDecorator(this);
		}
		return statusDecorator;
	}
	
	public Date getDataHoraColeta() {
		Date dataHora = null;
		if (getEtapa().getOperacaoColeta().realizada()) {
			dataHora = getEtapa().getOperacaoColeta().getDataHoraStatus();
		}
		return dataHora;
	}
	
	public Date getDataHoraEntrega() {
		Date dataHora = null;
		if (getEtapa().getOperacaoEntrega().realizada()) {
			dataHora = getEtapa().getOperacaoEntrega().getDataHoraStatus();
		}
		return dataHora;
	}
}
