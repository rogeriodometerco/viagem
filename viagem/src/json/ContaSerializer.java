package json;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import modelo.Conta;

public class ContaSerializer implements JsonSerializer<Conta> {

    @Override
    public JsonElement serialize(Conta conta, Type tipo, JsonSerializationContext context) {
	JsonObject jsonObject = new JsonObject();
	jsonObject.addProperty("id", conta.getId());
	jsonObject.addProperty("nome", conta.getNome());
	jsonObject.add("perfis", (context.serialize(conta.getPerfis())));
	jsonObject.add("administradores", (context.serialize(conta.getAdministradores())));
	return jsonObject;
	
    }

}
