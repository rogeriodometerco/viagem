package json;

import java.text.SimpleDateFormat;

import com.google.gson.JsonObject;

import modelo.Evento;
import modelo.EventoTerminoViagem;
import modelo.Viagem;

public class EventoTerminoViagemJsonAdapter extends EventoJsonAdapter {


	public EventoTerminoViagemJsonAdapter(String json) {
		super(json);
	}

	@Override
	protected Evento terminarDeserializacao(Evento target, JsonObject jsonObject) throws Exception {
		EventoTerminoViagem evento = (EventoTerminoViagem) target;
		
		JsonObject viagemObject = jsonObject.get("viagem").getAsJsonObject();
		Viagem viagem = new Viagem();
		viagem.setId(viagemObject.get("id").getAsLong());
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		evento.setDataHoraTermino(format.parse(
			jsonObject.get("dataHoraTermino").getAsString()));
		evento.setViagem(viagem);

		return evento;
	}
}
