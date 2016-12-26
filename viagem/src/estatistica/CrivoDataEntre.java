package estatistica;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

import util.DataUtil;

public class CrivoDataEntre implements Crivo<Date> {

	private Date aPartirDe;
	private Date antesDe;
	
	public CrivoDataEntre(Date data, UnidadeDataEnum unidade, int intervalo) {
		int field;
		Date dataManipulada;
		if (unidade.equals(UnidadeDataEnum.DIA)) {
			field = Calendar.DAY_OF_MONTH;
			dataManipulada = DataUtil.extrairDataSemHora(data);
		} else {
			field = Calendar.HOUR_OF_DAY;
			dataManipulada = DataUtil.extrairDataSemMinuto(data);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataManipulada);
		int porcaoUnidade = calendar.get(field);
		int resto = porcaoUnidade % intervalo;
		calendar.add(field, resto * -1);
		System.out.println(new SimpleDateFormat("dd-M-yyyy HH:mm:ss").format(calendar.getTime()));
		calendar.add(field, intervalo);
		System.out.println(new SimpleDateFormat("dd-M-yyyy HH:mm:ss").format(calendar.getTime()));
		
	}
	
	public CrivoDataEntre(Date aPartirDe, Date antesDe) {
		this.aPartirDe = aPartirDe;
		this.antesDe = antesDe;
	}

	@Override
	public boolean passa(Date objeto) {
		return !aPartirDe.after(objeto) && objeto.before(antesDe);
	}
}
