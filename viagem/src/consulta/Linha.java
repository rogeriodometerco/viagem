package consulta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Coamo Agroindustrial Cooperativa 
 *
 * @author rdometerco@coamo.com.br 
 * @creation  28/12/2016 16:37:20 
 */
public class Linha implements Serializable {
	private List<Celula> celulas;
	
	public Linha() {
		this.celulas = new ArrayList<Celula>();
	}

	public void adicionarCelula(Celula celula) {
		this.celulas.add(celula);
		celula.setLinha(this);
	}

	public boolean acumular(Linha outra) {
		if (!igualParaAcumular(outra)) {
			return false;
		}
		for (int i = 0; i < celulas.size(); i++) {
			if (celulas.get(i).getColuna().getAcumulada()) {
				celulas.get(i).acumular(outra.getCelulas().get(i));
			}
		}
		return true;
	}
	
	
	public List<Celula> getCelulas() {
		return celulas;
	}
	
	public boolean passaNoFiltro() {
		for (Celula celula: celulas) {
			if (!celula.passaNoFiltro()) {
				return false;
			}
		}
		return true;
	}

	public String getString() {
		String descricao = "| ";
		for (Celula celula: celulas) {
			descricao += celula.getRepresentacao() + " | ";
		}
		return descricao;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((celulas == null) ? 0 : celulas.hashCode());
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
		Linha other = (Linha) obj;
		if (celulas == null) {
			if (other.celulas != null)
				return false;
		} else if (!celulas.equals(other.celulas))
			return false;
		return true;
	}

	public boolean parcialmenteIgual(Linha linha, int[] posicoes) {
		for (int i: posicoes) {
			if (!celulas.get(i).equals(linha.getCelulas().get(i))) {
				return false;
			}
		}
		return true;
	}
	
	private boolean igualParaAcumular(Linha linha) {
		for (int i = 0; i < celulas.size(); i++) {
			Celula celula = celulas.get(i);
			Celula celulaOutra = linha.getCelulas().get(i);
			if (celula.getColuna().getAgrupada() && !celula.equals(celulaOutra)) {
				return false;
			}
		}
		return true;
	}
	
	public List<Celula> getParaAgrupamento() {
		List<Celula> celulasParaAgrupamento = new ArrayList<Celula>();
		for (Celula celula: this.celulas) {
			if (celula.getColuna().getAcumulada() || celula.getColuna().getAgrupada()) {
				celulasParaAgrupamento.add(celula);
			}
		}
		return celulasParaAgrupamento;
	}
	

}
