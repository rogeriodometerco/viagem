package estatistica;

import java.util.Date;

/**
 * Coamo Agroindustrial Cooperativa 
 *
 * @author rdometerco@coamo.com.br 
 * @creation  22/12/2016 10:51:48 
 */
public class FiltroData implements Filtro<Date> {

	private Date dataInicial;
	private Date dataFinal;
	
	public FiltroData(Date dataInicial, Date dataFinal) {
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
	}
	
	@Override
	public boolean filtrar(Date objeto) {
		if (dataInicial.after(objeto) || dataFinal.before(objeto)) {
			return false;
		}
		return true;
	}

}
