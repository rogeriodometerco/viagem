package json;

import java.text.SimpleDateFormat;

import com.google.gson.JsonObject;

import enums.TipoTerminoOperacao;
import modelo.Evento;
import modelo.EventoTerminoOperacao;
import modelo.OperacaoViagem;

public class EventoTerminoOperacaoJsonAdapter extends EventoJsonAdapter {

	public EventoTerminoOperacaoJsonAdapter(String json) {
		super(json);
	}

	@Override
	protected Evento terminarDeserializacao(Evento target, JsonObject jsonObject) throws Exception {
		EventoTerminoOperacao evento = (EventoTerminoOperacao) target;
		
		JsonObject operacaoObject = jsonObject.get("operacao").getAsJsonObject();
		OperacaoViagem operacao = new OperacaoViagem();
		operacao.setId(operacaoObject.get("id").getAsLong());
		
		TipoTerminoOperacao tipoTermino = TipoTerminoOperacao.valueOf(
				jsonObject.get("tipoTermino").getAsString());
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		evento.setDataHoraTermino(format.parse(
			jsonObject.get("dataHoraTermino").getAsString()));
		evento.setOperacao(operacao);
		evento.setTipoTermino(tipoTermino);
		return evento;
	}
}
