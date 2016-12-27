package estatistica;

import java.util.HashSet;
import java.util.Set;

public abstract class Filtro<T> {
	private Set<Crivo<T>> crivos;


	protected Set<Crivo<T>> getCrivos() {
		return crivos;
	}

	public Filtro() {
		this.crivos = new HashSet<Crivo<T>>();
	}

	public Filtro(Crivo<T> crivo) {
		this.crivos = new HashSet<Crivo<T>>();
		this.crivos.add(crivo);
	}

	public Filtro(Set<Crivo<T>> crivos) {
		this.crivos = crivos;
	}

	public void adicionarCrivo(Crivo<T> crivo) {
		this.crivos.add(crivo);
	}
	
	public void adicionarCrivoPara(T elemento) {
		this.crivos.add(construirCrivo(elemento));
	}
	
	protected abstract Crivo<T> construirCrivo(T objeto);
	
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
