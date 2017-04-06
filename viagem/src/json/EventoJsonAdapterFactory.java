package json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class EventoJsonAdapterFactory {

	public static EventoJsonAdapter getJsonAdapter(String json) {
		String tipo = new Gson()
				.fromJson(json, JsonObject.class)
				.get("tipo").getAsString();

		if (tipo.equals("ACEITE_VIAGEM")) {
			return new EventoAceiteViagemJsonAdapter(json);
			
		} else if (tipo.equals("RECUSA_VIAGEM")) {
			return new EventoRecusaViagemJsonAdapter(json);
			
		} else if (tipo.equals("INICIO_VIAGEM")) {
			return new EventoInicioViagemJsonAdapter(json);
			
		} else if (tipo.equals("PREVISAO_CHEGADA")) {
			return new EventoPrevisaoChegadaJsonAdapter(json);
			
		} else if (tipo.equals("CHEGADA")) {
			return new EventoChegadaJsonAdapter(json);
			
		} else if (tipo.equals("SAIDA")) {
			return new EventoSaidaJsonAdapter(json);
			
		} else if (tipo.equals("TERMINO_OPERACAO")) {
			return new EventoTerminoOperacaoJsonAdapter(json);
			
		} else if (tipo.equals("TERMINO_VIAGEM")) {
			return new EventoTerminoViagemJsonAdapter(json);
			
		} else if (tipo.equals("LOCALIZACAO")) {
			return new EventoLocalizacaoJsonAdapter(json);
		} else {
			return null;
		}
	}
}
