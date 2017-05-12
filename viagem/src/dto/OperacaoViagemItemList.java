package dto;

import java.util.Date;

import enums.StatusEntrega;
import enums.StatusOperacaoViagem;
import enums.StatusViagem;
import enums.TipoOperacaoViagem;

public class OperacaoViagemItemList {

	private Long demandaId;
	private String estabelecimentoNome;
	private String estabelecimentoMunicipio;
	private String estabelecimentoUf;
	private TipoOperacaoViagem tipoOperacao;
	private StatusOperacaoViagem statusOperacao;
	private Date dataHoraStatusOperacao;
	private Date dataChegadaAcordada;
	private Date dataHoraChegadaPrevista;
	private Date dataHoraChegada;
	private Date dataHoraSaida;
	private Long viagemId;
	private String placa;
	private String motoristaNome;
	private String transportadorNome;
	private String tomadorNome;
	private String produtoNome;
	private Integer quantidadeOperacao;
	private String unidadeQuantidade;
	private StatusEntrega statusEntrega;
	private StatusViagem statusViagem;
	private String origemNome;
	private String origemMunicipio;
	private String origemUf;
	private String destinoNome;
	private String destinoMunicipio;
	private String destinoUf;
	
	public OperacaoViagemItemList() {

	}

	public Long getDemandaId() {
		return demandaId;
	}

	public void setDemandaId(Long demandaId) {
		this.demandaId = demandaId;
	}

	public String getEstabelecimentoNome() {
		return estabelecimentoNome;
	}

	public void setEstabelecimentoNome(String estabelecimentoNome) {
		this.estabelecimentoNome = estabelecimentoNome;
	}

	public String getEstabelecimentoMunicipio() {
		return estabelecimentoMunicipio;
	}

	public void setEstabelecimentoMunicipio(String estabelecimentoMunicipio) {
		this.estabelecimentoMunicipio = estabelecimentoMunicipio;
	}

	public String getEstabelecimentoUf() {
		return estabelecimentoUf;
	}

	public void setEstabelecimentoUf(String estabelecimentoUf) {
		this.estabelecimentoUf = estabelecimentoUf;
	}

	public TipoOperacaoViagem getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(TipoOperacaoViagem tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public StatusOperacaoViagem getStatusOperacao() {
		return statusOperacao;
	}

	public void setStatusOperacao(StatusOperacaoViagem statusOperacao) {
		this.statusOperacao = statusOperacao;
	}

	public Date getDataHoraStatusOperacao() {
		return dataHoraStatusOperacao;
	}

	public void setDataHoraStatusOperacao(Date dataHoraStatusOperacao) {
		this.dataHoraStatusOperacao = dataHoraStatusOperacao;
	}

	public Date getDataChegadaAcordada() {
		return dataChegadaAcordada;
	}

	public void setDataChegadaAcordada(Date dataChegadaAcordada) {
		this.dataChegadaAcordada = dataChegadaAcordada;
	}

	public Date getDataHoraChegadaPrevista() {
		return dataHoraChegadaPrevista;
	}

	public void setDataHoraChegadaPrevista(Date dataHoraChegadaPrevista) {
		this.dataHoraChegadaPrevista = dataHoraChegadaPrevista;
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

	public Long getViagemId() {
		return viagemId;
	}

	public void setViagemId(Long viagemId) {
		this.viagemId = viagemId;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getMotoristaNome() {
		return motoristaNome;
	}

	public void setMotoristaNome(String motoristaNome) {
		this.motoristaNome = motoristaNome;
	}

	public String getTransportadorNome() {
		return transportadorNome;
	}

	public void setTransportadorNome(String transportadorNome) {
		this.transportadorNome = transportadorNome;
	}

	public String getTomadorNome() {
		return tomadorNome;
	}

	public void setTomadorNome(String tomadorNome) {
		this.tomadorNome = tomadorNome;
	}

	public String getProdutoNome() {
		return produtoNome;
	}

	public void setProdutoNome(String produtoNome) {
		this.produtoNome = produtoNome;
	}

	public Integer getQuantidadeOperacao() {
		return quantidadeOperacao;
	}

	public void setQuantidadeOperacao(Integer quantidadeOperacao) {
		this.quantidadeOperacao = quantidadeOperacao;
	}

	public String getUnidadeQuantidade() {
		return unidadeQuantidade;
	}

	public void setUnidadeQuantidade(String unidadeQuantidade) {
		this.unidadeQuantidade = unidadeQuantidade;
	}

	public StatusEntrega getStatusEntrega() {
		return statusEntrega;
	}

	public void setStatusEntrega(StatusEntrega statusEntrega) {
		this.statusEntrega = statusEntrega;
	}

	public StatusViagem getStatusViagem() {
		return statusViagem;
	}

	public void setStatusViagem(StatusViagem statusViagem) {
		this.statusViagem = statusViagem;
	}

	public String getOrigemNome() {
		return origemNome;
	}

	public void setOrigemNome(String origemNome) {
		this.origemNome = origemNome;
	}

	public String getDestinoNome() {
		return destinoNome;
	}

	public void setDestinoNome(String destinoNome) {
		this.destinoNome = destinoNome;
	}

	public String getOrigemOuDestino() {
		if (this.tipoOperacao.equals(TipoOperacaoViagem.COLETA)) {
			return destinoNome + ", " + destinoMunicipio + " - " + destinoUf;
		} else {
			return origemNome + ", " + origemMunicipio + " - " + origemUf;
		}
	}

	public String getOrigemMunicipio() {
		return origemMunicipio;
	}

	public void setOrigemMunicipio(String origemMunicipio) {
		this.origemMunicipio = origemMunicipio;
	}

	public String getOrigemUf() {
		return origemUf;
	}

	public void setOrigemUf(String origemUf) {
		this.origemUf = origemUf;
	}

	public String getDestinoMunicipio() {
		return destinoMunicipio;
	}

	public void setDestinoMunicipio(String destinoMunicipio) {
		this.destinoMunicipio = destinoMunicipio;
	}

	public String getDestinoUf() {
		return destinoUf;
	}

	public void setDestinoUf(String destinoUf) {
		this.destinoUf = destinoUf;
	}
	
}
