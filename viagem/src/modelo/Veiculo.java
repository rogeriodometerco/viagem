package modelo;

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

	public TipoVeiculo getTipo() {
		return tipo;
	}

	public void setTipo(TipoVeiculo tipo) {
		this.tipo = tipo;
	}

	@OneToMany(mappedBy="veiculo", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@OrderBy(value="posicaoNoVeiculo")
	private Set<ComponenteVeiculo> componentes;
	
	public Set<ComponenteVeiculo> getComponentes() {
		return componentes;
	}

	public void setComponentes(Set<ComponenteVeiculo> componentes) {
		this.componentes = componentes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
