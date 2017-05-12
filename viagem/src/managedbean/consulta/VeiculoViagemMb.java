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
public class VeiculoViagemMb implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<VeiculoViagem> lista;
    
    @PostConstruct
    private void inicializar() {
	listar();
    }
    
    public void listar() {
	lista = new ArrayList<VeiculoViagem>();
	for (int i = 0; i < 20; i++) {
	    VeiculoViagem item = new VeiculoViagem();
	    item.setPlaca("AWC192" + i%10);
	    item.setMotorista(Arrays.asList("José da Silva", "Antônio Siqueira", "Joel Silveira", "André Oliveira", "Marcos Antônio", "Sandro José").get(i%6));
	    item.setStatusViagem(Arrays.asList("Pendente", "Aceita", "Iniciada", "Iniciada", "Concluída", "Recusada").get(i%6));
	    item.setViagem((Long)(i + 234l));
	    item.setDataPrevista(new Date());
	    lista.add(item);
	}
    }

    public List<VeiculoViagem> getLista() {
	if (lista == null) {
	    listar();
	}
        return lista;
    }	

    public void setLista(List<VeiculoViagem> lista) {
        this.lista = lista;
    }
    
    
}
