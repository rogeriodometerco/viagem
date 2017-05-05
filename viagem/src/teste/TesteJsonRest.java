package teste;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dto.Filtro;
import dto.ParametrosListagem;
import enums.TipoOperacaoViagem;

public class TesteJsonRest {

	
	public static void main(String[] args) {
		TesteJsonRest teste = new TesteJsonRest();
		teste.testar5();
	}

	public void testar(){
		String json = "{p:1, t:5, filtros:[{chave: nome, valor: campo, restricao: inicia}, {chave: uf, valor: PR, restricao: igual}], ordenacao: [{chave: nome, ordem: A}]}";
		ParametrosListagem p = new Gson().fromJson(json, ParametrosListagem.class);
		
		System.out.println(new Gson().toJson(p));
	}

	public void testar2(){
		String json = "{pagina:1, tamanhoPagina:5, restricoes:[{chave: nome, valor: campo, restricao: '='}, {chave: uf, valor: PR, restricao: '='}], ordenacao: [{chave: nome, ordem: A}]}";
		Filtro f = new Gson().fromJson(json, Filtro.class);
		
		System.out.println(new Gson().toJson(f));
	}

	public void testar3(){
		String json = "{p:1, t:5, q:[{chave: nome, valor: campo, restricao: '='}, {chave: uf, valor: PR, restricao: '='}], ordenacao: [{chave: nome, ordem: A}]}";
		Map<String, Object> params = new Gson().fromJson(json, Map.class);
		
		System.out.println(new Gson().toJson(params));
		
		List<Map<String, Object>> qMap = (List<Map<String, Object>>)params.get("q");
		System.out.println(qMap.get(0).get("chave"));
		json = "";
		Filtro f = new Filtro();
		f.inicializar(json);
		System.out.println(f);
		
	}

	public void testar4(){
		String json = "{p:1, t:5, q:[{chave: nome, valor: campo, restricao: '='}, {chave: uf, valor: PR, restricao: '='}], ordenacao: [{chave: nome, ordem: A}]}";
		JsonObject params = new Gson().fromJson(json, JsonObject.class);
		
		System.out.println(new Gson().toJson(params));
		
		Integer p = params.get("p").getAsInt();
		Integer t = params.get("t").getAsInt();
		JsonArray q = params.get("q").getAsJsonArray();
		for (JsonElement e: q) {
			System.out.println(e.getAsJsonObject().get("chave") + " - " + e.getAsJsonObject().get("valor"));
		}
		//System.out.println(qMap.get(0).get("chave"));
		
		Filtro f = new Filtro();
		//f.inicializar(json);
		System.out.println(f);
	}
	
	public void testar5() {
		String sql = "select * from OperacaoViagem where tipo = " + TipoOperacaoViagem.COLETA.ordinal();
		System.out.println(sql);
	}

}
