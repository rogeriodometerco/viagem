package dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import enums.StatusOperacaoViagem;
import enums.StatusPontoViagem;
import enums.TipoOperacaoViagem;
import modelo.Conta;
import modelo.Estabelecimento;
import modelo.Veiculo;
import util.DataUtil;

public class OperacaoViagemRequest {
	
	private Conta transportador;
	private Conta tomador;
	private Conta motorista;
	private Veiculo veiculo;
	private Estabelecimento estabelecimento;
	private TipoOperacaoViagem tipoOperacao;
	private StatusOperacaoViagem statusOperacaoViagem;
	private Date dataStatusOperacaoViagemInicial;
	private Date dataStatusOperacaoViagemFinal;
	private StatusPontoViagem statusPontoViagem;
	private Date dataChegadaPrevistaDoVeiculo;
	
	private List<String> colunasSelecao;
	private List<String> colunasAcumulado;
	private Boolean contar;
	private Boolean agrupar;
	

	public OperacaoViagemRequest() {
		colunasSelecao = new ArrayList<String>();
		colunasAcumulado = new ArrayList<String>();
	}


	public Conta getMotorista() {
		return motorista;
	}

	public void setMotorista(Conta motorista) {
		this.motorista = motorista;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public Conta getTransportador() {
		return transportador;
	}

	public void setTransportador(Conta transportador) {
		this.transportador = transportador;
	}

	public Conta getTomador() {
		return tomador;
	}

	public void setTomador(Conta tomador) {
		this.tomador = tomador;
	}

	public Date getDataStatusOperacaoViagemInicial() {
		return dataStatusOperacaoViagemInicial;
	}

	public void setDataStatusOperacaoViagemInicial(Date dataStatusOperacaoViagemInicial) {
		this.dataStatusOperacaoViagemInicial = dataStatusOperacaoViagemInicial;
	}

	public Date getDataStatusOperacaoViagemFinal() {
		return dataStatusOperacaoViagemFinal;
	}

	public void setDataStatusOperacaoViagemFinal(Date dataStatusOperacaoViagemFinal) {
		this.dataStatusOperacaoViagemFinal = dataStatusOperacaoViagemFinal;
	}

	public TipoOperacaoViagem getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(TipoOperacaoViagem tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public StatusOperacaoViagem getStatusOperacaoViagem() {
		return statusOperacaoViagem;
	}

	public void setStatusOperacaoViagem(StatusOperacaoViagem statusOperacaoViagem) {
		this.statusOperacaoViagem = statusOperacaoViagem;
	}

	public StatusPontoViagem getStatusPontoViagem() {
		return statusPontoViagem;
	}

	public void setStatusPontoViagem(StatusPontoViagem statusPontoViagem) {
		this.statusPontoViagem = statusPontoViagem;
	}

	public Date getDataChegadaPrevistaDoVeiculo() {
		return dataChegadaPrevistaDoVeiculo;
	}

	public void setDataChegadaPrevistaDoVeiculo(Date dataChegadaPrevistaDoVeiculo) {
		this.dataChegadaPrevistaDoVeiculo = dataChegadaPrevistaDoVeiculo;
	}
	
	public void agruparEstabelecimento() {
		this.colunasSelecao.add("PontoViagem.estabelecimento.id");
		this.colunasSelecao.add("PontoViagem.estabelecimento.nome");
	}
	
	public Boolean getAgrupar() {
		return agrupar;
	}

	public void setAgrupar(Boolean agrupar) {
		this.agrupar = agrupar;
	}


	public List<String> getColunasSelecao() {
		return this.colunasSelecao;
	}


	public void cargasPendentes() {
		this.statusOperacaoViagem = StatusOperacaoViagem.PENDENTE;
	}
	
	public void cargasRealizadas(Date data) {
		this.statusOperacaoViagem = StatusOperacaoViagem.REALIZADA;
		this.dataStatusOperacaoViagemInicial = DataUtil.extrairDataSemHora(data);
		this.dataStatusOperacaoViagemFinal = DataUtil.somarDias(this.dataStatusOperacaoViagemInicial, 1);
	}
	
	public void operacoesPendentesComVeiculoNoLocal() {
		this.statusOperacaoViagem = StatusOperacaoViagem.PENDENTE;
		this.statusPontoViagem = StatusPontoViagem.NO_LOCAL;
	}



	
}
