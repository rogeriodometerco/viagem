package novo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Dispositivo {

	@Id
	@GeneratedValue
	private Long id;
	
	private String identificador;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdentificacao() {
		return identificador;
	}

	public void setIdentificacao(String identificador) {
		this.identificador = identificador;
	}
	
}
