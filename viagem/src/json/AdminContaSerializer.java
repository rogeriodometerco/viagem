package json;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import modelo.Conta;
import modelo.AdminConta;

public class AdminContaSerializer implements JsonSerializer<AdminConta> {

    @Override
    public JsonElement serialize(AdminConta adminConta, Type tipo, JsonSerializationContext context) {
	JsonObject jsonObject = new JsonObject();
	jsonObject.addProperty("id", adminConta.getId());
	jsonObject.add("usuario", (context.serialize(adminConta.getUsuario())));

	return jsonObject;
	
    }

}
