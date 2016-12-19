package teste;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import util.DataUtil;

public class TesteEvento {

	public static void main(String[] args) throws Exception {
		TesteEvento testeEvento = new TesteEvento();
		testeEvento.testar();
	}

	public void testar() {
		Calendar calendar = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");
		System.out.println("1: " + format.format(calendar.getTime()));
		calendar.setTime(DataUtil.extrairDataSemHora(calendar.getTime()));
		
		System.out.println("2: " + format.format(calendar.getTime()));
		Date dataInicial = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		System.out.println("3: " + format.format(calendar.getTime()));
		//Date dataFinal = calendar.getTime();
		Date dataFinal = DataUtil.somarDias(calendar.getTime(), -3);
		System.out.println("1: " + format.format(dataFinal));
	}
}
