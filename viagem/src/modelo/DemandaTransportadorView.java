package modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import enums.StatusDemandaTransporte;

@Table(name="demanda_transportador_view")
@Entity
public class DemandaTransportadorView implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="transportador_demanda_id")
	private Long transportadorDemandaAutorizadoId;

	@Column(name="transportador_id")
	private Long transportadorId;
	
	@Column(name="transportador_ativo")
	private Boolean transportadorAtivo;
	
	@Column(name="transportador_nome")
	private String transportadorNome;

	@Column(name="demanda_id")
	private Long demandaId;

	@Column(name="origem_id")
	private Long origemId;
	
	@Column(name="origem_nome")
	private String origemNome;
	
	@Column(name="origem_municipio_id")
	private Long origemMunicipioId;
	
	@Column(name="origem_municipio_nome")
	private String origemMunicipioNome;
	
	@Column(name="origem_uf_abreviatura")
	private String origemUfAbreviatura;
	
	@Column(name="destino_id")
	private Long destinoId;
	
	@Column(name="destino_nome")
	private String destinoNome;
	
	@Column(name="destino_municipio_id")
	private Long destinoMunicipioId;
	
	@Column(name="destino_municipio_nome")
	private String destinoMunicipioNome;
	
	@Column(name="destino_uf_abreviatura")
	private String destinoUfAbreviatura;
	
	@Column(name="produto_id")
	private Long produtoId;
	
	@Column(name="produto_nome")
	private String produtoNome;
	
	@Column(name="quantidade")
	private Integer quantidade;
	
	@Column(name="unidadequantidade_id")
	private Long unidadeQuantidadeId;
	
	@Column(name="unidadequantidade_abreviacao")
	private String unidadeQuantidadeAbreviacao;
	
	@Column(name="status")
	private Integer status;
	
	@Transient
	private String statusDescricao;

	@Column(name="tomador_id")
	private Long tomadorId;
	
	@Column(name="tomador_nome")
	private String tomadorNome;
	
	@Column(name="cargas_realizadas")
	private Long cargasRealizadas;
	
	@Column(name="cargas_pendentes")
	private Long cargasPendentes;
	
	@Column(name="cargas_transito")
	private Long cargasTransito;
	
	@Column(name="quantidade_embarcada")
	private Long quantidadeEmbarcada;

	public Long getTransportadorDemandaAutorizadoId() {
		return transportadorDemandaAutorizadoId;
	}

	public void setTransportadorDemandaAutorizadoId(Long transportadorDemandaAutorizadoId) {
		this.transportadorDemandaAutorizadoId = transportadorDemandaAutorizadoId;
	}

	public Long getTransportadorId() {
		return transportadorId;
	}

	public void setTransportadorId(Long transportadorId) {
		this.transportadorId = transportadorId;
	}

	public Boolean getTransportadorAtivo() {
		return transportadorAtivo;
	}

	public void setTransportadorAtivo(Boolean transportadorAtivo) {
		this.transportadorAtivo = transportadorAtivo;
	}

	public String getTransportadorNome() {
		return transportadorNome;
	}

	public void setTransportadorNome(String transportadorNome) {
		this.transportadorNome = transportadorNome;
	}

	public Long getDemandaId() {
		return demandaId;
	}

	public void setDemandaId(Long id) {
		this.demandaId = id;
	}

	public Long getOrigemId() {
		return origemId;
	}

	public void setOrigemId(Long origemId) {
		this.origemId = origemId;
	}

	public String getOrigemNome() {
		return origemNome;
	}

	public void setOrigemNome(String origemNome) {
		this.origemNome = origemNome;
	}

	public Long getOrigemMunicipioId() {
		return origemMunicipioId;
	}

	public void setOrigemMunicipioId(Long origemMunicipioId) {
		this.origemMunicipioId = origemMunicipioId;
	}

	public String getOrigemMunicipioNome() {
		return origemMunicipioNome;
	}

	public void setOrigemMunicipioNome(String origemMunicipioNome) {
		this.origemMunicipioNome = origemMunicipioNome;
	}

	public String getOrigemUfAbreviatura() {
		return origemUfAbreviatura;
	}

	public void setOrigemUfAbreviatura(String origemUfAbreviatura) {
		this.origemUfAbreviatura = origemUfAbreviatura;
	}

	public Long getDestinoId() {
		return destinoId;
	}

	public void setDestinoId(Long destinoId) {
		this.destinoId = destinoId;
	}

	public String getDestinoNome() {
		return destinoNome;
	}

	public void setDestinoNome(String destinoNome) {
		this.destinoNome = destinoNome;
	}

	public Long getDestinoMunicipioId() {
		return destinoMunicipioId;
	}

	public void setDestinoMunicipioId(Long destinoMunicipioId) {
		this.destinoMunicipioId = destinoMunicipioId;
	}

	public String getDestinoMunicipioNome() {
		return destinoMunicipioNome;
	}

	public void setDestinoMunicipioNome(String destinoMunicipioNome) {
		this.destinoMunicipioNome = destinoMunicipioNome;
	}

	public String getDestinoUfAbreviatura() {
		return destinoUfAbreviatura;
	}

	public void setDestinoUfAbreviatura(String destinoUfAbreviatura) {
		this.destinoUfAbreviatura = destinoUfAbreviatura;
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public String getProdutoNome() {
		return produtoNome;
	}

	public void setProdutoNome(String produtoNome) {
		this.produtoNome = produtoNome;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Long getUnidadeQuantidadeId() {
		return unidadeQuantidadeId;
	}

	public void setUnidadeQuantidadeId(Long unidadeQuantidadeId) {
		this.unidadeQuantidadeId = unidadeQuantidadeId;
	}

	public String getUnidadeQuantidadeAbreviacao() {
		return unidadeQuantidadeAbreviacao;
	}

	public void setUnidadeQuantidadeAbreviacao(String unidadeQuantidadeAbreviacao) {
		this.unidadeQuantidadeAbreviacao = unidadeQuantidadeAbreviacao;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@PostLoad
	private void setStatusDescricao() {
		this.statusDescricao = StatusDemandaTransporte.values()[this.status].getDescricao();
	}
	
	public String getStatusDescricao() {
		return statusDescricao;
	}

	public Long getTomadorId() {
		return tomadorId;
	}

	public void setTomadorId(Long tomadorId) {
		this.tomadorId = tomadorId;
	}

	public String getTomadorNome() {
		return tomadorNome;
	}

	public void setTomadorNome(String tomadorNome) {
		this.tomadorNome = tomadorNome;
	}

	public Long getCargasRealizadas() {
		return cargasRealizadas;
	}

	public void setCargasRealizadas(Long cargasRealizadas) {
		this.cargasRealizadas = cargasRealizadas;
	}

	public Long getCargasPendentes() {
		return cargasPendentes;
	}

	public void setCargasPendentes(Long cargasPendentes) {
		this.cargasPendentes = cargasPendentes;
	}

	public Long getCargasTransito() {
		return cargasTransito;
	}

	public void setCargasTransito(Long cargasTransito) {
		this.cargasTransito = cargasTransito;
	}

	public Long getQuantidadeEmbarcada() {
		return quantidadeEmbarcada;
	}

	public void setQuantidadeEmbarcada(Long quantidadeEmbarcada) {
		this.quantidadeEmbarcada = quantidadeEmbarcada;
	}
	
	
}