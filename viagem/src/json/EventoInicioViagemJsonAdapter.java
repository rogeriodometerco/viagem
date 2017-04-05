package json;

import java.text.SimpleDateFormat;

import com.google.gson.JsonObject;

import modelo.Evento;
import modelo.EventoInicioViagem;
import modelo.Viagem;

public class EventoInicioViagemJsonAdapter extends EventoJsonAdapter {


	public EventoInicioViagemJsonAdapter(String json) {
		super(json);
	}

	@Override
	protected Evento terminarDeserializacao(Evento target, JsonObject jsonObject) throws Exception {
		EventoInicioViagem evento = (EventoInicioViagem) target;
		
		JsonObject viagemObject = jsonObject.get("viagem").getAsJsonObject();
		Viagem viagem = new Viagem();
		viagem.setId(viagemObject.get("id").getAsLong());
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		evento.setDataHoraInicio(format.parse(
			jsonObject.get("dataHoraInicio").getAsString()));
		evento.setViagem(viagem);

		return evento;
	}
}
