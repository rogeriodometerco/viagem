package estatistica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Coamo Agroindustrial Cooperativa 
 *
 * @author rdometerco@coamo.com.br 
 * @creation  22/12/2016 11:00:08 
 */
public class Bkp_Colecao {

	private Object[] filtros;
	private Object[][] dados;
	private Object[] opcoesParaFiltro;

	public Bkp_Colecao() {
		dados = new Object[4][2];

		opcoesParaFiltro = new Object[dados.length];
		for (int i = 0; i < opcoesParaFiltro.length; i++) {
			opcoesParaFiltro[i] = new HashSet<Object>();
		}
		
		set(0, 0, new Pessoa("Rogerio"));
		set(0, 1, new Date());
		set(1, 0, new Pessoa("Thiago"));
		set(1, 1, new Date());
		set(2, 0, new Pessoa("Pedro"));
		set(2, 1, new Date());
		set(3, 0, new Pessoa("Pedro"));
		set(3, 1, new Date());

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		List<Bkp_FiltroPessoa> filtrosPessoa = new ArrayList<Bkp_FiltroPessoa>();
		filtrosPessoa.add(new Bkp_FiltroPessoa("T"));
		filtrosPessoa.add(new Bkp_FiltroPessoa("g"));
		filtrosPessoa.add(new Bkp_FiltroPessoa("o"));

		filtros = new Object[2];
		filtros[0] = filtrosPessoa;
		filtros[1] = new ArrayList<Bkp_FiltroData>(Arrays.asList(new Bkp_FiltroData(calendar.getTime(), new Date())));

	}

	private void set(int lin, int col, Object o) {
		dados[lin][col] = o;
		garantirOpcaoDeFiltro(col, o);
	}
	
	private void garantirOpcaoDeFiltro(int col, Object o) {
		((Set<Object>)opcoesParaFiltro[col]).add(o);
	}
	
	private List<Bkp_Filtro> montarFiltros(int col) {
		Set<Object> opcoes = (Set<Object>)opcoesParaFiltro[col];
		for (Object o: opcoes) {
			Bkp_Filtro f = new Bkp_FiltroPessoa("rogerio");
			//f.adicionarOpcao(o);
		}
		return null;
	}
	
	public void listar() {
		for (int lin = 0; lin < dados.length; lin++) {
			boolean ok = true;
			for (int col = 0; col < dados[lin].length; col++) {
				for (int i = 0; i < filtros.length; i++) {
					boolean colunaFiltrada = false;
					for (Bkp_Filtro f: (List<Bkp_Filtro>)filtros[col]) {
						if (f.filtrar(dados[lin][col])) {
							colunaFiltrada = true;
						}
					}
					if (!colunaFiltrada) {
						ok = false;
					}
				}
			}
			if (ok) {
				System.out.println("Filtrou: " + ((Pessoa)dados[lin][0]).getNome() + " " + dados[lin][1]);
			}
		}
	}
	
	public void agrupar() {
		List<Object[]> result = new ArrayList<Object[]>();
		
		for (int lin = 0; lin < dados.length; lin++) {
			Object[] linha = dados[lin];
			Bkp_FiltroPessoa filtro = new Bkp_FiltroPessoa(((Pessoa)linha[0]).getNome());
			
			
			boolean achou = false;
			Object[] novo = null;
			for (Object[] agrupado: result) {
				if (filtro.filtrar(((Pessoa)agrupado[0]))) {
					achou = true;
					novo = agrupado;
					break;
				}
			}
			if (!achou) {
				novo = new Object[2];
				novo[0] = linha[0];
				novo[1] = 0;
				result.add(novo);
			} 
			novo[1] = 1 + (int)novo[1];
		}
		for (Object[] linha: result) {
			System.out.println(((Pessoa)linha[0]).getNome() + " - " + linha[1]);
		}
	}
}
