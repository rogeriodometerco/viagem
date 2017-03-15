package json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import enums.Perfil;
import modelo.PerfilConta;

public class PerfilContaJsonAdapter implements JsonSerializer<PerfilConta>, JsonDeserializer<PerfilConta> {

	@Override
	public JsonElement serialize(PerfilConta perfilConta, Type tipo, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("id", perfilConta.getId());
		jsonObject.add("perfil", (context.serialize(perfilConta.getPerfil())));

		return jsonObject;

	}


	@Override
	public PerfilConta deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2) 
			throws JsonParseException {

		JsonObject perfilJson = json.getAsJsonObject().get("perfil").getAsJsonObject();
		Perfil perfil = Perfil.valueOf(perfilJson.get("id").getAsString());

		PerfilConta perfilConta = new PerfilConta();
		perfilConta.setPerfil(perfil);
		perfilConta.setId(json.getAsJsonObject().get("id").getAsLong());

		return perfilConta;
	}

}
