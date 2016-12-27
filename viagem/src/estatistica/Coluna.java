package estatistica;

import java.util.ArrayList;
import java.util.List;

public class Coluna<T> {
	private List<T> elementos;
	private List<T> elementosFiltrados;
	private Filtro<T> filtro;
	public List<Integer> filtrados;
	
	public Coluna(Filtro<T> filtro) {
		this.elementos = new ArrayList<T>();
		this.elementosFiltrados = new ArrayList<T>();
		this.filtro = filtro;
	}

	public void adicionarLinha(T elemento) {
		elementos.add(elemento);
		filtro.adicionarCrivoPara(elemento);
	}
	
	public Filtro<T> getFiltro() {
		return filtro;
	}
	
	public List<Integer> filtrar() {
		List<Integer> indices = new ArrayList<Integer>();
		int i = 0;
		for (T elemento: elementos) {
			if (filtro.comparar(elemento)) {
				indices.add(i);
			}
			i++;
		}
		return indices;
	}
	
	public void setFiltrados(List<Integer> filtrados) {
		this.filtrados = filtrados;
	}
	
	public List<T> getFiltrados() {
		elementosFiltrados.clear();
		for (int i: filtrados) {
			elementosFiltrados.add(elementos.get(i));
		}
		return elementosFiltrados;
	}
}
