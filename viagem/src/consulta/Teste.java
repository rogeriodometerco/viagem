package consulta;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Coamo Agroindustrial Cooperativa 
 *
 * @author rdometerco@coamo.com.br 
 * @creation  28/12/2016 16:42:02 
 */
public class Teste {

	public static void main(String[] args) {
		Teste teste = new Teste();
		teste.testar();
	}
	
	public void testar() {
		
		List<Coluna> colunas = new ArrayList<Coluna>();
		Coluna colunaNome = new Coluna();
		Coluna colunaData = new Coluna();
		Coluna colunaNumero = new Coluna();
		colunas.add(colunaNome);
		colunas.add(colunaData);
		colunas.add(colunaNumero);
		
		Date date = new Date();
		
		Matriz matriz = new Matriz(colunas);
		
		for (int i = 1; i <= 3; i++) {
			Celula nome = new CelulaString();
			Celula data = new CelulaData();
			Celula numero = new CelulaNumero();
	
			nome.setDado("Rogério");
			data.setDado(date);
			numero.setDado(i);

			List<Celula> celulas = new ArrayList<Celula>();
			celulas.add(nome);
			celulas.add(data);
			celulas.add(numero);
			matriz.criarLinha(celulas);
		}

		colunaNome.setAgrupada(true);
		colunaData.setAgrupada(true);
		colunaNumero.setAcumulada(true);
		
		Matriz agrupada = matriz.agrupar();
		for (Linha linha: agrupada.getLinhas()) {
			System.out.println(linha.getString());
		}

	}

}
