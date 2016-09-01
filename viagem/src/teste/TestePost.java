package teste;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.Cookie;

public class TestePost {

	public static void main(String[] args) throws Exception {
		/*
        URL url=new URL("http://localhost:8080/viagem/rest/testePost");
        HttpURLConnection httpcon=(HttpURLConnection)url.openConnection();
        httpcon.setDoOutput(true);
        httpcon.setRequestMethod("POST");
        httpcon.setRequestProperty("Accept", "application/json");
        httpcon.setRequestProperty("Content-Type", "application/json");
        Cookie cookie=new Cookie("user", "abc");
        cookie.setValue("store");
        httpcon.setRequestProperty("Accept", "application/json");
        httpcon.setRequestProperty("Cookie", cookie.getValue());

        OutputStreamWriter output = new OutputStreamWriter(httpcon.getOutputStream());
        httpcon.connect();
        String output1=httpcon.getResponseMessage();
        System.out.println(output1);
		*/

        
        URL url=new URL("http://localhost:8080/viagem/rest/testePost");
        HttpURLConnection httpcon=(HttpURLConnection)url.openConnection();
        httpcon.setDoOutput(true);
        httpcon.setRequestMethod("POST");
        httpcon.setRequestProperty("Accept", "application/json");
        httpcon.setRequestProperty("Content-Type", "application/json");

        OutputStreamWriter output = new OutputStreamWriter(httpcon.getOutputStream());
        httpcon.connect();
        String output1=httpcon.getResponseMessage();
        System.out.println(output1);

	}

}
