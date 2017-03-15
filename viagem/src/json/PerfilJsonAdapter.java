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


public class PerfilJsonAdapter implements JsonSerializer<Perfil>, JsonDeserializer<Perfil> {

	@Override
	public JsonElement serialize(Perfil perfil, Type tipo, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("id", perfil.name());
		jsonObject.addProperty("descricao", perfil.getDescricao());
		return jsonObject;

	}

	@Override
	public Perfil deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2) 
			throws JsonParseException {
		Perfil p = Perfil.valueOf(json.getAsJsonObject().get("id").getAsString());
		return p;
	}

}
