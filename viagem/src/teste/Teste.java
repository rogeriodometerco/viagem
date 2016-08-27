package teste;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Teste {

	public static void main(String[] args) throws Exception {

		String dataStr = "Aug 26, 2016 9:29:29 PM";
		DateFormat format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a", Locale.US);
		System.out.println(format.parse(dataStr));
		
	}
}
