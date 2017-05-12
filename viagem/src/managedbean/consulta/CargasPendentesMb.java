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
public class CargasPendentesMb implements Serializable {

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
	    item.setProduto(Arrays.asList("Soja", "Soja", "Milho", "Farelo", "Trigo").get(i%5));
	    item.setQuantidade(Arrays.asList(35, 34, 37, 36).get(i%4));
	    item.setTransportador(Arrays.asList("ATDL", "Buturi", "Tindiana", "Tindiana", "Mendes").get(i%5));
	    item.setTomador(Arrays.asList("Coamo", "Cocamar", "Cotriguaçu").get(i%3));
	    item.setStatus(Arrays.asList("Pendente", "Pendente", "Carregado", "Carregado", "Trânsito").get(i%5));
	    item.setPlaca(Arrays.asList("AWA2120", "AWC3933", "BWA8585").get(i%3));
	    item.setMotorista(Arrays.asList("João da Silva", "José Alves", "Antônio Siqueira").get(i%3));
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
	public Integer quantidade;
	public String status;
	public String placa;
	public String motorista;
	public String transportador;
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
	public Integer getQuantidade() {
	    return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
	    this.quantidade = quantidade;
	}
	public String getStatus() {
	    return status;
	}
	public void setStatus(String status) {
	    this.status = status;
	}
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
	
    }
}
