package teste;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import json.EventoJsonAdapter;
import json.EventoJsonAdapterFactory;
import modelo.Evento;
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
			
			
			String eventosJson = 
					"[{"
					+"\"tipo\":\"ACEITE_VIAGEM\","
					+"\"dataHoraRegistro\":\"21/03/2017 14:32:04\",			"
					+"		\"localizacao\":{			"
					+"			\"dispositivo\":{		"
					+"				\"id\":\"123\"	"
					+"			},		"
					+"			\"motorista\":{		"
					+"				\"id\":\"3211\"	"
					+"			},		"
					+"			\"dataHora\":\"22/03/2017 13:57:04\",		"
					+"			\"latitude\":\"-24.85856\",		"
					+"			\"longitude\":\"-52.304902\",		"
					+"			\"velocidade\":\"67\"		"
					+"		},			"
					+"		\"viagem\":{			"
					+"			\"id\":\"6351\"		"
					+"		},			"
					+"		\"dataHoraAceite\":\"22/03/2017 14:12:04\"			"
					+"	},				"
					+"	{				"
					+"		\"tipo\":\"RECUSA_VIAGEM\",			"
					+"		\"dataHoraRegistro\":\"21/03/2017 14:32:04\",			"
					+"		\"localizacao\":{			"
					+"			\"dispositivo\":{		"
					+"				\"id\":\"1515\"	"
					+"			},		"
					+"			\"motorista\":{		"
					+"				\"id\":\"9191\"	"
					+"			},		"
					+"			\"dataHora\":\"22/03/2017 13:57:04\",		"
					+"			\"latitude\":\"-25.85856\",		"
					+"			\"longitude\":\"-51.304902\",		"
					+"			\"velocidade\":\"51\"		"
					+"		},			"
					+"		\"viagem\":{			"
					+"			\"id\":\"5151\"		"
					+"		},			"
					+"		\"dataHoraRecusa\":\"22/03/2017 04:18:04\"			"
					+"	},				"
					+"					"
					+"	{				"
					+"		\"tipo\":\"INICIO_VIAGEM\",			"
					+"		\"dataHoraRegistro\":\"21/02/2016 14:32:04\",			"
					+"		\"localizacao\":{			"
					+"			\"dispositivo\":{		"
					+"				\"id\":\"1515\"	"
					+"			},		"
					+"			\"motorista\":{		"
					+"				\"id\":\"9191\"	"
					+"			},		"
					+"			\"dataHora\":\"22/03/2015 13:57:04\",		"
					+"			\"latitude\":\"-25.85856\",		"
					+"			\"longitude\":\"-51.304902\",		"
					+"			\"velocidade\":\"51\"		"
					+"		},			"
					+"		\"viagem\":{			"
					+"			\"id\":\"5151\"		"
					+"		},			"
					+"		\"dataHoraInicio\":\"22/03/2016 04:18:04\"			"
					+"	},				"
					+"	{				"
					+"		\"tipo\":\"PREVISAO_CHEGADA\",			"
					+"		\"dataHoraRegistro\":\"21/02/2013 10:32:04\",			"
					+"		\"localizacao\":{			"
					+"			\"dispositivo\":{		"
					+"				\"id\":\"115\"	"
					+"			},		"
					+"			\"motorista\":{		"
					+"				\"id\":\"91\"	"
					+"			},		"
					+"			\"dataHora\":\"22/03/2020 13:57:04\",		"
					+"			\"latitude\":\"-21.85856\",		"
					+"			\"longitude\":\"-18.304902\",		"
					+"			\"velocidade\":\"38\"		"
					+"		},			"
					+"		\"pontoViagem\":{			"
					+"			\"id\":\"423\"		"
					+"		},			"
					+"		\"dataHoraPrevista\":\"22/03/2026 04:18:04\"			"
					+"	},				"
					+"	{				"
					+"		\"tipo\":\"CHEGADA\",			"
					+"		\"dataHoraRegistro\":\"21/02/2013 10:32:04\",			"
					+"		\"localizacao\":{			"
					+"			\"dispositivo\":{		"
					+"				\"id\":\"115\"	"
					+"			},		"
					+"			\"motorista\":{		"
					+"				\"id\":\"91\"	"
					+"			},		"
					+"			\"dataHora\":\"22/03/2020 13:57:04\",		"
					+"			\"latitude\":\"-21.85856\",		"
					+"			\"longitude\":\"-18.304902\",		"
					+"			\"velocidade\":\"38\"		"
					+"		},			"
					+"		\"pontoViagem\":{			"
					+"			\"id\":\"423\"		"
					+"		},			"
					+"		\"dataHoraChegada\":\"22/03/2026 04:18:04\"			"
					+"	},				"
					+"	{				"
					+"		\"tipo\":\"SAIDA\",			"
					+"		\"dataHoraRegistro\":\"21/02/2013 10:32:04\",			"
					+"		\"localizacao\":{			"
					+"			\"dispositivo\":{		"
					+"				\"id\":\"115\"	"
					+"			},		"
					+"			\"motorista\":{		"
					+"				\"id\":\"91\"	"
					+"			},		"
					+"			\"dataHora\":\"22/03/2020 13:57:04\",		"
					+"			\"latitude\":\"-21.85856\",		"
					+"			\"longitude\":\"-18.304902\",		"
					+"			\"velocidade\":\"38\"		"
					+"		},			"
					+"		\"pontoViagem\":{			"
					+"			\"id\":\"423\"		"
					+"		},			"
					+"		\"dataHoraSaida\":\"22/03/2026 14:33:04\"			"
					+"	},				"
					+"	{				"
					+"		\"tipo\":\"TERMINO_OPERACAO\",			"
					+"		\"dataHoraRegistro\":\"21/02/2013 10:32:04\",			"
					+"		\"localizacao\":{			"
					+"			\"dispositivo\":{		"
					+"				\"id\":\"115\"	"
					+"			},		"
					+"			\"motorista\":{		"
					+"				\"id\":\"91\"	"
					+"			},		"
					+"			\"dataHora\":\"22/03/2020 13:57:04\",		"
					+"			\"latitude\":\"-21.85856\",		"
					+"			\"longitude\":\"-18.304902\",		"
					+"			\"velocidade\":\"38\"		"
					+"		},			"
					+"		\"operacao\":{			"
					+"			\"id\":\"43\"		"
					+"		},			"
					+"		\"dataHoraTermino\":\"22/03/2026 04:18:04\",			"
					+"		\"tipoTermino\":\"REALIZADA\"			"
					+"	},				"
					+"	{				"
					+"		\"tipo\":\"TERMINO_VIAGEM\",			"
					+"		\"dataHoraRegistro\":\"21/02/2013 10:32:04\",			"
					+"		\"localizacao\":{			"
					+"			\"dispositivo\":{		"
					+"				\"id\":\"115\"	"
					+"			},		"
					+"			\"motorista\":{		"
					+"				\"id\":\"91\"	"
					+"			},		"
					+"			\"dataHora\":\"22/03/2020 13:57:04\",		"
					+"			\"latitude\":\"-21.85856\",		"
					+"			\"longitude\":\"-18.304902\",		"
					+"			\"velocidade\":\"38\"		"
					+"		},			"
					+"		\"viagem\":{			"
					+"			\"id\":\"43\"		"
					+"		},			"
					+"		\"dataHoraTermino\":\"22/03/2026 04:18:04\"			"
					+"	},				"
					+"	{				"
					+"		\"tipo\":\"LOCALIZACAO\",			"
					+"		\"dataHoraRegistro\":\"21/02/2013 10:32:04\",			"
					+"		\"localizacao\":{			"
					+"			\"dispositivo\":{		"
					+"				\"id\":\"115\"	"
					+"			},		"
					+"			\"motorista\":{		"
					+"				\"id\":\"91\"	"
					+"			},		"
					+"			\"dataHora\":\"22/03/2020 13:57:04\",		"
					+"			\"latitude\":\"-21.85856\",		"
					+"			\"longitude\":\"-18.304902\",		"
					+"			\"velocidade\":\"38\"		"
					+"		}			"
					+"	}				"
					+"]".replace("	", "");					
			JsonArray eventosArray = new Gson()
					.fromJson(eventosJson, JsonArray.class);

			for (JsonElement e: eventosArray) {
				JsonObject o = e.getAsJsonObject();
				
				json = new Gson().toJson(o);
				adapter = EventoJsonAdapterFactory.getJsonAdapter(json);
				
				Evento ev = adapter.getEvento();
				
				System.out.println(ev.getDataHoraRegistro());
				System.out.println(ev.getLocalizacao().getDataHora());
				System.out.println(ev.getLocalizacao().getLatitude());
				System.out.println(ev.toString());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
