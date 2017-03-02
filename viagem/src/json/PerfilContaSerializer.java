package json;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import modelo.Conta;
import modelo.PerfilConta;

public class PerfilContaSerializer implements JsonSerializer<PerfilConta> {

    @Override
    public JsonElement serialize(PerfilConta perfilConta, Type tipo, JsonSerializationContext context) {
	JsonObject jsonObject = new JsonObject();
	jsonObject.addProperty("id", perfilConta.getId());
	jsonObject.add("perfil", (context.serialize(perfilConta.getPerfil())));

//	jsonObject.add("uf",(context.serialize(new Gson().toJsonTree(municipio.getUf()))));

	return jsonObject;
	
    }

}
