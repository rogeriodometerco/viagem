package managedbean.consulta;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;

/**
 * Coamo Agroindustrial Cooperativa 
 *
 * @author rdometerco@coamo.com.br 
 * @creation  12/05/2017 15:27:07 
 */
@ManagedBean
@ViewScoped
public class DashboardMb {
	private DashboardModel model;
	private LineChartModel lineModel1;
	private List<Object[]> operacoes;
	private List<Object[]> veiculosChegando;
	private PieChartModel cargasPieModel;
	private HorizontalBarChartModel barModel;

	@PostConstruct
	public void init() {
		createLineModels();
		inicializarUltimasOperacoes();
		inicializarVeiculosChegando();
		createPieModel();
		initBarModel();

		model = new DefaultDashboardModel();
		DashboardColumn column1 = new DefaultDashboardColumn();
		DashboardColumn column2 = new DefaultDashboardColumn();
		DashboardColumn column3 = new DefaultDashboardColumn();
		DashboardColumn column4 = new DefaultDashboardColumn();
		DashboardColumn column5 = new DefaultDashboardColumn();

		column1.addWidget("noLocal");
		column1.addWidget("alocado");
		column1.addWidget("naoIniciada");
		column1.addWidget("semViagem");
		column1.addWidget("proximaHora");
		column1.addWidget("atraso");

		column2.addWidget("transito");
		column2.addWidget("demandasHabilitadas");

		model.addColumn(column1);
		model.addColumn(column2);
		model.addColumn(column3);
		model.addColumn(column4);
		model.addColumn(column5);
	}

	public DashboardModel getModel() {
		return model;
	}

	private void createLineModels() {
		lineModel1 = initCategoryModel();
		lineModel1.setTitle("Operações últimas 24 horas");
		lineModel1.setLegendPosition("e");
		lineModel1.setShowPointLabels(true);
		lineModel1.getAxes().put(AxisType.X, new CategoryAxis("Hora"));
		Axis yAxis = lineModel1.getAxis(AxisType.Y);
		yAxis = lineModel1.getAxis(AxisType.Y);
		yAxis.setLabel("Qtde");
		yAxis.setMin(0);
		yAxis.setMax(15);
	}

	private LineChartModel initCategoryModel() {
		LineChartModel model = new LineChartModel();

		ChartSeries boys = new ChartSeries();
		boys.setLabel("Coletas");
		boys.set("6", 1);
		boys.set("7", 0);
		boys.set("8", 3);
		boys.set("9", 8);
		boys.set("10", 2);
		boys.set("11", 5);
		boys.set("12", 5);
		boys.set("13", 7);
		boys.set("14", 11);
		boys.set("15", 8);
		boys.set("16", 13);
		boys.set("17", 9);

		ChartSeries girls = new ChartSeries();
		girls.setLabel("Entregas");
		girls.set("6", 3);
		girls.set("7", 2);
		girls.set("8", 1);
		girls.set("9", 7);
		girls.set("10", 3);
		girls.set("11", 6);
		girls.set("12", 11);
		girls.set("13", 4);
		girls.set("14", 6);
		girls.set("15", 4);
		girls.set("16", 9);
		girls.set("17", 4); 

		model.addSeries(boys);
		model.addSeries(girls);

		return model;
	}

	public LineChartModel getLineModel1() {
		return lineModel1;
	}    


	private void inicializarUltimasOperacoes() {
		operacoes = new ArrayList<Object[]>();
		operacoes.add(new Object[]{"22/05/207 08:05", "Início de viagem", "AWA2102"});
		operacoes.add(new Object[]{"22/05/207 08:08", "Chegada do veículo", "Paranaguá"});
		operacoes.add(new Object[]{"22/05/207 08:36", "Início de viagem", "AWC8585"});
		operacoes.add(new Object[]{"22/05/207 09:05", "Previsão de chegada", "Campo Mourão"});
		operacoes.add(new Object[]{"22/05/207 10:12", "Aceite de viagem", "José da Silva"});
		operacoes.add(new Object[]{"22/05/207 10:43", "Viagem concluída", "João Maria"});
		operacoes.add(new Object[]{"22/05/207 11:26", "Viagem criada", "Antônio Silveira"});
		operacoes.add(new Object[]{"22/05/207 11:32", "Término de operação", "Soja"});
	}

	public List<Object[]> getOperacoes() {
		return operacoes;
	}

	private void inicializarVeiculosChegando() {
		veiculosChegando = new ArrayList<Object[]>();
		veiculosChegando.add(new Object[]{"08:05", "Coamo Araruna", "AWA2102"});
		veiculosChegando.add(new Object[]{"08:08", "Paranaguá", "ABC0392"});
		veiculosChegando.add(new Object[]{"08:36", "AGTL", "AWC8585"});
		veiculosChegando.add(new Object[]{"09:05", "C Vale Campo Mourão", "BWA0875"});
		veiculosChegando.add(new Object[]{"10:12", "Coamo Mamborê", "AWB3940"});
		veiculosChegando.add(new Object[]{"10:43", "Cidasc", "AKS7878"});
		veiculosChegando.add(new Object[]{"11:26", "Rocha Paranaguá", "ABN7831"});
		veiculosChegando.add(new Object[]{"11:32", "Indústria de Óleo", "MEY2039"});
	}


	private void createPieModel() {
		cargasPieModel = new PieChartModel();
		cargasPieModel.set("Buturi", 16);
		cargasPieModel.set("ATDL", 32);
		cargasPieModel.set("Mendes", 15);
		cargasPieModel.set("Transvidal", 7);
		cargasPieModel.set("Zanella", 43);
		cargasPieModel.set("Coamo", 53);
		//cargasPieModel.setTitle("Cargas por transportador");

	}

	public PieChartModel getCargasPieModel() {
		return cargasPieModel;
	}


	private void initBarModel() {
		barModel = new HorizontalBarChartModel();

		ChartSeries boys = new ChartSeries();
//		boys.set("Agotran", 14);
//		boys.set("Buturi", 15);
		boys.set("Rincão", 22);
		boys.set("ATDL", 26);
		boys.set("Schon", 29);
		boys.set("G10", 30);
		boys.set("Tindiana", 35);
		boys.set("Transvidal", 43);
		boys.set("Mendes", 48);
		boys.set("Coamo", 53);
		barModel.setTitle("Cargas por transportador - top 8");

		barModel.addSeries(boys);
	}

	public BarChartModel getBarModel() {
		return barModel;
	}

	public List<Object[]> getVeiculosChegando() {
		return veiculosChegando;
	}
}
