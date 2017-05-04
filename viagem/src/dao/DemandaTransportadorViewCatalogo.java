package dao;

import java.util.HashMap;

import javax.ejb.Stateless;

import modelo.DemandaTransportadorView;

@Stateless
public class DemandaTransportadorViewCatalogo extends Catalogo<DemandaTransportadorView> {

	public static final String CAMPO_DEMANDA_ID = "demandaId";
	public static final String CAMPO_ORIGEM_ID = "origemId";
	public static final String CAMPO_DESTINO_ID = "destinoId";
	public static final String CAMPO_PRODUTO_ID = "produtoId";
	public static final String CAMPO_STATUS = "status";
	public static final String CAMPO_TOMADOR_ID = "tomadorId";
	public static final String CAMPO_TRANSPORTADOR_ID = "transportadorId";
	
	
	@Override
	protected void inicializarConversaoFiltroParaCampo() {
		this.conversaoFiltroParaCampo = new HashMap<String, String>();
		this.conversaoFiltroParaCampo.put("demandaId", CAMPO_DEMANDA_ID);
		this.conversaoFiltroParaCampo.put("origemId", CAMPO_ORIGEM_ID);
		this.conversaoFiltroParaCampo.put("destinoId", CAMPO_DESTINO_ID);
		this.conversaoFiltroParaCampo.put("produtoId", CAMPO_PRODUTO_ID);
		this.conversaoFiltroParaCampo.put("status", CAMPO_STATUS);
		this.conversaoFiltroParaCampo.put("tomadorId", CAMPO_TOMADOR_ID);
		this.conversaoFiltroParaCampo.put("transportadorId", CAMPO_TRANSPORTADOR_ID);
	}

}
