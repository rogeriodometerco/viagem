package json;

import java.text.SimpleDateFormat;

import com.google.gson.JsonObject;

import modelo.Evento;
import modelo.EventoRecusaViagem;
import modelo.Viagem;

public class EventoRecusaViagemJsonAdapter extends EventoJsonAdapter {

	public EventoRecusaViagemJsonAdapter(String json) {
		super(json);
	}

	@Override
	protected Evento terminarDeserializacao(Evento target, JsonObject jsonObject) throws Exception {
		EventoRecusaViagem evento = (EventoRecusaViagem) target;
		
		JsonObject viagemObject = jsonObject.get("viagem").getAsJsonObject();
		Viagem viagem = new Viagem();
		viagem.setId(viagemObject.get("id").getAsLong());
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		evento.setDataHoraRecusa(format.parse(
			jsonObject.get("dataHoraRecusa").getAsString()));
		evento.setViagem(viagem);

		return evento;
	}
}
