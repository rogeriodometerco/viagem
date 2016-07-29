package novo;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import modelo.PontoViagem;

@Entity
@DiscriminatorValue(value = "PREVISAO")
public class PrevisaoEvento extends Evento {

	@ManyToOne
	private PontoViagem pontoViagem;
	
	private Date dataHoraPrevista;

	public PontoViagem getPontoViagem() {
		return pontoViagem;
	}

	public void setPontoViagem(PontoViagem pontoViagem) {
		this.pontoViagem = pontoViagem;
	}

	public Date getDataHoraPrevista() {
		return dataHoraPrevista;
	}

	public void setDataHoraPrevista(Date dataHoraPrevista) {
		this.dataHoraPrevista = dataHoraPrevista;
	}

	
}
