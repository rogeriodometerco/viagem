package consulta;

import java.text.SimpleDateFormat;

/**
 * Coamo Agroindustrial Cooperativa 
 *
 * @author rdometerco@coamo.com.br 
 * @creation  28/12/2016 16:29:20 
 */
public class CelulaData extends Celula {

	private String mascara;
	
	public void setMascara(String mascara) {
		this.mascara = mascara;
	}
	
	@Override
	public String getRepresentacao() {
		if (dado == null) {
			return "";
		}
		if (mascara == null || mascara.trim().equals("")) {
			mascara = "dd/MM/yyyy HH:mm";
		}
		return new SimpleDateFormat(mascara).format(this.dado);
	}

	@Override
	public Celula copiar() {
		CelulaData celula = new CelulaData();
		celula.setColuna(coluna);
		celula.setLinha(linha);
		celula.setDado(dado);
		celula.setMascara(mascara);
		return celula;
	}

	public void acumular(Celula outra) {
	}
}
