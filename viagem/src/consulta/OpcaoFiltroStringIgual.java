package consulta;

/**
 * Coamo Agroindustrial Cooperativa 
 *
 * @author rdometerco@coamo.com.br 
 * @creation  29/12/2016 09:18:41 
 */
public class OpcaoFiltroStringIgual extends OpcaoFiltro {

	public OpcaoFiltroStringIgual(String opcao) {
		super(opcao);
	}

	@Override
	public boolean passa(Celula celula) {
		if (opcao == null && celula.getDado() == null) {
			return true;
		} else if (opcao == null || celula.getDado() == null) {
			return false;
		}
		return ((String)celula.getDado()).toUpperCase().equals(((String)opcao).toUpperCase());
	}

	@Override
	public String getDescricao() {
		return (String)opcao;
	}

}
