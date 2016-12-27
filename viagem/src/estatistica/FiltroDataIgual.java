package estatistica;

import java.util.Date;

/**
 * Coamo Agroindustrial Cooperativa 
 *
 * @author rdometerco@coamo.com.br 
 * @creation  26/12/2016 16:05:09 
 */
public class FiltroDataIgual extends Filtro<Date> {

	@Override
	public CrivoDataIgual construirCrivo(Date objeto) {
		CrivoDataIgual crivo = new CrivoDataIgual(objeto);
		return crivo;
	}

}
