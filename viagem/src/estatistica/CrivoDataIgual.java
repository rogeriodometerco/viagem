package estatistica;

import java.util.Date;

public class CrivoDataIgual implements Crivo<Date> {

	private Date data;
	
	public CrivoDataIgual(Date e) {
		this.data = e;
	}

	@Override
	public boolean passa(Date objeto) {
		return data.equals(objeto);
	}}
