package modelo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "LOCALIZACAO")
public class EventoLocalizacao extends Evento {

}

