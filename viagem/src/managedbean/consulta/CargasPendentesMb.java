package managedbean.consulta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import consulta.Celula;
import consulta.CelulaNumero;
import consulta.CelulaString;
import consulta.Coluna;
import consulta.Matriz;

@ManagedBean
@ViewScoped
public class CargasPendentesMb implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<Item> lista;
    private Matriz matriz;
    private Matriz porTransportador;
    private Matriz porDestino;
    private Matriz porDestinoProduto;

    @PostConstruct
    private void inicializar() {
	listar();
	inicializarMatriz();
    }

    public void listar() {
	lista = new ArrayList<Item>();
	for (int i = 0; i < 20; i++) {
	    Item item = new Item();
	    item.setDemandaId(Long.valueOf(347 + i));
	    item.setOrigem(Arrays.asList("Campo Mourão", "Peabiru", "Araruna", "Farol").get(i%3));
	    item.setDestino(Arrays.asList("Paranaguá", "Guarapuava", "AGTL", "Cotriguaçu").get(i%3));
	    item.setProduto(Arrays.asList("Soja", "Soja", "Milho", "Farelo", "Trigo").get(i%4));
	    item.setQuantidade(35);
	    item.setTransportador(Arrays.asList("ATDL", "Buturi", "Tindiana", "Tindiana", "Mendes").get(i%4));
	    item.setTomador(Arrays.asList("Coamo", "Cocamar", "Cotriguaçu").get(i%2));
	    item.setStatus(Arrays.asList("Pendente", "Pendente", "Carregado", "Carregado", "Trânsito").get(i%5));
	    item.setPlaca(Arrays.asList("AWA2120", "AWC3933", "BWA8585").get(i%2));
	    item.setMotorista(Arrays.asList("João da Silva", "José Alves", "Antônio Siqueira").get(i%2));
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







    private void inicializarMatriz() {
	List<Coluna> colunas = new ArrayList<Coluna>();
	Coluna colunaStatus = new Coluna();
	Coluna colunaOrigem = new Coluna();
	Coluna colunaDestino = new Coluna();
	Coluna colunaProduto = new Coluna();
	Coluna colunaTomador = new Coluna();
	Coluna colunaTransportador = new Coluna();
	Coluna colunaPendente = new Coluna();
	Coluna colunaCarregado = new Coluna();
	Coluna colunaTransito = new Coluna();

	colunas.add(colunaStatus);
	colunas.add(colunaOrigem);
	colunas.add(colunaDestino);
	colunas.add(colunaProduto);
	colunas.add(colunaTomador);
	colunas.add(colunaTransportador);
	colunas.add(colunaPendente);
	colunas.add(colunaCarregado);
	colunas.add(colunaTransito);

	colunaStatus.setTitulo("Status");
	colunaOrigem.setTitulo("Origem");
	colunaDestino.setTitulo("Destino");
	colunaProduto.setTitulo("Produto");
	colunaTomador.setTitulo("Tomador");
	colunaTransportador.setTitulo("Transportador");
	colunaPendente.setTitulo("Pendente");
	colunaCarregado.setTitulo("Carregado");
	colunaTransito.setTitulo("Trânsito");

	Date date = new Date();

	this.matriz = new Matriz(colunas);

	String nomeStr = "";
	for (Item item: lista) {
	    Celula status = new CelulaString();
	    Celula origem = new CelulaString();
	    Celula destino = new CelulaString();
	    Celula produto = new CelulaString();
	    Celula tomador = new CelulaString();
	    Celula transportador = new CelulaString();
	    Celula pendente = new CelulaNumero();
	    Celula carregado = new CelulaNumero();
	    Celula transito = new CelulaNumero();

	    status.setDado(item.getStatus());
	    origem.setDado(item.getOrigem());
	    destino.setDado(item.getDestino());
	    produto.setDado(item.getProduto());
	    tomador.setDado(item.getTomador());
	    transportador.setDado(item.getTransportador());
	    pendente.setDado(item.getStatus().equals("Pendente") ? 1 : 0);
	    carregado.setDado(item.getStatus().equals("Carregado") ? 1 : 0);
	    transito.setDado(item.getStatus().equals("Trânsito") ? 1 : 0);


	    List<Celula> celulas = new ArrayList<Celula>();
	    celulas.add(status);
	    celulas.add(origem);
	    celulas.add(destino);
	    celulas.add(produto);
	    celulas.add(tomador);
	    celulas.add(transportador);
	    celulas.add(pendente);
	    celulas.add(carregado);
	    celulas.add(transito);

	    matriz.criarLinha(celulas);
	}

	colunaPendente.setAcumulada(true);
	colunaCarregado.setAcumulada(true);
	colunaTransito.setAcumulada(true);

	colunaTransportador.setAgrupada(true);
	this.porTransportador = matriz.agrupar();
	colunaTransportador.setAgrupada(false);

	colunaDestino.setAgrupada(true);
	this.porDestino = matriz.agrupar();

	colunaProduto.setAgrupada(true);
	colunaDestino.setAgrupada(true);
	this.porDestinoProduto = matriz.agrupar();

    }

    public Matriz getMatriz() {
	return matriz;
    }

    public void agrupar() {
	porTransportador = matriz.agrupar();
    }

    public Matriz getPorTransportador() {
	return porTransportador;
    }    

    public Matriz getPorDestino() {
	return porDestino;
    }

    public void setPorDestino(Matriz porDestino) {
	this.porDestino = porDestino;
    }

    public Matriz getPorDestinoProduto() {
	return porDestinoProduto;
    }

    public void setPorDestinoProduto(Matriz porDestinoProduto) {
	this.porDestinoProduto = porDestinoProduto;
    }

    public void setPorTransportador(Matriz porTransportador) {
	this.porTransportador = porTransportador;
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
