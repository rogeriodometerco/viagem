package json;

import java.text.SimpleDateFormat;

import com.google.gson.JsonObject;

import modelo.Evento;
import modelo.EventoPrevisaoChegada;
import modelo.PontoViagem;

public class EventoPrevisaoChegadaJsonAdapter extends EventoJsonAdapter {

	public EventoPrevisaoChegadaJsonAdapter(String json) {
		super(json);
	}

	@Override
	protected Evento terminarDeserializacao(Evento target, JsonObject jsonObject) throws Exception {
		EventoPrevisaoChegada evento = (EventoPrevisaoChegada) target;
		
		JsonObject pontoViagemObject = jsonObject.get("pontoViagem").getAsJsonObject();
		PontoViagem pontoViagem = new PontoViagem();
		pontoViagem.setId(pontoViagemObject.get("id").getAsLong());
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		evento.setDataHoraPrevista(format.parse(
			jsonObject.get("dataHoraPrevista").getAsString()));
		evento.setPontoViagem(pontoViagem);

		return evento;
	}
}
