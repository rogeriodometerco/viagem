package consulta;

/**
 * Coamo Agroindustrial Cooperativa 
 *
 * @author rdometerco@coamo.com.br 
 * @creation  29/12/2016 09:55:36 
 */
public class OpcaoFiltroNumeroIgual extends OpcaoFiltro {

	public OpcaoFiltroNumeroIgual(Number opcao) {
		super(opcao);
	}

	@Override
	public boolean passa(Celula celula) {
		return celula.getDado().equals(opcao);
	}

	@Override
	public String getDescricao() {
		return String.valueOf(opcao);
	}

}
