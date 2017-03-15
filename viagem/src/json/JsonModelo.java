package json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import enums.Perfil;
import modelo.AdminConta;
import modelo.Conta;
import modelo.PerfilConta;
import modelo.TransportadorDemandaAutorizado;

public class JsonModelo {
	
	private Gson gson;

	public JsonModelo() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Conta.class, new ContaSerializer());
		builder.registerTypeAdapter(PerfilConta.class, new PerfilContaJsonAdapter());
		builder.registerTypeAdapter(AdminConta.class, new AdminContaSerializer());
		builder.registerTypeAdapter(Perfil.class, new PerfilJsonAdapter());
		builder.registerTypeAdapter(Perfil.class, new PerfilDeserializer());
		builder.registerTypeAdapter(TransportadorDemandaAutorizado.class, new TransportadorDemandaAutorizadoSerializer());
		gson = builder.create();
    }

	public String toJson(Object src) {
		return gson.toJson(src);
	}
}

