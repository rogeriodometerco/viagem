package consulta;

/**
 * Coamo Agroindustrial Cooperativa 
 *
 * @author rdometerco@coamo.com.br 
 * @creation  28/12/2016 16:13:53 
 */
public class CelulaString extends Celula {
	
	@Override
	public String getRepresentacao() {
		if (dado == null) {
			return "";
		}
		return (String)dado;
	}

	@Override
	public Celula copiar() {
		CelulaString celula = new CelulaString();
		celula.setColuna(coluna);
		celula.setLinha(linha);
		celula.setDado(dado);
		return celula;
	}

	public void acumular(Celula outra) {
	}
}
