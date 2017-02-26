package dto;

import java.io.Serializable;

public class Filtro implements Serializable {

	public static final String IGUAL = "IGUAL";
	public static String INICIA = "INICIA";
	public static String TERMINA = "TERMINA";
	public static String CONTEM = "CONTEM";
	public static String MAIOR = "MAIOR";
	public static String MENOR = "MENOR";
	public static String MAIOR_IGUAL = "MAIOR_IGUAL";
	public static String MENOR_IGUAL = "MENOR_IGUAL";

	private String chave;
	private Object valor;
	private String restricao;
	
	public Filtro(String chave, Object valor, String restricao) {
		this.chave = chave;
		this.valor = valor;
		this.restricao = restricao;
	}

}
