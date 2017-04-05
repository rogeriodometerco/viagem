package teste;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import json.EventoInicioViagemJsonAdapter;
import json.EventoJsonAdapter;
import json.EventoJsonAdapterFactory;
import modelo.EventoInicioViagem;

public class DeserializacaoEvento {

	public DeserializacaoEvento() {
	}

	public static void main(String[] args) {
		DeserializacaoEvento d = new DeserializacaoEvento();
		d.testar();
	}
	
	public void testar() {
		try {
			
			String jsonRequest = 
					"{"
					+"\"tipo\": \"INICIO_VIAGEM\","
					+"\"dataHoraRegistro\": \"23/03/2017 17:40:12\","
					+"				\"localizacao\": {"
					+"\"dispositivo\": {"
					+"						\"id\": \"2929\""
					+"},"
					+"\"motorista\": {"
					+"						\"id\": \"3302\""
					+"},"
					+"\"dataHora\": \"22/03/2017 15:40:30\","
					+"\"latitude\": \"-24.59401\","
					+"\"longitude\": \"-53.5040\","
					+"\"velocidade\": \"94\""
					+"},"
					+"\"viagem\": {"
					+"\"id\": \"123\""
					+"},"
					+"\"dataHoraInicio\": \"21/03/2017 15:34:30\""
					+"}"
					;
					
			JsonObject evento = new JsonObject();
			evento.addProperty("tipo", "INICIO_VIAGEM");
			evento.addProperty("dataHoraRegistro", "22/03/2017 08:36:02");
			
			JsonObject viagem = new JsonObject();
			viagem.addProperty("id", "123");
			
			evento.add("viagem", viagem);
			evento.addProperty("dataHoraInicio", "22/03/2017 08:35:01");
			
			String json = new Gson().toJson(evento);
			EventoJsonAdapter adapter = EventoJsonAdapterFactory.getJsonAdapter(jsonRequest);
			
			EventoInicioViagem eventoDeserializado = (EventoInicioViagem)adapter.getEvento();
			System.out.println(eventoDeserializado.getDataHoraRegistro());
			System.out.println(eventoDeserializado.getDataHoraInicio());
			System.out.println(eventoDeserializado.getViagem().getId());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
