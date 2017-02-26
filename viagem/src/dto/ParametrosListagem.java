package dto;

import java.io.Serializable;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ParametrosListagem implements Serializable {

	private Integer pagina;
	private Long tamanhoPagina;
	private Filtro[] filtros;
	private Ordenacao[] ordenacao;


	public ParametrosListagem(String json) {
		ParametrosListagem p = new Gson().fromJson(json, this.getClass());

		
		JSONObject params = new JSONObject(json);
		this.pagina = params.getInt("p");
		this.tamanhoPagina = params.getLong("t");
		//this.filtros = params.getJSONArray("filtros").
	}
	
	public ParametrosListagem(int pagina, long tamanhoPagina, Filtro[] filtros, Ordenacao[] ordenacao) {
		this.pagina = pagina;
		this.tamanhoPagina = tamanhoPagina;
		this.filtros = filtros;
		this.ordenacao = ordenacao;
	}
}

