package json;

import com.google.gson.JsonObject;

import modelo.Evento;
import modelo.EventoLocalizacao;

public class EventoLocalizacaoJsonAdapter extends EventoJsonAdapter {

	public EventoLocalizacaoJsonAdapter(String json) {
		super(json);
	}

	@Override
	protected Evento terminarDeserializacao(Evento target, JsonObject jsonObject) throws Exception {
		EventoLocalizacao evento = (EventoLocalizacao) target;
		
		return evento;
	}
}
