package consulta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Coamo Agroindustrial Cooperativa 
 *
 * @author rdometerco@coamo.com.br 
 * @creation  29/12/2016 09:36:35 
 */
public class Filtro implements Serializable {
	private List<OpcaoFiltro> opcoes;
	
	public Filtro() {
		this.opcoes = new ArrayList<OpcaoFiltro>();
	}
	
	public void adicionarOpcao(OpcaoFiltro opcao) {
		opcoes.add(opcao);
	}
	
	public boolean passaNoFiltro(Celula celula) {
		boolean algumaOpcaoHabilitada = false;
		for (OpcaoFiltro opcao: opcoes) {
			if (opcao.getHabilitada()) {
				algumaOpcaoHabilitada = true;

				if (opcao.passa(celula)) {
					return true;
				}
			}
		}
		return !algumaOpcaoHabilitada;
	}
	
	public List<OpcaoFiltro> getOpcoes() {
		return opcoes;
	}

}
