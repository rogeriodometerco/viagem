package estatistica;

import java.util.Date;

/**
 * Coamo Agroindustrial Cooperativa 
 *
 * @author rdometerco@coamo.com.br 
 * @creation  26/12/2016 16:05:09 
 */
public class FiltroDataEntre extends Filtro<Date> {

	private EscalaTempo escala;
	private int granularidade;
	
	public FiltroDataEntre(EscalaTempo escala, int granularidade) {
		this.escala = escala;
		this.granularidade = granularidade;
	}
	
	@Override
	public CrivoDataEntre construirCrivo(Date objeto) {
		CrivoDataEntre crivo = new CrivoDataEntre(objeto, escala, granularidade);
		return crivo;
	}

}
