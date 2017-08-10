package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import dto.DemandaTomadorDto;
import dto.DemandaTransportadorDto;
import dto.Filtro;
import enums.StatusEntrega;
import enums.TipoOperacaoViagem;
import modelo.DemandaTransporte;

@Stateless
public class DemandaTransporteDao extends GenericDao<DemandaTransporte> {

	public List<DemandaTransporte> listarOrdenadoPorIdDescendente(int pagina, int tamanhoPagina) 
			throws Exception {

		List<DemandaTransporte> result = null;
		String sql = "SELECT x FROM DemandaTransporte x " +
				" ORDER BY x.id DESC";
		result = getEntityManager()
				.createQuery(sql, DemandaTransporte.class)
				.setFirstResult(pagina * tamanhoPagina - tamanhoPagina)
				.setMaxResults(tamanhoPagina)
				.getResultList();
		return result;
	}

	public Long contar() throws Exception {
		String sql = "SELECT COUNT(x) FROM DemandaTransporte x";
		return getEntityManager()
				.createQuery(sql, Long.class)
				.getSingleResult();
	}



	public List<DemandaTomadorDto> listarTomador(Filtro filtro) throws Exception {
		List<DemandaTomadorDto> result = new ArrayList<DemandaTomadorDto>();

		String select = "SELECT d"
				+ ", (SELECT SUM(e.quantidade) FROM Entrega e WHERE e.demanda = d AND e.status = :statusEntregaPendente) AS quantidadePendente"
				+ ", (SELECT SUM(e.quantidade) FROM Entrega e WHERE e.demanda = d AND e.status = :statusEntregaCarregada) AS quantidadeCarregada"
				+ ", (SELECT SUM(e.quantidade) FROM Entrega e WHERE e.demanda = d AND e.status = :statusEntregaDescarregada) AS quantidadeDescarregada"
				+ ", (SELECT COUNT(e) FROM Entrega e WHERE e.demanda = d AND e.status = :statusEntregaPendente) AS quantidadeCargaPendente"
				+ ", (SELECT COUNT(e) FROM Entrega e WHERE e.demanda = d AND e.status = :statusEntregaCarregada) AS quantidadeCargaCarregada"
				+ " FROM DemandaTransporte d"
				+ " [WHERE]";

		Map<String, String> conversao = new HashMap<String, String>();
		conversao.put("tomadorId", "d.tomador.id");
		conversao.put("demandaId", "d.id");
		conversao.put("origemId", "d.origem.id");
		conversao.put("destinoId", "d.destino.id");
		conversao.put("produtoId", "d.produto.id");

		String sql = filtro.completarSql(select, conversao);

		TypedQuery<Object[]> query = getEntityManager()
				.createQuery(sql, Object[].class);

		filtro.setarParametrosWhere(query);

		query.setParameter("statusEntregaPendente", StatusEntrega.PENDENTE);
		query.setParameter("statusEntregaCarregada", StatusEntrega.CARREGADO);
		query.setParameter("statusEntregaDescarregada", StatusEntrega.REALIZADA);

		List<Object[]> lista = query.getResultList();

		for (Object[] item: lista) {
			DemandaTomadorDto dto = new DemandaTomadorDto();
			dto.setDemandaTransporte((DemandaTransporte)item[0]);
			dto.setQuantidadePendente((Long)item[1]);
			dto.setQuantidadeCarregada((Long)item[2]);
			dto.setQuantidadeDescarregada((Long)item[3]);
			dto.setQuantidadeCargaPendente((Long)item[4]);
			dto.setQuantidadeCargaCarregada((Long)item[5]);
			result.add(dto);
		}
		return result;

	}


