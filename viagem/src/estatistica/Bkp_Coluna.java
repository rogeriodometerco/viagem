package estatistica;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import util.DataUtil;

public class Bkp_Coluna<T> {

	public static String GRANULARIDADE_FILTRO_EXATO = "EXATO";
	public static String GRANULARIDADE_FILTRO_DIA = "DIA";
	public static String GRANULARIDADE_FILTRO_HORA = "HORA";
	public static String GRANULARIDADE_FILTRO_UNIDADE = "UNIDADE";
	

	private String granularidadeFiltro;
	private int intervaloFiltro;
	private List<Bkp_Filtro<T>> bkp_Filtros;
	private List<T> elementos;
	
	public Bkp_Coluna() {
		elementos = new ArrayList<T>();
		bkp_Filtros = new ArrayList<Bkp_Filtro<T>>();
	}
	
	public void adicionarLinha(T elemento) {
		elementos.add(elemento);
	}
	
	public void configurarFiltroPontual() {
		this.granularidadeFiltro = GRANULARIDADE_FILTRO_EXATO;
		this.intervaloFiltro = 0;
		criarFiltros();
		
/*		for (T elemento: getElementosSemRepeticao()) {
			filtros.add(new FiltroPontual<T>(elemento));
		}
*/	}
	
	public void configurarFiltroIntervalo(String granularidade, Integer intervalo) {
		this.granularidadeFiltro = granularidade;
		this.intervaloFiltro = intervalo;
		criarFiltros();
/*		for (T elemento: getElementosSemRepeticao()) {
			Date[] periodo = obterIntervaloEmData((Date)elemento);
			filtros.add(new FiltroIntervalo(periodo[0], periodo[1]));
		}
*/		
	}
	
	public void criarFiltros() {
		Bkp_Filtro<T> filtro = null;
		for (T elemento: getElementosSemRepeticao()) {
			if (this.granularidadeFiltro.equals(GRANULARIDADE_FILTRO_EXATO)) {
				filtro = new Bkp_FiltroPontual<T>(elemento);
			} else {
				Object[] range = criarIntervalo(elemento);
				filtro = new Bkp_FiltroIntervalo<T>((T)range[0], (T)range[1]);
			}
			Date[] periodo = obterIntervaloEmData((Date)elemento);
			bkp_Filtros.add(new Bkp_FiltroIntervalo(periodo[0], periodo[1]));
		}
		for (Bkp_Filtro f: bkp_Filtros) {
			System.out.println((Date)((Bkp_FiltroIntervalo)f).getInicioIntervalo() + " - " + (Date)((Bkp_FiltroIntervalo)f).getFimIntervalo());
		}
	}

	private Object[] criarIntervalo(Object objeto) {
		if (objeto.getClass().equals(Date.class)) {
			Date[] intervalo = new Date[2];
			Date dataInicial = (Date)objeto;
			Date dataFinal = null;
			if (granularidadeFiltro.equals(GRANULARIDADE_FILTRO_DIA)) {
				dataInicial = DataUtil.extrairDataSemHora((Date)objeto);
				dataFinal = DataUtil.somarDias(dataInicial, intervaloFiltro);
			} else {
				dataInicial = DataUtil.extrairDataSemMinuto((Date)objeto);
				dataFinal = DataUtil.somarHoras(dataInicial, intervaloFiltro);
			}
			intervalo[0] = dataInicial;
			intervalo[1] = dataFinal;
			return intervalo;
			
		}
		return null;
	}
	
	public List<T> getElementosSemRepeticao() {
		return new ArrayList<T>(new HashSet<T>(elementos));
	}
	
	private Date[] obterIntervaloEmData(Date data) {
		Date[] intervalo = new Date[2];
		Date dataInicial = (Date)data;
		Date dataFinal = null;
		if (granularidadeFiltro.equals(GRANULARIDADE_FILTRO_DIA)) {
			dataInicial = DataUtil.extrairDataSemHora(data);
			dataFinal = DataUtil.somarDias(dataInicial, intervaloFiltro);
		} else {
			dataInicial = DataUtil.extrairDataSemMinuto(data);
			dataFinal = DataUtil.somarHoras(dataInicial, intervaloFiltro);
		}
		intervalo[0] = dataInicial;
		intervalo[1] = dataFinal;
		return intervalo;
	}
}
