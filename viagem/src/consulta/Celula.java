package consulta;

import java.io.Serializable;

/**
 * Coamo Agroindustrial Cooperativa 
 *
 * @author rdometerco@coamo.com.br 
 * @creation  28/12/2016 16:07:34 
 */
public abstract class Celula implements Serializable {
	protected Object dado;
	protected Linha linha;
	protected Coluna coluna;
	
	public Celula() {
		
	}
	
	public abstract Celula copiar();
	
	public abstract void acumular(Celula outra);

	public Celula(Linha linha, Coluna coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	public Object getDado() {
		return dado;
	}

	public void setDado(Object dado) {
		this.dado = dado;
	}
	
	public abstract String getRepresentacao();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dado == null) ? 0 : dado.hashCode());
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
		Celula other = (Celula) obj;
		if (dado == null) {
			if (other.dado != null)
				return false;
		} else if (!dado.equals(other.dado))
			return false;
		return true;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}
	
	public void setColuna(Coluna coluna) {
		this.coluna = coluna;
	}
	
	public Coluna getColuna() {
		return coluna;
	}

	public boolean passaNoFiltro() {
		return coluna.getFiltro().passaNoFiltro(this);
	}

	
}
