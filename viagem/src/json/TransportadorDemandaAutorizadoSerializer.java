package json;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import modelo.TransportadorDemandaAutorizado;

public class TransportadorDemandaAutorizadoSerializer implements JsonSerializer<TransportadorDemandaAutorizado> {

	@Override
	public JsonElement serialize(TransportadorDemandaAutorizado transportadorDemandaAutorizado, Type tipo, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("id", transportadorDemandaAutorizado.getId());
		jsonObject.add("transportador", (context.serialize(transportadorDemandaAutorizado.getTransportador())));

		return jsonObject;

	}

}
