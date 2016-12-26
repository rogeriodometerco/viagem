package estatistica;

import java.util.Date;

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
		Bkp_Colecao bkp_Colecao = new Bkp_Colecao();
		bkp_Colecao.listar();
		bkp_Colecao.agrupar();
		
/*		
 		Filtro<Date> filtro = new FiltroIntervalo<Date>(new Date(2), new Date());
		System.out.println(filtro.filtrar(new Date(1)));
		Coluna<Date> coluna = new Coluna<Date>();
		coluna.adicionarLinha(DataUtil.extrairDataSemHora(new Date()));
		coluna.adicionarLinha(DataUtil.extrairDataSemMinuto(new Date()));
		coluna.adicionarLinha(DataUtil.somarDias(new Date(), 1));
		coluna.adicionarLinha(DataUtil.somarDias(new Date(), 3));
		coluna.configurarFiltroIntervalo(Coluna.GRANULARIDADE_FILTRO_DIA, 1);
*/	
		Date dataInicial = DataUtil.extrairDataSemHora(new Date());
		Date dataFinal = DataUtil.somarDias(dataInicial, 1);
		
		Filtro<Date> comparador = new Filtro<Date>(new CrivoDataIgual(dataInicial));
		System.out.println(comparador.comparar(dataInicial));

		comparador = new Filtro<Date>(new CrivoDataEntre(dataInicial, dataFinal));
		System.out.println(comparador.comparar(dataInicial));
		System.out.println(comparador.comparar(DataUtil.somarDias(dataInicial, 2)));
	
		new CrivoDataEntre(DataUtil.somarHoras(new Date(), 14), UnidadeDataEnum.HORA, 2);
		
		System.out.println(DataUtil.somarHoras(new Date(), 11));

	}
	

}
