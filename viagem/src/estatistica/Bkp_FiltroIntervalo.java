package estatistica;

import java.util.Date;

public class Bkp_FiltroIntervalo<T> implements Bkp_Filtro<T> {

	private T inicioIntervalo;
	private T fimIntervalo;
	
	public Bkp_FiltroIntervalo(T inicio, T fim) {
		this.inicioIntervalo = inicio;
		this.fimIntervalo = fim;
	}

	@Override
	public boolean filtrar(T objeto) {
		if (objeto.getClass().equals(Date.class)) {
			return (!((Date)inicioIntervalo).after((Date)objeto) && !((Date)fimIntervalo).before((Date)objeto));
		}
		return false;
	}
	
	public T getInicioIntervalo() {
		return inicioIntervalo;
	}
	
	public T getFimIntervalo() {
		return fimIntervalo;
	}

}
