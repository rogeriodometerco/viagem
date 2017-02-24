package util;

import java.util.List;

public class Listagem<T> {
	
	private Long count;
	private int pagina;
	private List<T> lista;

	public Listagem(int pagina, List<T> lista, Long count) {
		this.lista = lista;
		this.pagina = pagina;
		this.count = count;
	}

	public Long getCount() {
		return count;
	}
	
	public List<T> getLista() {
		return lista;
	}
	
	public int getPagina() {
		return pagina;
	}
}
