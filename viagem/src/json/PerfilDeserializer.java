package json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import enums.Perfil;

public class PerfilDeserializer implements JsonDeserializer<Perfil> {

    @Override
    public Perfil deserialize(JsonElement json, Type arg1, JsonDeserializationContext context) throws JsonParseException {
	Perfil p = Perfil.valueOf(json.getAsJsonObject().get("nome").getAsString());
	return p;
    }

}
