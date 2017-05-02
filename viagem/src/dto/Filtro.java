package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Filtro implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Restricao> restricoes;
	private Integer pagina;
	private Integer tamanhoPagina;

	public Filtro() {
		this.restricoes = new ArrayList<Restricao>();
		this.pagina = 1;
		this.tamanhoPagina = 20;
	}

	public void inicializar(String json) {
		JsonObject params = new Gson().fromJson(json, JsonObject.class);
		if (params == null) {
			return;
		}
		if (params.get("p") != null) {
			this.pagina = params.get("p").getAsInt();
		}
		if (params.get("t") != null) {
			this.tamanhoPagina = params.get("t").getAsInt();
		}
		if (params.get("q") != null) {
			this.restricoes = new ArrayList<Restricao>();
			JsonArray q = params.get("q").getAsJsonArray();
			for (JsonElement e: q) {
				Restricao r = new Restricao(
						e.getAsJsonObject().get("chave").getAsString(),
						e.getAsJsonObject().get("valor").getAsString(),
						e.getAsJsonObject().get("restricao").getAsString());
				this.restricoes.add(r);
			}
		}
	}

	public Filtro igual(String chave, Object valor) {
		this.restricoes.add(new Restricao(chave, valor, Restricao.IGUAL));
		return this;
	}
	/*	
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

	 */	public Filtro maior(String chave, Object valor) {
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

	 public Integer getPagina() {
		 return pagina;
	 }

	 public void setPagina(Integer pagina) {
		 this.pagina = pagina;
	 }

	 public Integer getTamanhoPagina() {
		 return tamanhoPagina;
	 }

	 public void setTamanhoPagina(Integer tamanhoPagina) {
		 this.tamanhoPagina = tamanhoPagina;
	 }



	 public class Restricao {
		 public static final String IGUAL = "=";
		 //public static final String INICIA = "INICIA";
		 //public static final String TERMINA = "TERMINA";
		 //public static final String CONTEM = "IN";
		 public static final String MAIOR = ">";
		 public static final String MENOR = "<";
		 public static final String MAIOR_IGUAL = ">=";
		 public static final String MENOR_IGUAL = "<=";

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
