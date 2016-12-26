package estatistica;

public class Bkp_FiltroPontual<T> implements Bkp_Filtro<T> {

	private T objetoFiltro;
	
	public Bkp_FiltroPontual(T objetoFiltro) {
		this.objetoFiltro = objetoFiltro;
	}

	@Override
	public boolean filtrar(T objeto) {
		return objetoFiltro.equals(objeto);
	}

}
