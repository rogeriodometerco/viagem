package resumo;

import java.util.HashMap;
import java.util.Map;

public class Resumo<T> {
	private Map<T, Map<String, Totalizacao>> resumo;


	public Map<String, Totalizacao> getTotalizacao(T chave) {
		return resumo.get(chave);
	}
	
	public void putTotalizacoes(T chave, Map<String, Totalizacao> nova) {
		resumo.put(chave, nova);
	}
	
	public void acumular(T chave, String nome, int valor, int indice) {
		Map<String, Totalizacao> totalizacoes = resumo.get(chave);
		if (totalizacoes == null) {
			totalizacoes = new HashMap<String, Totalizacao>();
		}
		Totalizacao totalizacao = totalizacoes.get(nome);
		if (totalizacao == null) {
			totalizacao = new Totalizacao();
			totalizacoes.put(nome, totalizacao);
		}
		totalizacao.acumular(valor, indice);
	}
}
