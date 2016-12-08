package modelo;

import java.util.Date;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Evento {
	@Id 
	@GeneratedValue
	private Long id;
	
	private Date dataHoraRegistro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataHoraRegistro() {
		return dataHoraRegistro;
	}

	public void setDataHoraRegistro(Date dataHoraRegistro) {
		this.dataHoraRegistro = dataHoraRegistro;
	}
}

