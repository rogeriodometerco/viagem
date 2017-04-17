package resumo;

import java.util.ArrayList;
import java.util.List;

public class Totalizacao {

	private int total;
	private List<Integer> indices;
	
	public Totalizacao() {
		indices = new ArrayList<Integer>();
	}

	public void acumular(int valor, int indice) {
		this.total += valor;
		this.indices.add(indice);
	}
}