	public List<DemandaTransportadorDto> listarTransportador(Filtro filtro) throws Exception {
		List<DemandaTransportadorDto> result = new ArrayList<DemandaTransportadorDto>();

		String select = "SELECT DISTINCT d"
				+ ", (SELECT SUM(e.quantidade) FROM Entrega e, "
				+ "	EtapaEntrega ee, OperacaoViagem o, PontoViagem p, Viagem v "
				+ "	WHERE e.demanda = d "
				+ "		AND e.status = :statusEntregaPendente "
				+ "		AND o.tipo = :tipoOperacaoViagemColeta "
				+ "		AND ee.entrega = e "
				+ "		AND o.etapaEntrega = ee "
				+ "		AND o.pontoViagem = p "
				+ "		AND p.viagem = v "
				+ "		AND v.transportador.id = :transportadorId ) AS quantidadePendente"

		+ ", (SELECT SUM(e.quantidade) FROM Entrega e, "
		+ "	EtapaEntrega ee, OperacaoViagem o, PontoViagem p, Viagem v "
		+ "	WHERE e.demanda = d "
		+ "		AND e.status = :statusEntregaCarregada "
		+ "		AND o.tipo = :tipoOperacaoViagemColeta "
		+ "		AND ee.entrega = e "
		+ "		AND o.etapaEntrega = ee "
		+ "		AND o.pontoViagem = p "
		+ "		AND p.viagem = v "
		+ "		AND v.transportador.id = :transportadorId ) AS quantidadeCarregada"

		+ ", (SELECT SUM(e.quantidade) FROM Entrega e, "
		+ "	EtapaEntrega ee, OperacaoViagem o, PontoViagem p, Viagem v "
		+ "	WHERE e.demanda = d "
		+ "		AND e.status = :statusEntregaDescarregada "
		+ "		AND o.tipo = :tipoOperacaoViagemEntrega "
		+ "		AND ee.entrega = e "
		+ "		AND o.etapaEntrega = ee "
		+ "		AND o.pontoViagem = p "
		+ "		AND p.viagem = v "
		+ "		AND v.transportador.id = :transportadorId ) AS quantidadeDescarregada"

		+ ", (SELECT COUNT(e) FROM Entrega e, "
		+ "	EtapaEntrega ee, OperacaoViagem o, PontoViagem p, Viagem v "
		+ "	WHERE e.demanda = d "
		+ "		AND e.status = :statusEntregaPendente "
		+ "		AND o.tipo = :tipoOperacaoViagemColeta "
		+ "		AND ee.entrega = e "
		+ "		AND o.etapaEntrega = ee "
		+ "		AND o.pontoViagem = p "
		+ "		AND p.viagem = v "
		+ "		AND v.transportador.id = :transportadorId ) AS quantidadeCargaPendente"

		+ ", (SELECT COUNT(e) FROM Entrega e, "
		+ "	EtapaEntrega ee, OperacaoViagem o, PontoViagem p, Viagem v "
		+ "	WHERE e.demanda = d "
		+ "		AND e.status = :statusEntregaCarregada "
		+ "		AND o.tipo = :tipoOperacaoViagemEntrega "
		+ "		AND ee.entrega = e "
		+ "		AND o.etapaEntrega = ee "
		+ "		AND o.pontoViagem = p "
		+ "		AND p.viagem = v "
		+ "		AND v.transportador.id = :transportadorId ) AS quantidadeCargaCarregada"

		+ ", (SELECT SUM(e.quantidade) FROM Entrega e WHERE e.demanda = d AND e.status = :statusEntregaCarregada) AS quantidadeTotalCarregada"
		+ ", (SELECT SUM(e.quantidade) FROM Entrega e WHERE e.demanda = d AND e.status = :statusEntregaDescarregada) AS quantidadeTotalDescarregada"

		+ " FROM DemandaTransporte d JOIN d.transportadores t"
		+ " [WHERE]";

		Map<String, String> conversao = new HashMap<String, String>();
		conversao.put("tomadorId", "d.tomador.id");
		conversao.put("transportadorId", "t.transportador.id");
		conversao.put("demandaId", "d.id");
		conversao.put("origemId", "d.origem.id");
		conversao.put("destinoId", "d.destino.id");
		conversao.put("produtoId", "d.produto.id");

/*		String where = filtro.construirClausula(conversao);

		String sql = select;
		if (where != null && where.length() > 0) {
			sql += " WHERE (" + where + ")";
		}

		TypedQuery<Object[]> query = getEntityManager()
				.createQuery(sql, Object[].class);

		filtro.configurarQuery(query);
*/
		
		String sql = filtro.completarSql(select, conversao);

		TypedQuery<Object[]> query = getEntityManager()
				.createQuery(sql, Object[].class);

		filtro.setarParametrosWhere(query);
		
		query.setParameter("statusEntregaPendente", StatusEntrega.PENDENTE);
		query.setParameter("statusEntregaCarregada", StatusEntrega.CARREGADO);
		query.setParameter("statusEntregaDescarregada", StatusEntrega.REALIZADA);
		query.setParameter("tipoOperacaoViagemColeta", TipoOperacaoViagem.COLETA);
		query.setParameter("tipoOperacaoViagemEntrega", TipoOperacaoViagem.ENTREGA);
		
		List<Object[]> lista = query.getResultList();

		for (Object[] item: lista) {
			DemandaTransportadorDto dto = new DemandaTransportadorDto();
			dto.setDemandaTransporte((DemandaTransporte)item[0]);
			dto.setQuantidadePendente((Long)item[1]);
			dto.setQuantidadeCarregada((Long)item[2]);
			dto.setQuantidadeDescarregada((Long)item[3]);
			dto.setQuantidadeCargaPendente((Long)item[4]);
			dto.setQuantidadeCargaCarregada((Long)item[5]);
			dto.setQuantidadeTotalCarregada((Long)item[6]);
			dto.setQuantidadeTotalDescarregada((Long)item[7]);
			result.add(dto);
		}
		return result;

	}

}
