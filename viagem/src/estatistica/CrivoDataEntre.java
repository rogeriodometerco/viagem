package estatistica;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import util.DataUtil;

public class CrivoDataEntre implements Crivo<Date> {

	private Date aPartirDe;
	private Date antesDe;
	
	public CrivoDataEntre(Date data, EscalaTempo escala, int granularidade) {
		int field;
		Date dataManipulada;
		if (escala.equals(EscalaTempo.DIA)) {
			field = Calendar.DAY_OF_MONTH;
			dataManipulada = DataUtil.extrairDataSemHora(data);
		} else {
			field = Calendar.HOUR_OF_DAY;
			dataManipulada = DataUtil.extrairDataSemMinuto(data);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataManipulada);
		int porcaoUnidade = calendar.get(field);
		int resto = porcaoUnidade % granularidade;
		
		calendar.add(field, resto * -1);
		this.aPartirDe = calendar.getTime();
		
		calendar.add(field, granularidade);
		//this.aPartirDe = dataManipulada;
		this.antesDe = calendar.getTime();

		System.out.println(new SimpleDateFormat("dd-M-yyyy HH:mm:ss").format(aPartirDe) + " até " + new SimpleDateFormat("dd-M-yyyy HH:mm:ss").format(antesDe));
	}
	
	public CrivoDataEntre(Date aPartirDe, Date antesDe) {
		this.aPartirDe = aPartirDe;
		this.antesDe = antesDe;
	}

	@Override
	public boolean passa(Date objeto) {
		return !aPartirDe.after(objeto) && objeto.before(antesDe);
	}

	public Date getAPartirDe() {
		return aPartirDe;
	}

	public Date getAntesDe() {
		return antesDe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aPartirDe == null) ? 0 : aPartirDe.hashCode());
		result = prime * result + ((antesDe == null) ? 0 : antesDe.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CrivoDataEntre other = (CrivoDataEntre) obj;
		if (aPartirDe == null) {
			if (other.aPartirDe != null)
				return false;
		} else if (!aPartirDe.equals(other.aPartirDe))
			return false;
		if (antesDe == null) {
			if (other.antesDe != null)
				return false;
		} else if (!antesDe.equals(other.antesDe))
			return false;
		return true;
	}


	
}

