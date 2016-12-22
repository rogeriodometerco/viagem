package estatistica;

import java.util.ArrayList;
import java.util.List;

import enums.TipoOperacaoViagem;
import modelo.Conta;
import modelo.Estabelecimento;
import modelo.OperacaoViagem;

public class FiltroOperacaoViagem {

	private List<Estabelecimento> estabelecimentosOpcoes = new ArrayList<Estabelecimento>();
	private List<Estabelecimento> estabelecimentosFiltro = new ArrayList<Estabelecimento>();

	private List<TipoOperacaoViagem> tiposOperacaoOpcoes = new ArrayList<TipoOperacaoViagem>();
	private List<TipoOperacaoViagem> tiposOperacaoFiltro = new ArrayList<TipoOperacaoViagem>();
	
	private List<Conta> transportadoresOpcoes = new ArrayList<Conta>();
	private List<Conta> transportadoresFiltro = new ArrayList<Conta>();
	
	private List<Conta> tomadoresOpcoes = new ArrayList<Conta>();
	private List<Conta> tomadoresFiltro = new ArrayList<Conta>();
	
	private List<OperacaoViagem> lista = new ArrayList<OperacaoViagem>();

	
	public FiltroOperacaoViagem(List<OperacaoViagem> lista) {
		this.lista = lista;
		for (OperacaoViagem operacao: lista) {
			garantirOpcoesDeFiltro(operacao);
		}
	}
	
	public FiltroOperacaoViagem() {
	}

	public List<OperacaoViagem> getLista() {
		System.out.println("getLista()");
		List<OperacaoViagem> result = new ArrayList<OperacaoViagem>();
		for (OperacaoViagem operacao: lista) {
			if (aceita(operacao)) {
				result.add(operacao);
			}
		}
		return result;
	}
	
	public List<Estabelecimento> getEstabelecimentosOpcoes() {
		return estabelecimentosOpcoes;
	}
	public List<Estabelecimento> getEstabelecimentosFiltro() {
		return estabelecimentosFiltro;
	}
	
	public void setEstabelecimentosFiltro(List<Estabelecimento> estabelecimentos) {
		this.estabelecimentosFiltro = estabelecimentos;
	}
	
	private void garantirOpcoesDeFiltro(OperacaoViagem operacao) {
		if (estabelecimentosOpcoes.indexOf(operacao.getPontoViagem().getEstabelecimento()) < 0) {
			estabelecimentosOpcoes.add(operacao.getPontoViagem().getEstabelecimento());
		}
		if (tiposOperacaoOpcoes.indexOf(operacao.getTipo()) < 0) {
			tiposOperacaoOpcoes.add(operacao.getTipo());
		}
		if (transportadoresOpcoes.indexOf(operacao.getPontoViagem().getViagem().getTransportador()) < 0) {
			transportadoresOpcoes.add(operacao.getPontoViagem().getViagem().getTransportador()); 
		}
		if (tomadoresOpcoes.indexOf(operacao.getEtapaEntrega().getEntrega().getDemanda().getTomador()) < 0) {
			tomadoresOpcoes.add(operacao.getEtapaEntrega().getEntrega().getDemanda().getTomador());
		}
	}


	public boolean aceita(OperacaoViagem target) {
		System.out.println("aceita() - " + estabelecimentosFiltro.size());
		if (!estabelecimentosFiltro.isEmpty() && estabelecimentosFiltro.indexOf(target.getPontoViagem().getEstabelecimento()) < 0) {
			return false;
		}
		if (!tiposOperacaoFiltro.isEmpty() && tiposOperacaoFiltro.indexOf(target.getTipo()) < 0) {
			return false;
		}
		if (!transportadoresFiltro.isEmpty() && transportadoresFiltro.indexOf(target.getPontoViagem().getViagem().getTransportador()) < 0) {
			return false;
		}
		if (!tomadoresFiltro.isEmpty() && tomadoresFiltro.indexOf(target.getEtapaEntrega().getEntrega().getDemanda().getTomador()) < 0) {
			return false;
		}
		return true;
	}
}
