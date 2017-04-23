package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import dto.OperacaoViagemRequest;
import modelo.OperacaoViagem;
import util.DataUtil;

@Stateless
public class OperacaoViagemLista extends GenericDao<OperacaoViagem> {
	
	private Map<String, Object> filtros = new HashMap<String, Object>();
	private List<String> condicoes = new ArrayList<String>();
	private String sqlBase = "FROM OperacaoViagem x JOIN x.pontoViagem p JOIN p.viagem v";

	public OperacaoViagemLista() {
	}

	public List<OperacaoViagem> listar(OperacaoViagemRequest request) throws Exception {
		
		List<OperacaoViagem> result = null;

		inicializarFiltros(request);
		String sql = montarSql("SELECT x");
		
		TypedQuery<OperacaoViagem> query = criarQuery(sql);
		result = query.getResultList();
		
		System.out.println(sql);
		return result;
	}

	private TypedQuery<OperacaoViagem> criarQuery(String sql) {
		TypedQuery<OperacaoViagem> query = getEntityManager()
				.createQuery(sql, OperacaoViagem.class);
		for (String filtro: filtros.keySet()) {
			query.setParameter(filtro, filtros.get(filtro));
		}
		return query;
	}

	private String montarSql(String projecao) {
		StringBuffer sql = new StringBuffer(projecao);
		sql.append(" ").append(this.sqlBase);
		int i = 0;
		for (String condicao: condicoes) {
			if (i == 0) {
				sql.append(" WHERE");
			} else {
				sql.append(" AND");
			}
			sql.append(" ").append(condicao);
			i++;
		}
		return sql.toString();
	}
	

	private void inicializarFiltros(OperacaoViagemRequest request) {
		
		filtros = new HashMap<String, Object>();
		condicoes = new ArrayList<String>();

		if (request.getTransportador() != null) {
			condicoes.add("v.transportador = :transportador");
			filtros.put("transportador", request.getTransportador());
		}
		
		if (request.getTomador() != null) {
			condicoes.add("v.tomador = :tomador");
			filtros.put("tomador", request.getTomador());
		}
		
		if (request.getMotorista() != null) {
			condicoes.add("v.motorista = :motorista");
			filtros.put("motorista", request.getMotorista());
		}
		
		if (request.getVeiculo() != null) {
			condicoes.add("v.veiculo = :veiculo");
			filtros.put("veiculo", request.getVeiculo());
		}
		
		if (request.getEstabelecimento() != null) {
			condicoes.add("p.estabelecimento = :estabelecimento");
			filtros.put("estabelecimento", request.getEstabelecimento());
		}
		
		if (request.getTipoOperacao() != null) {
			condicoes.add("x.tipo = :tipoOperacaoViagem");
			filtros.put("tipoOperacaoViagem", request.getTipoOperacao());
		}
		
		if (request.getStatusOperacaoViagem() != null) {
			condicoes.add("x.status = :statusOperacaoViagem");
			filtros.put("statusOperacaoViagem", request.getStatusOperacaoViagem());
		}
		
		if (request.getDataStatusOperacaoViagemInicial() != null) {
			condicoes.add("x.dataHoraStatus >= :dataHoraStatusInicial");
			filtros.put("dataHoraStatusInicial", request.getDataStatusOperacaoViagemInicial());
		}
		
		if (request.getDataStatusOperacaoViagemFinal() != null) {
			condicoes.add("x.dataHoraStatus <= :dataHoraStatusFinal");
			filtros.put("dataHoraStatusFinal", DataUtil.extrairDataSemHora(
					DataUtil.somarDias(request.getDataStatusOperacaoViagemFinal(), 1)));
		}
		
		if (request.getStatusPontoViagem() != null) {
			condicoes.add("p.status = :statusPontoViagem");
			filtros.put("statusPontoViagem", request.getStatusPontoViagem());
		}
		
		if (request.getDataChegadaPrevistaDoVeiculo() != null) {
			condicoes.add("p.dataHoraChegadaPrevista >= :dataChegadaPrevistaDoVeiculoInicial");
			filtros.put("dataChegadaPrevistaDoVeiculoInicial", request.getDataChegadaPrevistaDoVeiculo());
			
			Date referencia = DataUtil.somarDias(request.getDataChegadaPrevistaDoVeiculo(), 1);
			condicoes.add("p.dataHoraChegadaPrevista < :dataChegadaPrevistaDoVeiculoFinal");
			filtros.put("dataChegadaPrevistaDoVeiculoFinal", referencia);
		}
	}	

}
