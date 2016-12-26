package estatistica;

import java.util.ArrayList;
import java.util.List;

public class Coluna<T> {
	private List<T> elementos;
	private Filtro<T> filtro;
	
	public Coluna() {
		elementos = new ArrayList<T>();
		filtro = new Filtro<T>();
	}
	
	public void adicionarLinha(T elemento) {
		elementos.add(elemento);
	}
	
	public Filtro<T> getFiltro() {
		return filtro;
	}
	
}
