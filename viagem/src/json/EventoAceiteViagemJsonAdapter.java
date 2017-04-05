package json;

import java.text.SimpleDateFormat;

import com.google.gson.JsonObject;

import modelo.Evento;
import modelo.EventoAceiteViagem;
import modelo.Viagem;

public class EventoAceiteViagemJsonAdapter extends EventoJsonAdapter {

	public EventoAceiteViagemJsonAdapter(String json) {
		super(json);
	}

	@Override
	protected Evento terminarDeserializacao(Evento target, JsonObject jsonObject) throws Exception {
		EventoAceiteViagem evento = (EventoAceiteViagem) target;
		
		JsonObject viagemObject = jsonObject.get("viagem").getAsJsonObject();
		Viagem viagem = new Viagem();
		viagem.setId(viagemObject.get("id").getAsLong());
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		evento.setDataHoraAceite(format.parse(
			jsonObject.get("dataHoraAceite").getAsString()));
		evento.setViagem(viagem);

		return evento;
	}
}
