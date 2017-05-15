package consulta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Coamo Agroindustrial Cooperativa 
 *
 * @author rdometerco@coamo.com.br 
 * @creation  28/12/2016 17:31:43 
 */
public class Coluna implements Serializable {
	private String titulo;
	private List<Celula> celulas;
	private Filtro filtro;
	private boolean agrupada;
	private boolean acumulada;
	
	public Coluna() {
		this.celulas = new ArrayList<Celula>();
		this.filtro = new Filtro();
		this.agrupada = false;
		this.acumulada = false;
	}
	
	public boolean getAcumulada() {
		return acumulada;
	}
	
	public boolean getAgrupada() {
		return agrupada;
	}
	
	public Coluna copiar() {
		Coluna coluna = new Coluna();
		coluna.setTitulo(titulo);
		coluna.setAgrupada(agrupada);
		coluna.setAcumulada(acumulada);
		return coluna;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void adicionarCelula(Celula celula) {
		celulas.add(celula);
		celula.setColuna(this);
	}

	public List<Celula> getCelulas() {
		return celulas;
	}
	
	public Filtro getFiltro() {
		return filtro;
	}
	
	public void setAgrupada(boolean agrupada) {
		this.agrupada = agrupada;
	}
	
	public void setAcumulada(boolean acumulada) {
		this.acumulada = acumulada;
	}
}
