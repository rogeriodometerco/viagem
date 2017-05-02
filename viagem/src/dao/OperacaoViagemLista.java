package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import dto.OperacaoViagemRequest;
import enums.StatusOperacaoViagem;
import modelo.Estabelecimento;
import modelo.OperacaoViagem;
import util.DataUtil;

@Stateless
public class OperacaoViagemLista extends GenericDao<OperacaoViagem> {

	private Map<String, Object> filtros = new HashMap<String, Object>();
	private List<String> condicoes = new ArrayList<String>();
	private String sqlBase = "FROM OperacaoViagem OperacaoViagem JOIN OperacaoViagem.pontoViagem PontoViagem JOIN PontoViagem.viagem Viagem";

	public OperacaoViagemLista() {
	}

	public List<Object[]> agrupado(OperacaoViagemRequest request) throws Exception {

		List<Object[]> result = null;

		inicializarFiltros(request);
		String sql = montarSql(request, "SELECT");
		TypedQuery<Object[]> query = getEntityManager()
				.createQuery(sql, Object[].class);
		inicializarParametros(query);

		result = query.getResultList();

		System.out.println(sql);
		return result;

	}

	public void testar() throws Exception {
		String sql = "SELECT PontoViagem.estabelecimento.id, count(*) "
				+ "FROM OperacaoViagem OperacaoViagem "
				+ "JOIN OperacaoViagem.pontoViagem PontoViagem "
				+ "JOIN PontoViagem.viagem Viagem "
				+ "WHERE OperacaoViagem.status = :statusOperacaoViagem "
				+ "GROUP BY PontoViagem.estabelecimento.id";

		TypedQuery<Object[]> query = getEntityManager()
				.createQuery(sql, Object[].class);

		Query q = getEntityManager().createQuery(sql);
		q.setParameter("statusOperacaoViagem", StatusOperacaoViagem.PENDENTE);
		List lista = q.getResultList();

		Estabelecimento estabelecimento = new Estabelecimento();
		estabelecimento.setId(94l);
		query.setParameter("statusOperacaoViagem", StatusOperacaoViagem.PENDENTE);
		List<Object[]> result = query.getResultList();
		System.out.println(result.size());
	}

	public List<OperacaoViagem> listar(OperacaoViagemRequest request) throws Exception {

		List<OperacaoViagem> result = null;

		inicializarFiltros(request);
		String sql = montarSql(request, "SELECT");
		TypedQuery<OperacaoViagem> query = getEntityManager()
				.createQuery(sql, OperacaoViagem.class);
		inicializarParametros(query);

		result = query.getResultList();

		System.out.println(sql);
		return result;
	}

	public Long contar(OperacaoViagemRequest request) throws Exception {
		inicializarFiltros(request);
		String sql = montarSql(request, "SELECT COUNT(OperacaoViagem)");
		TypedQuery<Long> query = getEntityManager()
				.createQuery(sql, Long.class);
		inicializarParametros(query);

		return query.getSingleResult();
	}


	private void inicializarParametros(TypedQuery<?> query) {
		for (String filtro: filtros.keySet()) {
			query.setParameter(filtro, filtros.get(filtro));
		}
	}

	private String montarSql(OperacaoViagemRequest request, String projecao) {
		StringBuffer sql = new StringBuffer(projecao);

		if (request.getColunasSelecao().isEmpty()) {
			request.getColunasSelecao().add("OperacaoViagem");
		}
		int i = 0;
		for (String s: request.getColunasSelecao()) {
			if (i == 0) {
				sql.append(" ");
			} else {
				sql.append(", ");
			}
			sql.append(s);
			i++;
		}

		sql.append(" ").append(this.sqlBase);
		i = 0;
		for (String condicao: condicoes) {
			if (i == 0) {
				sql.append(" WHERE");
			} else {
				sql.append(" AND");
			}
			sql.append(" ").append(condicao);
			i++;
		}

		if (request.getAgrupar()) {
			sql.append(" GROUP BY");

			i = 0;
			for (String s: request.getColunasSelecao()) {
				if (i == 0) {
					sql.append(" ");
				} else {
					sql.append(", ");
				}
				sql.append(s);
				i++;
			}
		}
		return sql.toString();
	}


	private void inicializarFiltros(OperacaoViagemRequest request) {

		filtros = new HashMap<String, Object>();
		condicoes = new ArrayList<String>();

		if (request.getTransportador() != null) {
			condicoes.add("Viagem.transportador = :transportador");
			filtros.put("transportador", request.getTransportador());
		}

		if (request.getTomador() != null) {
			condicoes.add("Viagem.tomador = :tomador");
			filtros.put("tomador", request.getTomador());
		}

		if (request.getMotorista() != null) {
			condicoes.add("Viagem.motorista = :motorista");
			filtros.put("motorista", request.getMotorista());
		}

		if (request.getVeiculo() != null) {
			condicoes.add("Viagem.veiculo = :veiculo");
			filtros.put("veiculo", request.getVeiculo());
		}

		if (request.getEstabelecimento() != null) {
			condicoes.add("PontoViagem.estabelecimento = :estabelecimento");
			filtros.put("estabelecimento", request.getEstabelecimento());
		}

		if (request.getTipoOperacao() != null) {
			condicoes.add("OperacaoViagem.tipo = :tipoOperacaoViagem");
			filtros.put("tipoOperacaoViagem", request.getTipoOperacao());
		}

		if (request.getStatusOperacaoViagem() != null) {
			condicoes.add("OperacaoViagem.status = :statusOperacaoViagem");
			filtros.put("statusOperacaoViagem", request.getStatusOperacaoViagem());
		}

		if (request.getDataStatusOperacaoViagemInicial() != null) {
			condicoes.add("OperacaoViagem.dataHoraStatus >= :dataHoraStatusInicial");
			filtros.put("dataHoraStatusInicial", request.getDataStatusOperacaoViagemInicial());
		}

		if (request.getDataStatusOperacaoViagemFinal() != null) {
			condicoes.add("OperacaoViagem.dataHoraStatus <= :dataHoraStatusFinal");
			filtros.put("dataHoraStatusFinal", DataUtil.extrairDataSemHora(
					DataUtil.somarDias(request.getDataStatusOperacaoViagemFinal(), 1)));
		}

		if (request.getStatusPontoViagem() != null) {
			condicoes.add("PontoViagem.status = :statusPontoViagem");
			filtros.put("statusPontoViagem", request.getStatusPontoViagem());
		}

		if (request.getDataChegadaPrevistaDoVeiculo() != null) {
			condicoes.add("PontoViagem.dataHoraChegadaPrevista >= :dataChegadaPrevistaDoVeiculoInicial");
			filtros.put("dataChegadaPrevistaDoVeiculoInicial", request.getDataChegadaPrevistaDoVeiculo());

			Date referencia = DataUtil.somarDias(request.getDataChegadaPrevistaDoVeiculo(), 1);
			condicoes.add("PontoViagem.dataHoraChegadaPrevista < :dataChegadaPrevistaDoVeiculoFinal");
			filtros.put("dataChegadaPrevistaDoVeiculoFinal", referencia);
		}
	}	

}
