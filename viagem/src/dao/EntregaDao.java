package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import dto.DemandaTomadorDto;
import dto.Filtro;
import enums.StatusEntrega;
import modelo.DemandaTransporte;
import modelo.Entrega;

@Stateless
public class EntregaDao extends GenericDao<Entrega> {

	public List<Entrega> listarPorDemanda(DemandaTransporte demanda) throws Exception {
		List<Entrega> result = null;
		
		String sql = "SELECT x FROM Entrega x "
				+ " WHERE x.demanda = :demanda"
				+ " ORDER BY x.id DESC";
		result = getEntityManager()
				.createQuery(sql, Entrega.class)
				.setParameter("demanda", demanda)
				.getResultList();

		return result;
	}

	public List<Entrega> listarEntregasPendentesPorTomador(Filtro filtro) throws Exception {
		List<Entrega> result = null;
		
		String select = "SELECT e FROM Entrega e, DemandaTransporte d ";
		String where = "e.status IN (:statusEntregaPendente, :statusEntregaCarregado)"
				+ " AND e.demanda.id = d.id"
				+ " AND d.tomador.id = :tomadorId";

		Map<String, String> conversao = new HashMap<String, String>();
		conversao.put("tomadorId", "d.tomador.id");
		conversao.put("demandaId", "d.id");
		conversao.put("origemId", "d.origem.id");
		conversao.put("destinoId", "d.destino.id");
		conversao.put("produtoId", "d.produto.id");

		String sql = filtro.completarSql2(select, where, conversao);

		TypedQuery<Entrega> query = getEntityManager()
				.createQuery(sql, Entrega.class);

		filtro.setarParametrosWhere(query);

		query.setParameter("statusEntregaPendente", StatusEntrega.PENDENTE);
		query.setParameter("statusEntregaCarregado", StatusEntrega.CARREGADO);

		result = query.getResultList();
		return result;
	}
	

}
