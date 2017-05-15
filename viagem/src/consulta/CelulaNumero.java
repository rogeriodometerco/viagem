package consulta;

/**
 * Coamo Agroindustrial Cooperativa 
 *
 * @author rdometerco@coamo.com.br 
 * @creation  28/12/2016 16:47:24 
 */
public class CelulaNumero extends Celula {

	@Override
	public String getRepresentacao() {
		if (dado == null) {
			return "";
		}
		return String.valueOf(dado);
	}

	@Override
	public Celula copiar() {
		CelulaNumero celula = new CelulaNumero();
		celula.setColuna(coluna);
		celula.setLinha(linha);
		celula.setDado(dado);
		return celula;
	}

	public void acumular(Celula outra) {
		this.dado = ((Number)this.dado).doubleValue() + ((Number)outra.getDado()).doubleValue();
	}
}
