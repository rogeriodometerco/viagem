package modelo;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Veiculo {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private TipoVeiculo tipo;
	
	private Integer pesoBruto;
	private Integer pesoTara;
	private String placa;

	@ManyToOne
	private Conta conta;
	
	@OneToMany(mappedBy="veiculo", fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
	@OrderBy(value="posicaoNoVeiculo")
	private Set<ComponenteVeiculo> componentes;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getPlaca() {
		// TODO Deixar lógica conforme modelo
		if (placa != null && placa.length() > 0) {
			return placa;
		}
		for (ComponenteVeiculo componente: componentes) {
			if (componente.getPosicaoNoVeiculo() == 1) {
				return componente.getPlaca();
			}
		}
		return null;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
	public Integer getPesoBruto() {
		return pesoBruto;
	}

	public void setPesoBruto(Integer pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	public Integer getPesoTara() {
		return pesoTara;
	}

	public void setPesoTara(Integer pesoTara) {
		this.pesoTara = pesoTara;
	}

	public String toStringDescricao() {
		// TODO Deixar lógica conforme modelo
		StringBuffer sb = new StringBuffer();
		//sb.append(placa != null ? placa : "");

		for (ComponenteVeiculo componente: componentes) {
			if (sb.length() > 0) {
				sb.append(" - ");
			}
			sb.append(componente.getPlaca());
		}
		return sb.toString();
	}
	
	public Integer calcularPesoLiquido() {
		if (pesoBruto != null && pesoTara != null) {
			return pesoBruto - pesoTara;
		}
		return 0;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public TipoVeiculo getTipo() {
		return tipo;
	}

	public void setTipo(TipoVeiculo tipo) {
		this.tipo = tipo;
	}

	public Set<ComponenteVeiculo> getComponentes() {
		return componentes;
	}

	public void setComponentes(Set<ComponenteVeiculo> componentes) {
		this.componentes = componentes;
	}

	public void setComponente(int posicaoNoVeiculo, ComponenteVeiculo componente) {
		Set<ComponenteVeiculo> novaLista = new LinkedHashSet<ComponenteVeiculo>();
		Iterator<ComponenteVeiculo> it = componentes.iterator();
		int i = 1;
		while (it.hasNext()) {
			ComponenteVeiculo item = it.next();
			if (i == posicaoNoVeiculo) {
				item = componente;
				item.setVeiculo(this);
			}
			item.setPosicaoNoVeiculo(i);
			novaLista.add(item);
			i++;
		}
		this.componentes = novaLista;
	}
}
