package mobile.modelo;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME, 
		include = JsonTypeInfo.As.PROPERTY, 
		property = "tipo")
public abstract class Evento {

	private Long dispositivoId;
	private Long motoristaId;
	private Date dataHora;
	private LatLng latLng;;
	
}
