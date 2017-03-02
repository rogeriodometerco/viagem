package json;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import enums.Perfil;


public class PerfilSerializer implements JsonSerializer<Perfil> {

    @Override
    public JsonElement serialize(Perfil perfil, Type tipo, JsonSerializationContext context) {
	JsonObject jsonObject = new JsonObject();
	jsonObject.addProperty("nome", perfil.name());
	jsonObject.addProperty("descricao", perfil.getDescricao());
	return jsonObject;
	
    }

}
