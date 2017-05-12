package managedbean.consulta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PontualidadeMb implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<Pontualidade> lista;

    @PostConstruct
    private void inicializar() {
	listar();
    }

    public void listar() {
	lista = new ArrayList<Pontualidade>();
	Date d1 = new Date(System.currentTimeMillis()-5000000000l);
	Date d2 = new Date(System.currentTimeMillis()-8000000000l);
	Date d3 = new Date(System.currentTimeMillis()-10000000000l);
	for (int i = 0; i < 20; i++) {
	    Pontualidade item = new Pontualidade();
	    item.setEstabelecimento(Arrays.asList("Paranaguá", "Guarapuava", "AGTL", "Cotriguaçu").get(i%4));
	    item.setTransportador(Arrays.asList("ATDL", "Buturi", "Tindiana", "Tindiana", "Mendes").get(i%5));
	    item.setTomador(Arrays.asList("Coamo", "Cocamar", "Cotriguaçu").get(i%3));
	    item.setPlaca(Arrays.asList("AWA2120", "AWC3933", "BWA8585").get(i%3));
	    item.setMotorista(Arrays.asList("João da Silva", "José Alves", "Antônio Siqueira").get(i%3));
	    item.setDataAcordada(Arrays.asList(d1, d2, d3).get(i%3));
	    item.setDataPrevista(Arrays.asList(d3, d1, d2).get(i%3));
	    item.setDataChegada(Arrays.asList(d2, d1, d3).get(i%3));
	    item.setAtraso(Arrays.asList("Sim", "Não", "Não", "Sim", "Não").get(i%5));
	    item.setPrevisoes(Arrays.asList(1, 3, 2, 2, 1, 1, 2).get(i%7));
	    lista.add(item);
	}
    }

    public List<Pontualidade> getLista() {
	if (lista == null) {
	    listar();
	}
	return lista;
    }	

    public void setLista(List<Pontualidade> lista) {
	this.lista = lista;
    }



    public class Pontualidade {
	private String transportador;
	private String tomador;
	private String Estabelecimento;
	private String placa;
	private String motorista;
	private Date dataAcordada;
	private Date dataPrevista;
	private Date dataChegada;
	private String atraso;
	private Integer previsoes;

	public String getPlaca() {
	    return placa;
	}
	public void setPlaca(String placa) {
	    this.placa = placa;
	}
	public String getMotorista() {
	    return motorista;
	}
	public void setMotorista(String motorista) {
	    this.motorista = motorista;
	}
	public Date getDataPrevista() {
	    return dataPrevista;
	}
	public void setDataPrevista(Date dataPrevista) {
	    this.dataPrevista = dataPrevista;
	}
	public String getTransportador() {
	    return transportador;
	}
	public void setTransportador(String transportador) {
	    this.transportador = transportador;
	}
	public String getTomador() {
	    return tomador;
	}
	public void setTomador(String tomador) {
	    this.tomador = tomador;
	}
	public Date getDataAcordada() {
	    return dataAcordada;
	}
	public void setDataAcordada(Date dataAcordada) {
	    this.dataAcordada = dataAcordada;
	}
	public Date getDataChegada() {
	    return dataChegada;
	}
	public void setDataChegada(Date dataChegada) {
	    this.dataChegada = dataChegada;
	}
	public String getEstabelecimento() {
	    return Estabelecimento;
	}
	public void setEstabelecimento(String estabelecimento) {
	    Estabelecimento = estabelecimento;
	}
	public String getAtraso() {
	    return atraso;
	}
	public void setAtraso(String atraso) {
	    this.atraso = atraso;
	}
	public Integer getPrevisoes() {
		return previsoes;
	}
	public void setPrevisoes(Integer previsoes) {
		this.previsoes = previsoes;
	}    

    }
}
