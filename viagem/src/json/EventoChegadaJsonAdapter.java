package json;

import java.text.SimpleDateFormat;

import com.google.gson.JsonObject;

import modelo.Evento;
import modelo.EventoChegada;
import modelo.PontoViagem;

public class EventoChegadaJsonAdapter extends EventoJsonAdapter {

	public EventoChegadaJsonAdapter(String json) {
		super(json);
	}

	@Override
	protected Evento terminarDeserializacao(Evento target, JsonObject jsonObject) throws Exception {
		EventoChegada evento = (EventoChegada) target;
		
		JsonObject pontoViagemObject = jsonObject.get("pontoViagem").getAsJsonObject();
		PontoViagem pontoViagem = new PontoViagem();
		pontoViagem.setId(pontoViagemObject.get("id").getAsLong());
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		evento.setDataHoraChegada(format.parse(
			jsonObject.get("dataHoraChegada").getAsString()));
		evento.setPontoViagem(pontoViagem);

		return evento;
	}
}
