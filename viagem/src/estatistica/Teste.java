package estatistica;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import util.DataUtil;

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
		Date dataInicial = DataUtil.extrairDataSemHora(new Date());
		Date dataMetade = DataUtil.somarDias(dataInicial, 1);
		Date dataFinal = DataUtil.somarDias(dataMetade, 1);
		
		Coluna<Date> coluna = new Coluna<Date>(new FiltroDataEntre(EscalaTempo.HORA, 24));
		//coluna.adicionarLinha(dataInicial);
		//coluna.adicionarLinha(dataMetade);
		coluna.adicionarLinha(DataUtil.somarHoras(dataInicial, 4));
		coluna.adicionarLinha(DataUtil.somarHoras(dataInicial, 7));
		coluna.adicionarLinha(DataUtil.somarHoras(dataInicial, 13));
		coluna.adicionarLinha(DataUtil.somarHoras(dataInicial, 31));
		coluna.adicionarLinha(dataMetade);
		coluna.adicionarLinha(dataFinal);
		
		System.out.println(coluna.getFiltro().getCrivos().size());
		
		System.out.println("Nova coluna:");
		coluna = new Coluna<Date>(new FiltroDataEntre(EscalaTempo.HORA, 24));
		//coluna.adicionarLinha(dataInicial);
		//coluna.adicionarLinha(dataMetade);
		coluna.adicionarLinha(DataUtil.somarHoras(dataInicial, 4));
		coluna.adicionarLinha(DataUtil.somarHoras(dataInicial, 7));
		coluna.adicionarLinha(DataUtil.somarHoras(dataInicial, 13));
		coluna.adicionarLinha(DataUtil.somarHoras(dataInicial, 31));
		coluna.adicionarLinha(dataMetade);
		coluna.adicionarLinha(dataFinal);
		
		System.out.println(coluna.getFiltro().getCrivos().size());

		for (int i = 0; i < 10000; i++) {
			coluna.adicionarLinha(DataUtil.somarHoras(dataInicial, i));
		}
		System.out.println(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
		coluna.filtrar();
		System.out.println(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
		System.out.println(coluna.getFiltro().getCrivos().size());
		
/*		
		Filtro<Date> comparador = new FiltroDataIgual<Date>(new CrivoDataIgual(dataInicial));
		System.out.println(comparador.comparar(dataInicial));

		comparador = new Filtro<Date>(new CrivoDataEntre(dataInicial, dataFinal));
		System.out.println(comparador.comparar(dataInicial));
		System.out.println(comparador.comparar(DataUtil.somarDias(dataInicial, 2)));
	
		Set<Crivo<Date>> crivos = new HashSet<Crivo<Date>>();
		Date data1 = DataUtil.somarHoras(dataInicial, 14);
		Date data2 = DataUtil.somarHoras(dataInicial, 14);
		
		crivos.add(new CrivoDataEntre(data1, EscalaTempo.HORA, 2));
		crivos.add(new CrivoDataEntre(data2, EscalaTempo.HORA, 2));
		//crivos.add(new CrivoDataEntre(new Date(), new Date()));

		System.out.println(crivos.size());
*/
	}
	

}
