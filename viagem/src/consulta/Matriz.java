package consulta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.PieChartModel;

/**
 * Coamo Agroindustrial Cooperativa 
 *
 * @author rdometerco@coamo.com.br 
 * @creation  28/12/2016 17:08:01 
 */
public class Matriz implements Serializable {

    public List<Coluna> colunas;
    public List<Linha> linhas;

    private PieChartModel pieModel;

    public Matriz(List<Coluna> colunas) {
	this.colunas = colunas;
	this.linhas = new ArrayList<Linha>();
    }

    public void criarLinha(List<Celula> celulas) {
	Linha linha = new Linha();
	int i = 0;
	for (Celula celula: celulas) {
	    linha.adicionarCelula(celula);
	    colunas.get(i).adicionarCelula(celula);
	    i++;
	}
	linhas.add(linha);
    }

    public Linha getLinha(int linha) {
	return linhas.get(linha);
    }

    public Linha getLinhaParaAgrupamento(int lin) {
	Linha linhaCopiada = linhas.get(lin);
	Linha linhaCopia = new Linha();
	int index = 0;
	for (Coluna coluna: colunas) {
	    if (coluna.getAgrupada() || coluna.getAcumulada()) {
		linhaCopiada.adicionarCelula(linhaCopiada.getCelulas().get(index).copiar());
	    }
	    index++;
	}
	return linhaCopia;
    }

    public Matriz agrupar() {
	List<Coluna> colunasNovas = new ArrayList<Coluna>();
	for (Coluna coluna: this.colunas) {
	    if (coluna.getAgrupada() || coluna.getAcumulada()) {
		Coluna colunaNova = coluna.copiar();
		colunasNovas.add(colunaNova);
	    }
	}

	Matriz matriz = new Matriz(colunasNovas);
	for (Linha l: linhas) {
	    Linha nova = new Linha();
	    for (Celula celula: l.getParaAgrupamento()) {
		nova.getCelulas().add(celula.copiar());
	    }
	    matriz.agrupar(nova);
	}
	matriz.createPieModel();
	return matriz;
    }


    public void agrupar(Linha nova) {
	boolean acumulou = false;
	for (Linha existente: linhas) {
	    if (existente.acumular(nova)) {
		acumulou = true;
		break;
	    }
	}
	if (!acumulou) {
	    criarLinha(nova.getCelulas());
	}
    }

    public List<Linha> getLinhas() {
	return linhas;
    }

    public List<Coluna> getColunas() {
	return colunas;
    }


    private void createPieModel() {
	pieModel = new PieChartModel();

	for (Linha linha: linhas) {
	    String label = "";
	    Number valor = 0;
	    for (Celula celula: linha.getCelulas()) {
		if (celula.getColuna().getAgrupada()) {
		    if (label != "") {
			label = label + " / ";
		    }
		    label = label + celula.getRepresentacao();
		}
		if (celula.getColuna().getAcumulada()) {
		    valor = (Number)celula.getDado();
		}
	    }
	    pieModel.set(label, valor);
	}

	String titulo = "";
	for (Coluna coluna: colunas) {
	    if (coluna.getAgrupada()) {
		if (titulo != "") {
		    titulo += " X ";
		}
		titulo = titulo + coluna.getTitulo();
	    }
	}
	pieModel.setTitle(titulo);
	pieModel.setLegendPosition("E");
	//pieModel.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
    }	

    public PieChartModel getPieModel() {
	return pieModel;
    }
}
