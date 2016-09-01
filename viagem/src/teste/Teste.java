package teste;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Teste {

	public static void main(String[] args) throws Exception {

		/*
		String dataStr = "Aug 26, 2016 9:29:29 PM";
		DateFormat format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a", Locale.US);
		System.out.println(format.parse(dataStr));
		 */

		//"https://maps.googleapis.com/maps/api/directions/json?origin=Campo+Mourao,PR&destination=Luisiana,PR");
		URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?"
				+ "origin=" + URLEncoder.encode("Campo Mourão, PR", "UTF-8")
				+ "&destination=" + URLEncoder.encode("Luisiana, PR", "UTF-8"));

		URLConnection uc = url.openConnection();
		BufferedReader in = new BufferedReader(
				new InputStreamReader(
						uc.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		System.out.println(response);
		in.close();
	}
}
