package estatistica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Matriz {

	private Map<String, Coluna<?>> colunas;

	public Matriz() {
		this.colunas = new HashMap<String, Coluna<?>>();
	}

	public void setColuna(String chave, Coluna<?> coluna) {
		colunas.put(chave, coluna);
	}

	public void aplicarFiltros() {
		List<Integer> indices = new ArrayList<Integer>();
		List<Integer> proximaColuna = new ArrayList<Integer>();

		Iterator<Entry<String, Coluna<?>>> colunasIterator = colunas.entrySet().iterator();

		while (colunasIterator.hasNext() && indices.isEmpty()) {
			indices = colunasIterator.next().getValue().filtrar();
		}
		while (colunasIterator.hasNext()) {
			while (colunasIterator.hasNext() && proximaColuna.isEmpty()) {
				proximaColuna = colunasIterator.next().getValue().filtrar();
			} 
			if (!indices.isEmpty() && !proximaColuna.isEmpty()) {
				indices.retainAll(proximaColuna);
				proximaColuna.clear();
			}
		}
		// Seta o índice das linhas que passaram nos filtros de todas as colunas.
		colunasIterator = colunas.entrySet().iterator();
		for (Entry<String, Coluna<?>> colunaEntry: colunas.entrySet()) {
			colunaEntry.getValue().setFiltrados(indices);
		}
	}
}
