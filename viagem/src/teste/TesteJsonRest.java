package teste;

import com.google.gson.Gson;

import dto.ParametrosListagem;

public class TesteJsonRest {

	
	public static void main(String[] args) {
		TesteJsonRest teste = new TesteJsonRest();
		teste.testar();
	}

	public void testar(){
		String json = "{p:1, t:5, filtros:[{chave: nome, valor: campo, restricao: inicia}, {chave: uf, valor: PR, restricao: igual}], ordenacao: [{chave: nome, ordem: A}]}";
		ParametrosListagem p = new Gson().fromJson(json, ParametrosListagem.class);
		
		System.out.println(new Gson().toJson(p));
	}

}
