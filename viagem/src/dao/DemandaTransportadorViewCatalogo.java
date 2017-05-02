package dao;

import java.util.HashMap;

import javax.ejb.Stateless;

import modelo.DemandaTransportadorView;

@Stateless
public class DemandaTransportadorViewCatalogo extends Catalogo<DemandaTransportadorView> {

	public static final String CAMPO_DEMANDA_ID = "id";
	public static final String CAMPO_ORIGEM_ID = "origemId";
	public static final String CAMPO_DESTINO_ID = "destinoId";
	public static final String CAMPO_PRODUTO_ID = "produtoId";
	public static final String CAMPO_STATUS = "status";
	public static final String CAMPO_TOMADOR_ID = "tomadorId";
	public static final String CAMPO_TRANSPORTADOR_ID = "transportadorId";
	
	
	@Override
	protected void inicializarConversaoFiltroParaCampo() {
		this.conversaoFiltroParaCampo = new HashMap<String, String>();
		this.conversaoFiltroParaCampo.put(CAMPO_DEMANDA_ID, "demandaId");
		this.conversaoFiltroParaCampo.put(CAMPO_ORIGEM_ID, "origemId");
		this.conversaoFiltroParaCampo.put(CAMPO_DESTINO_ID, "destinoId");
		this.conversaoFiltroParaCampo.put(CAMPO_PRODUTO_ID, "produtoId");
		this.conversaoFiltroParaCampo.put(CAMPO_STATUS, "status");
		this.conversaoFiltroParaCampo.put(CAMPO_TOMADOR_ID, "tomadorId");
		this.conversaoFiltroParaCampo.put(CAMPO_TRANSPORTADOR_ID, "transportadorId");
	}

}
