package estatistica;

import java.util.HashSet;
import java.util.Set;

/**
 * Coamo Agroindustrial Cooperativa 
 *
 * @author rdometerco@coamo.com.br 
 * @creation  22/12/2016 11:09:14 
 */
public class Teste {

	/**
	 *
	 * @author rdometerco@coamo.com.br 
	 * @creation  22/12/2016 11:09:14 
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		Teste teste = new Teste();
		teste.testar();
	}
	
	public void testar() {
		Colecao colecao = new Colecao();
		colecao.listar();
		colecao.agrupar();

	}
	

}
