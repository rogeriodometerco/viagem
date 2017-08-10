package modelo;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import enums.StatusViagem;

@Entity
public class Viagem {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private Conta transportador;
	
	@ManyToOne
	private Conta motorista;
	
	@ManyToOne
	private Veiculo veiculo;
	
	@OneToMany(mappedBy="viagem", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Set<EtapaEntrega> etapas;
	
	@OneToMany(mappedBy="viagem", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@OrderBy("ordem ASC")
	private Set<PontoViagem> pontos;

	private StatusViagem status;
	
	private Date dataHoraStatus;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Conta getTransportador() {
		return transportador;
	}

	public void setTransportador(Conta transportador) {
		this.transportador = transportador;
	}

	public Conta getMotorista() {
		return motorista;
	}

	public void setMotorista(Conta motorista) {
		this.motorista = motorista;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Set<EtapaEntrega> getEtapas() {
		return etapas;
	}

	public void setEtapas(Set<EtapaEntrega> etapas) {
		this.etapas = etapas;
	}

	public Set<PontoViagem> getPontos() {
		return pontos;
	}

	public void setPontos(Set<PontoViagem> pontos) {
		this.pontos = pontos;
	}

	public StatusViagem getStatus() {
		return status;
	}

	public void setStatus(StatusViagem stauts) {
		this.status = stauts;
	}

	public Date getDataHoraStatus() {
		return dataHoraStatus;
	}

	public void setDataHoraStatus(Date dataHoraStatus) {
		this.dataHoraStatus = dataHoraStatus;
	}

	public String toStringMunicipiosOrigem() {
		StringBuffer sb = new StringBuffer();
		for (EtapaEntrega etapa: etapas) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(etapa.getOrigem().getEndereco().getMunicipio().getNome() 
					+ "-" + etapa.getOrigem().getEndereco().getMunicipio().getUf().getAbreviatura());
		}
		return sb.toString();
	}

	public String toStringMunicipiosDestino() {
		StringBuffer sb = new StringBuffer();
		for (EtapaEntrega etapa: etapas) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(etapa.getDestino().getEndereco().getMunicipio().getNome() 
					+ "-" + etapa.getDestino().getEndereco().getMunicipio().getUf().getAbreviatura());
		}
		return sb.toString();
	}

	public String toStringProdutos() {
		StringBuffer sb = new StringBuffer();
		for (EtapaEntrega etapa: etapas) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(etapa.getEntrega().getProduto().getNome());
		}
		return sb.toString();
	}
	
	public Boolean pendente() {
		return status.equals(StatusViagem.PENDENTE);
	}

	public Boolean recusada() {
		return status.equals(StatusViagem.RECUSADA);
	}
	
	public Boolean aceita() {
		return status.equals(StatusViagem.ACEITA);
	}

	public Boolean iniciada() {
		return status.equals(StatusViagem.INICIADA);
	}
}
