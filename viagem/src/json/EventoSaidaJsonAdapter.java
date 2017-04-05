package json;

import java.text.SimpleDateFormat;

import com.google.gson.JsonObject;

import modelo.Evento;
import modelo.EventoSaida;
import modelo.PontoViagem;

public class EventoSaidaJsonAdapter extends EventoJsonAdapter {

	public EventoSaidaJsonAdapter(String json) {
		super(json);
	}

	@Override
	protected Evento terminarDeserializacao(Evento target, JsonObject jsonObject) throws Exception {
		EventoSaida evento = (EventoSaida) target;
		
		JsonObject pontoViagemObject = jsonObject.get("pontoViagem").getAsJsonObject();
		PontoViagem pontoViagem = new PontoViagem();
		pontoViagem.setId(pontoViagemObject.get("id").getAsLong());
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		evento.setDataHoraSaida(format.parse(
			jsonObject.get("dataHoraSaida").getAsString()));
		evento.setPontoViagem(pontoViagem);

		return evento;
	}
}
