package consulta;

import java.io.Serializable;

/**
 * Coamo Agroindustrial Cooperativa 
 *
 * @author rdometerco@coamo.com.br 
 * @creation  29/12/2016 09:17:49 
 */
public abstract class OpcaoFiltro implements Serializable {
	
	protected Object opcao;
	private boolean habilitada;
	
	public OpcaoFiltro(Object opcao) {
		this.opcao = opcao;
		this.habilitada = false;
	}
	
	public void setHabilitada(boolean habilitada) {
		this.habilitada = habilitada;
	}
	
	public boolean getHabilitada() {
		return habilitada;
	}
	
	public abstract boolean passa(Celula celula);
	public abstract String getDescricao();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((opcao == null) ? 0 : opcao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OpcaoFiltro other = (OpcaoFiltro) obj;
		if (opcao == null) {
			if (other.opcao != null)
				return false;
		} else if (!opcao.equals(other.opcao))
			return false;
		return true;
	}

	
	
	
}
