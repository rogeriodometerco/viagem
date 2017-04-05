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
		} else if (tipo.equals("INICIO_VIAGEM")) {
			return new EventoInicioViagemJsonAdapter(json);
		} else {
			return null;
		}
	}
}
