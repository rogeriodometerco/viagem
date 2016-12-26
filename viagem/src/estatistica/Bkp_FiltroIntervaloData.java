package estatistica;

import java.util.Date;

public class Bkp_FiltroIntervaloData implements Bkp_Filtro<Date>{

	private Date dataInicial;
	private Date dataFinal;
	
	@Override
	public boolean filtrar(Date data) {
		return false;
	}
	
	

}
