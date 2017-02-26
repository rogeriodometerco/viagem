package dto;

import java.io.Serializable;

public class Ordenacao implements Serializable {

	public static final String ASCENDENTE = "A";
	public static final String DESCENDENTE = "D";
	
	private String chave;
	private Character ordem;
	
	public Ordenacao(String chave, Character ordem) {
		this.chave = chave;
		this.ordem = ordem;
	}

}
