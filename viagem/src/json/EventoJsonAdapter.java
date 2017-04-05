package json;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import modelo.Conta;
import modelo.Dispositivo;
import modelo.Evento;
import modelo.EventoAceiteViagem;
import modelo.EventoChegada;
import modelo.EventoInicioViagem;
import modelo.EventoLocalizacao;
import modelo.EventoPrevisaoChegada;
import modelo.EventoRecusaViagem;
import modelo.EventoSaida;
import modelo.EventoTerminoOperacao;
import modelo.EventoTerminoViagem;
import modelo.Localizacao;

public abstract class EventoJsonAdapter {
	private String json;
	private Evento evento;
	
	private static Map<String, String> map;
	
	static {
		map = new HashMap<String, String>();
		map.put("ACEITE_VIAGEM", EventoAceiteViagem.class.getName());
		map.put("RECUSA_VIAGEM", EventoRecusaViagem.class.getName());
		map.put("INICIO_VIAGEM", EventoInicioViagem.class.getName());
		map.put("CHEGADA", EventoChegada.class.getName());
		map.put("SAIDA", EventoSaida.class.getName());
		map.put("PREVISAO_CHEGADA", EventoPrevisaoChegada.class.getName());
		map.put("TERMINO_OPERACAO", EventoTerminoOperacao.class.getName());
		map.put("TERMINO_VIAGEM", EventoTerminoViagem.class.getName());
		map.put("LOCALIZACAO", EventoLocalizacao.class.getName());
		
	}
	
	public EventoJsonAdapter(String json) {
		this.json = json;
		try {
			deserializar();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Evento getEvento() {
		return evento;
	}
	
	private Evento instanciarEvento(String tipoEvento) throws Exception {
		return (Evento)Class.forName(map.get(tipoEvento.toUpperCase())).newInstance();
	}
	
	private Evento deserializar() throws Exception {
		Gson g = new GsonBuilder().create();
		JsonObject objeto = g.fromJson(this.json, JsonObject.class);
		String tipoEvento = objeto.get("tipo").getAsString();

		this.evento = instanciarEvento(tipoEvento);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		if (objeto.get("localizacao") != null) {

			JsonObject localizacaoObject = objeto.get("localizacao").getAsJsonObject();
			
			Long id = null; //localizacaoObject.get("id").getAsLong();
			
			JsonObject dispositivoObject = localizacaoObject.get("dispositivo").getAsJsonObject();
			Dispositivo dispositivo = new Dispositivo();
			dispositivo.setId(dispositivoObject.get("id").getAsLong());
			
			JsonObject motoristaObject = localizacaoObject.get("motorista").getAsJsonObject();
			Conta motorista = new Conta();
			motorista.setId(motoristaObject.get("id").getAsLong());
			
			Date dataHora = format.parse(
					localizacaoObject.get("dataHora").getAsString());
			
			Double latitude = localizacaoObject.get("latitude").getAsDouble();
			
			Double longitude = localizacaoObject.get("longitude").getAsDouble();
			
			Double velocidade = localizacaoObject.get("velocidade").getAsDouble();
			
			Localizacao localizacao = new Localizacao();
			
			localizacao.setId(id);
			localizacao.setDispositivo(dispositivo);
			localizacao.setMotorista(motorista);
			localizacao.setDataHora(dataHora);
			localizacao.setLatitude(latitude);
			localizacao.setLongitude(longitude);
			localizacao.setVelocidade(velocidade);
			
			evento.setLocalizacao(localizacao);
		}
		Date dataHoraRegistro = format.parse(objeto.get("dataHoraRegistro").getAsString());
		evento.setDataHoraRegistro(dataHoraRegistro);
		
		terminarDeserializacao(evento, objeto);
		
		return evento;
	}
	
	protected abstract Evento terminarDeserializacao(Evento evento, JsonObject jsonObject) throws Exception;
}
