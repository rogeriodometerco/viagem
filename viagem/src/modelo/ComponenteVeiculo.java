package modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ComponenteVeiculo {

	@Id
	@GeneratedValue
	private Long id;
	
	private String placa;
	
	private Integer quantidadeEixos;
	
	@ManyToOne
	private TipoCarroceria tipoCarroceria;
	
	@ManyToOne
	private Veiculo veiculo;
	
	private Integer posicaoNoVeiculo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Integer getQuantidadeEixos() {
		return quantidadeEixos;
	}

	public void setQuantidadeEixos(Integer quantidadeEixos) {
		this.quantidadeEixos = quantidadeEixos;
	}

	public TipoCarroceria getTipoCarroceria() {
		return tipoCarroceria;
	}

	public void setTipoCarroceria(TipoCarroceria tipoCarroceria) {
		this.tipoCarroceria = tipoCarroceria;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Integer getPosicaoNoVeiculo() {
		return posicaoNoVeiculo;
	}

	public void setPosicaoNoVeiculo(Integer posicaoNoVeiculo) {
		this.posicaoNoVeiculo = posicaoNoVeiculo;
	}

}
