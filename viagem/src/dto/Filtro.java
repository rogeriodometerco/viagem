package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Filtro implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Restricao> restricoes;
	
	public Filtro() {
		restricoes = new ArrayList<Restricao>();
	}
	
	public Filtro igual(String chave, Object valor) {
		this.restricoes.add(new Restricao(chave, valor, Restricao.IGUAL));
		return this;
	}
	
	public Filtro iniciaPor(String chave, Object valor) {
		this.restricoes.add(new Restricao(chave, valor, Restricao.INICIA));
		return this;
	}
	
	public Filtro terminaCom(String chave, Object valor) {
		this.restricoes.add(new Restricao(chave, valor, Restricao.TERMINA));
		return this;
	}
	
	public Filtro contem(String chave, Object valor) {
		this.restricoes.add(new Restricao(chave, valor, Restricao.CONTEM));
		return this;
	}
	
	public Filtro maior(String chave, Object valor) {
		this.restricoes.add(new Restricao(chave, valor, Restricao.MAIOR));
		return this;
	}
	
	public Filtro menor(String chave, Object valor) {
		this.restricoes.add(new Restricao(chave, valor, Restricao.MENOR));
		return this;
	}
	
	public Filtro maiorOuIgual(String chave, Object valor) {
		this.restricoes.add(new Restricao(chave, valor, Restricao.MAIOR_IGUAL));
		return this;
	}
	
	public Filtro menorOuIgual(String chave, Object valor) {
		this.restricoes.add(new Restricao(chave, valor, Restricao.MENOR_IGUAL));
		return this;
	}
	
	public List<Restricao> getRestricoes() {
		return restricoes;
	}
	
	public class Restricao {
		public static final String IGUAL = "IGUAL";
		public static final String INICIA = "INICIA";
		public static final String TERMINA = "TERMINA";
		public static final String CONTEM = "CONTEM";
		public static final String MAIOR = "MAIOR";
		public static final String MENOR = "MENOR";
		public static final String MAIOR_IGUAL = "MAIOR_IGUAL";
		public static final String MENOR_IGUAL = "MENOR_IGUAL";

		private String chave;
		private Object valor;
		private String restricao;
		
		public Restricao(String chave, Object valor, String restricao) {
			this.chave = chave;
			this.valor = valor;
			this.restricao = restricao;
		}

		public String getChave() {
			return chave;
		}

		public Object getValor() {
			return valor;
		}

		public String getRestricao() {
			return restricao;
		}
	}
}
