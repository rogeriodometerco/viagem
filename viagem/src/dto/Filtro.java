package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Filtro implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Restricao> restricoes;
	private List<OrderBy> ordenacoes;
	private Integer pagina;
	private Integer tamanhoPagina;

	public Filtro() {
		this.restricoes = new ArrayList<Restricao>();
		this.ordenacoes = new ArrayList<OrderBy>();
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
						e.getAsJsonObject().get("operador").getAsString());
				this.restricoes.add(r);
			}
		}
	}

	public Filtro igual(String chave, Object valor) {
		this.restricoes.add(new Restricao(chave, valor, Restricao.IGUAL));
		return this;
	}

	public Filtro diferente(String chave, Object valor) {
		this.restricoes.add(new Restricao(chave, valor, Restricao.DIFERENTE));
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

	public Filtro iniciaCom(String chave, String valor) {
		this.restricoes.add(new Restricao(chave, "%" + valor.trim().toUpperCase(), Restricao.INICIA));
		return this;
	}

	public Filtro terminaCom(String chave, String valor) {
		this.restricoes.add(new Restricao(chave, valor.trim().toUpperCase() + "%", Restricao.TERMINA));
		return this;
	}

	public Filtro contem(String chave, String valor) {
		this.restricoes.add(new Restricao(chave, "%" + valor.trim().toUpperCase() + "%", Restricao.CONTEM));
		return this;
	}

	public Filtro isNull(String chave) {
		this.restricoes.add(new Restricao(chave, "", Restricao.IS_NULL));
		return this;
	}

	public Filtro isNotNull(String chave) {
		this.restricoes.add(new Restricao(chave, "", Restricao.IS_NOT_NULL));
		return this;
	}


	public Filtro orderAsc(String chave) {
		this.ordenacoes.add(new OrderBy(chave, "ASC"));
		return this;
	}

	public Filtro orderDesc(String chave) {
		this.ordenacoes.add(new OrderBy(chave, "DESC"));
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

	public String completarSql(String sql, Map<String, String> conversao) {
		String where = construirClausula(conversao);
		String orderBy = construirOrderBy(conversao);

		String substituta = "";
		if (where != "") {
			substituta = " WHERE " + where + " ";
		}
		sql = sql.replace("[WHERE]", substituta);

		if (orderBy != "" && orderBy.trim().length() > 0) {
			sql = sql + " ORDER BY " + orderBy;
		}
		return sql;
	}

	public String completarSql2(String projecao, String condicaoWhere, Map<String, String> conversao) {
		String query = "";
		String where = construirClausula(conversao);
		String orderBy = construirOrderBy(conversao);

		if (where == "") {
			where = condicaoWhere;
		} else {
			if (condicaoWhere != "") {
				where = "(" + where  + ")" + " AND (" + condicaoWhere + ")";
			}
		}
		if (where != "") {
			where = " WHERE (" + where + ") ";
		}
		
		/*
		String substituta = "";
		
		if (where != "" || condicaoWhere != "") {
			substituta = " WHERE ";
			if (condicaoWhere != "") {
				substituta = substituta + " (" + condicaoWhere + ") ";
			}
			if (where != "") {
				if (condicaoWhere != "") {
					substituta = substituta + " AND ";
				}
				substituta = substituta + " (" + where + ") ";
			}
		}
		query = projecao.concat(" ").concat(substituta); */
		
		query = projecao.concat(" ").concat(where);

		if (orderBy != "" && orderBy.trim().length() > 0) {
			query = query + " ORDER BY " + orderBy;
		}
		return query;
	}

	public String construirClausula(Map<String, String> conversao) {
		StringBuffer sql = new StringBuffer("(");
		int i = 0;

		for (Restricao r: restricoes) {
			if (conversao.get(r.getChave()) != null) {
				if (i > 0) {
					sql.append(" AND ");
				}

				String coluna = conversao.get(r.getChave());
				if (r.getValor().getClass().equals(String.class)) {
					coluna = "UPPER(" + coluna + ")";
				}

				sql.append("(");
				sql.append(coluna);
				sql.append(" ");
				sql.append(r.getOperador());
				sql.append(" :");
				sql.append(r.getChave());
				sql.append(")");
			}
			i++;
		}
		sql.append(")");
		return sql.toString();
	}


	private String construirOrderBy(Map<String, String> conversao) {
		StringBuffer sql = new StringBuffer();
		int i = 0;

		for (OrderBy orderBy: ordenacoes) {
			if (conversao.get(orderBy.getChave()) != null) {
				if (i > 0) {
					sql.append(", ");
				}

				String coluna = conversao.get(orderBy.getChave());
				sql.append(coluna);
				sql.append(" ");
				sql.append(orderBy.getOperador());
			}
			i++;
		}
		return sql.toString();
	}

	public void setarParametrosWhere(TypedQuery<?> query) {
		for (Restricao r: this.restricoes) {
			if (query.getParameter(r.getChave()) != null) {
				Object valor = r.getValor();
				if (valor.getClass().equals(String.class)) {
					valor = ((String)valor).toUpperCase();
				}
				query.setParameter(r.getChave(), valor);
			}
		}
	}
	/*
    protected void setarParametros(TypedQuery<?> query, Filtro filtro) {
	for (Restricao r: filtro.getRestricoes()) {
	    if (query.getParameter(r.getChave())) {
	    	Object valor = r.getValor();
	    	if (valor.getClass().equals(String.class)) {
	    		valor = ((String)valor).toUpperCase();
	    	}
		query.setParameter(r.getChave(), r.getValor());
	    }
	}
    }
	 */

/*	protected void setarParametros() {
		for (Restricao r: restricoes) {
			Object valor = r.getValor();
			if (valor.getClass().equals(String.class)) {
				valor = ((String)valor).toUpperCase();
			}
			System.out.println(r.getChave() + "	" + valor);
		}
	}

*/	public class Restricao {
		public static final String IGUAL = "=";
		public static final String DIFERENTE = "!=";
		public static final String INICIA = "LIKE";
		public static final String TERMINA = "LIKE";
		public static final String CONTEM = "LIKE";
		public static final String MAIOR = ">";
		public static final String MENOR = "<";
		public static final String MAIOR_IGUAL = ">=";
		public static final String MENOR_IGUAL = "<=";
		public static final String IS_NULL = "IS NULL";
		public static final String IS_NOT_NULL = "IS NOT NULL";

		private String chave;
		private Object valor;
		private String operador;

		public Restricao(String chave, Object valor, String operador) {
			this.chave = chave;
			this.valor = valor;
			this.operador = operador;
		}

		public String getChave() {
			return chave;
		}

		public Object getValor() {
			return valor;
		}

		public String getOperador() {
			return operador;
		}
	}

	public class OrderBy {
		private String chave;
		private String operador;

		public OrderBy(String chave, String operador) {
			this.chave = chave;
			this.operador = operador;
		}

		public String getChave() {
			return chave;
		}

		public String getOperador() {
			return operador;
		}
	}
}