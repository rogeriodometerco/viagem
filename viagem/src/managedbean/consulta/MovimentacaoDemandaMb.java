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
public class MovimentacaoDemandaMb implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<Item> lista;
    
    @PostConstruct
    private void inicializar() {
	listar();
    }
    
    public void listar() {
	lista = new ArrayList<Item>();
	for (int i = 0; i < 20; i++) {
	    Item item = new Item();
	    item.setDemandaId(Long.valueOf(347 + i));
	    item.setOrigem(Arrays.asList("Campo Mourão", "Peabiru", "Araruna", "Farol").get(i%4));
	    item.setDestino(Arrays.asList("Paranaguá", "Guarapuava", "AGTL", "Cotriguaçu").get(i%4));
	    item.setProduto(Arrays.asList("Soja", "Milho", "Farelo", "Trigo").get(i%4));
	    item.setQuantidade("1200" + i%3 * 120 + " TN");
	    item.setCargasHoje(i%4);
	    item.setAgendadoHoje(i%5);
	    item.setAgendadoAmanha(i%3);
	    item.setAgendadoFuturo(i%5);
	    item.setTomador(Arrays.asList("Coamo", "Cocamar", "Cotriguaçu", "Coopavel").get(i%4));
	    lista.add(item);
	}
    }

    public List<Item> getLista() {
	if (lista == null) {
	    listar();
	}
        return lista;
    }	

    public void setLista(List<Item> lista) {
        this.lista = lista;
    }
    
    
    
    
    
    public class Item {
	public Long demandaId;
	public String origem;
	public String destino;
	public String produto;
	public String quantidade;
	public Integer cargasHoje;
	public Integer noLocal;
	public Integer agendadoHoje;
	public Integer agendadoAmanha;
	public Integer agendadoFuturo;
	public String tomador;
	
	public Long getDemandaId() {
	    return demandaId;
	}
	public void setDemandaId(Long demandaId) {
	    this.demandaId = demandaId;
	}
	public String getOrigem() {
	    return origem;
	}
	public void setOrigem(String origem) {
	    this.origem = origem;
	}
	public String getDestino() {
	    return destino;
	}
	public void setDestino(String destino) {
	    this.destino = destino;
	}
	public String getProduto() {
	    return produto;
	}
	public void setProduto(String produto) {
	    this.produto = produto;
	}
	public String getQuantidade() {
	    return quantidade;
	}
	public void setQuantidade(String quantidade) {
	    this.quantidade = quantidade;
	}
	public Integer getCargasHoje() {
	    return cargasHoje;
	}
	public void setCargasHoje(Integer cargasHoje) {
	    this.cargasHoje = cargasHoje;
	}
	public Integer getNoLocal() {
	    return noLocal;
	}
	public void setNoLocal(Integer noLocal) {
	    this.noLocal = noLocal;
	}
	public Integer getAgendadoHoje() {
	    return agendadoHoje;
	}
	public void setAgendadoHoje(Integer agendadoHoje) {
	    this.agendadoHoje = agendadoHoje;
	}
	public Integer getAgendadoAmanha() {
	    return agendadoAmanha;
	}
	public void setAgendadoAmanha(Integer agendadoAmanha) {
	    this.agendadoAmanha = agendadoAmanha;
	}
	public Integer getAgendadoFuturo() {
	    return agendadoFuturo;
	}
	public void setAgendadoFuturo(Integer agendadoFuturo) {
	    this.agendadoFuturo = agendadoFuturo;
	}
	public String getTomador() {
	    return tomador;
	}
	public void setTomador(String tomador) {
	    this.tomador = tomador;
	}
	
	
    }
}
