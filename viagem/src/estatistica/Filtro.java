package estatistica;

import java.util.ArrayList;
import java.util.List;

public class Filtro<T> {
	private List<Crivo<T>> crivos;


	protected List<Crivo<T>> getCrivos() {
		return crivos;
	}

	public Filtro() {
		this.crivos = new ArrayList<Crivo<T>>();
	}

	public Filtro(Crivo<T> crivo) {
		this.crivos = new ArrayList<Crivo<T>>();
		this.crivos.add(crivo);
	}

	public Filtro(List<Crivo<T>> crivos) {
		this.crivos = crivos;
	}

	public void adicionarCrivo(Crivo<T> crivo) {
		this.crivos.add(crivo);
	}
	
	public void limparCrivos() {
		this.crivos.clear();
	}
	
	public boolean comparar(T objeto) {
		for (Crivo<T> crivo: crivos) {
			if (crivo.passa(objeto)) {
				return true;
			}
		}
		return false;
	}
}
