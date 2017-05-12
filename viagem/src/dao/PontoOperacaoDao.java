package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import dto.Filtro;
import dto.Filtro.Restricao;
import dto.OperacaoViagemItemList;
import enums.StatusOperacaoViagem;
import enums.StatusViagem;
import enums.TipoOperacaoViagem;
import modelo.PontoOperacao;

@Stateless
public class PontoOperacaoDao extends GenericDao<PontoOperacao> {

	public List<PontoOperacao> listar() throws Exception {
		return getEntityManager().createQuery("select x from PontoOperacao x").getResultList();
	}

	public List<Object[]> testar(String sql) throws Exception {
		return getEntityManager().createQuery(sql).getResultList();
	}

	public List<Object[]> listarFiltroMap(Map<String, Object> filtros) throws Exception {
		List<Object[]> result = null;
		Map<String, String> conversao = new HashMap<String, String>();
		conversao.put("estabelecimentoId", "p.estabelecimento.id");
		conversao.put("demandaId", "e.demanda.id");
		conversao.put("origemId", "e.origem.id");
		conversao.put("destinoId", "e.destino.id");
		conversao.put("produtoId", "e.produto.id");
		conversao.put("operacaoTipo", "o.tipo");
		conversao.put("operacaoStatus", "o.status");
		conversao.put("transportadorId", "v.transportador.id");
		conversao.put("tomadorId", "v.tomador.id");
		conversao.put("motoristaId", "v.motorista.id");
		conversao.put("operacaoDataHoraStatusInicial", "o.dataHoraStatus");
		conversao.put("operacaoDataHoraStatusFinal", "o.dataHoraStatus");


		String sql = 
				"SELECT"
						+ " o.id "
						+ " ,o.tipo"
						+ " ,o.status"
						+ " ,o.dataHoraStatus"
						+ " ,p.estabelecimento.id"
						+ " ,e1.nome" //5
						+ " ,e1.endereco.municipio.id"
						+ " ,uf1.id"
						+ " ,uf1.abreviatura"
						+ " ,p.status" 
						+ " ,p.dataHoraStatus" //10 
						+ " ,p.dataChegadaAcordada" 
						+ " ,p.dataHoraPrevistaChegada" 
						+ " ,p.dataHoraChegada" 
						+ " ,p.dataHoraSaida" 
						+ " ,v.id" //15
						+ " ,v.status" 
						+ " ,v.dataHoraStatus" 
						+ " ,v.motorista.id" 
						+ " ,v.transportador.id" 
						+ " ,v.veiculo.id" //20 
						+ " ,e.quantidade" 
						+ " ,e.status" 
						+ " ,e.dataHoraStatus" 
						+ " ,e.demanda.id"
						+ " ,e.produto.id" 
						+ " ,e.unidadeQuantidade.id" 
						+ " FROM"
						+ " OperacaoViagem o,"
						+ " Estabelecimento e1,"
						+ " Municipio m1,"
						+ " UF uf1,"
						+ " PontoViagem p,"
						+ " Viagem v,"
						+ " Entrega e,"
						+ " EtapaEntrega ee"
						+ " WHERE"
						+ " o.pontoViagem.id = p.id"
						+ " and p.viagem.id = v.id"
						+ " and o.etapaEntrega.id = ee.id"
						+ " and ee.entrega.id = e.id"
						+ " and p.estabelecimento.id = e1.id"
						+ " and e1.endereco.municipio.id = m1.id"
						+ " and m1.uf.id = uf1.id";
		/*
		for (String campo: filtros.keySet()) {
			if (campo.equals("demandaId")) {
				condicoes.add("e.demanda.id = :" + campo);
			} else if (campo.equals("origemId")) {
				condicoes.add("e.origem.id = :" + campo);
			} else if (campo.equals("destinoId")) {
				condicoes.add("e.destino.id = :" + campo);
			} else if (campo.equals("produtoId")) {
				condicoes.add("e.produto.id = :" + campo);
			} else if (campo.equals("operacaoTipo")) {
				condicoes.add("o.tipo = :" + campo);
			} else if (campo.equals("operacaoStatus")) {
				condicoes.add("o.status = :" + campo);
			} else if (campo.equals("transportadorId")) {
				condicoes.add("v.tranportador.id = :" + campo);
			} else if (campo.equals("tomadorId")) {
				condicoes.add("v.tomador.id = :" + campo);
			} else if (campo.equals("motoristaId")) {
				condicoes.add("v.motorista.id = :" + campo);
			} else if (campo.equals("operacaoDataStatus")) {
				condicoes.add("o.dataHoraStatus = :" + campo);
			}
		}
		for (String condicao: condicoes) {
			sql += " AND";
			sql +=  " " + condicao;
		}
		 */

		Filtro filtroRestricao = new Filtro();
		filtroRestricao.igual("demandaId", 110l);
		sql = montarSql(sql, conversao, filtroRestricao);

		TypedQuery<Object[]> query = getEntityManager()
				.createQuery(sql, Object[].class);

		/*
		for (String campo: filtros.keySet()) {
			query.setParameter(campo, filtros.get(campo));
		}
		 */
		inicializarParametros(query, filtroRestricao);
		result = query.getResultList();
		return result;
	}

	private String montarSql(String sqlParcial, Map<String, String> conversaoFiltroParaCampo, Filtro filtro) {
		StringBuffer sql = new StringBuffer(sqlParcial);

		String and = " AND";
		for (Restricao r: filtro.getRestricoes()) {
			sql.append(and);
			String condicao = 
					conversaoFiltroParaCampo.get(r.getChave())
					+ " ".concat(r.getOperador())
					+ " :".concat(r.getChave());
			sql.append(" ").append(condicao);
			and = " AND";
		}
		return sql.toString();
	}

	protected void inicializarParametros(TypedQuery<?> query, Filtro filtro) {
		for (Restricao r: filtro.getRestricoes()) {
			String chave = r.getChave();
			query.setParameter(chave, r.getValor());
		}
	}


	public List<OperacaoViagemItemList> listar(Filtro filtro) throws Exception {
		List<OperacaoViagemItemList> result = new ArrayList<OperacaoViagemItemList>();
		Map<String, String> conversao = new HashMap<String, String>();
		conversao.put("estabelecimentoId", "p.estabelecimento.id");
		conversao.put("demandaId", "e.demanda.id");
		conversao.put("origemId", "e.origem.id");
		conversao.put("destinoId", "e.destino.id");
		conversao.put("produtoId", "e.produto.id");
		conversao.put("operacaoTipo", "o.tipo");
		conversao.put("operacaoStatus", "o.status");
		conversao.put("transportadorId", "v.transportador.id");
		conversao.put("tomadorId", "v.tomador.id");
		conversao.put("motoristaId", "v.motorista.id");
		conversao.put("operacaoDataHoraStatusInicial", "o.dataHoraStatus");
		conversao.put("operacaoDataHoraStatusFinal", "o.dataHoraStatus");

		conversao.put("estabelecimentoId", "o.pontoViagem.estabelecimento.id");
		conversao.put("demandaId", "o.etapaEntrega.entrega.demanda.id");
		conversao.put("origemId", "o.etapaEntrega.entrega.origem.id");
		conversao.put("destinoId", "o.etapaEntrega.entrega.destino.id");
		conversao.put("produtoId", "o.etapaEntrega.entrega.produto.id");
		conversao.put("operacaoTipo", "o.tipo");
		conversao.put("operacaoStatus", "o.status");
		conversao.put("transportadorId", "o.pontoViagem.viagem.transportador.id");
		conversao.put("tomadorId", "o.pontoViagem.viagem.tomador.id");
		conversao.put("motoristaId", "o.pontoViagem.viagem.motorista.id");
		conversao.put("operacaoDataHoraStatusInicial", "o.dataHoraStatus");
		conversao.put("operacaoDataHoraStatusFinal", "o.dataHoraStatus");

		String sql = 
				"SELECT"
						+ " o.id "
						+ " ,o.tipo"
						+ " ,o.status"
						+ " ,o.dataHoraStatus"
						+ " ,e1.nome"
						+ " ,m1.nome" //5
						+ " ,uf1.abreviatura"
						+ " ,p.status" 
						+ " ,p.dataHoraStatus" 
						+ " ,p.dataChegadaAcordada" 
						+ " ,p.dataHoraPrevistaChegada" //10 
						+ " ,p.dataHoraChegada" 
						+ " ,p.dataHoraSaida" 
						+ " ,v.id" 
						+ " ,v.status" 
						+ " ,v.dataHoraStatus" //15 
						+ " ,c1.nome" 
						+ " ,c2.nome" 
						+ " ,e.quantidade" 
						+ " ,e.status" 
						+ " ,e.dataHoraStatus" //20 
						+ " ,e.demanda.id" 
						+ " ,p1.nome"
						+ " ,cv1.placa"
						+ " ,e2.nome"
						+ " ,m2.nome" //25
						+ " ,uf2.abreviatura"
						+ " ,e3.nome" 
						+ " ,m3.nome"
						+ " ,uf3.abreviatura"
						+ " ,e.unidadeQuantidade.abreviacao" //30
						+ " FROM"
						+ " OperacaoViagem o,"
						+ " Estabelecimento e1,"
						+ " Municipio m1,"
						+ " UF uf1,"
						+ " Municipio m2,"
						+ " UF uf2,"
						+ " Municipio m3,"
						+ " UF uf3,"
						+ " PontoViagem p,"
						+ " Viagem v,"
						+ " ComponenteVeiculo cv1,"
						+ " Entrega e,"
						+ " EtapaEntrega ee,"
						+ " Conta c1,"
						+ " Conta c2,"
						+ " Produto p1,"
						+ " Estabelecimento e2,"
						+ " Estabelecimento e3"
						+ " WHERE"
						+ " o.pontoViagem.id = p.id"
						+ " and p.viagem.id = v.id"
						+ " and o.etapaEntrega.id = ee.id"
						+ " and ee.entrega.id = e.id"
						+ " and e.produto.id = p1.id"
						+ " and p.estabelecimento.id = e1.id"
						+ " and e1.endereco.municipio.id = m1.id"
						+ " and m1.uf.id = uf1.id"
						+ " and e2.endereco.municipio.id = m2.id"
						+ " and m2.uf.id = uf2.id"
						+ " and e3.endereco.municipio.id = m3.id"
						+ " and m3.uf.id = uf3.id"
						+ " and v.motorista.id = c1.id"
						+ " and v.transportador.id = c2.id"
						+ " and v.veiculo.id = cv1.veiculo.id"
						+ " and cv1.posicaoNoVeiculo = 1"
						+ " and e.origem.id = e2.id"
						+ " and e.destino.id = e3.id";

		sql = 

				"SELECT"
						+ " o.id "
						+ " ,o.tipo"
						+ " ,o.status"
						+ " ,o.dataHoraStatus"
						+ " ,o.pontoViagem.estabelecimento.nome"
						+ " ,o.pontoViagem.estabelecimento.endereco.municipio.nome" //5
						+ " ,o.pontoViagem.estabelecimento.endereco.municipio.uf.abreviatura"
						+ " ,o.pontoViagem.status" 
						+ " ,o.pontoViagem.dataHoraStatus" 
						+ " ,o.pontoViagem.dataChegadaAcordada" 
						+ " ,o.pontoViagem.dataHoraPrevistaChegada" //10 
						+ " ,o.pontoViagem.dataHoraChegada" 
						+ " ,o.pontoViagem.dataHoraSaida" 
						+ " ,o.pontoViagem.viagem.id" 
						+ " ,o.pontoViagem.viagem.status" 
						+ " ,o.pontoViagem.viagem.dataHoraStatus" //15 
						+ " ,o.pontoViagem.viagem.motorista.nome" 
						+ " ,o.pontoViagem.viagem.transportador.nome" 
						+ " ,o.etapaEntrega.entrega.quantidade" 
						+ " ,o.etapaEntrega.entrega.status" 
						+ " ,o.etapaEntrega.entrega.dataHoraStatus" //20 
						+ " ,o.etapaEntrega.entrega.demanda.id" 
						+ " ,o.etapaEntrega.entrega.produto.nome"
						+ " ,cv.placa"
						+ " ,o.etapaEntrega.entrega.origem.nome"
						+ " ,o.etapaEntrega.entrega.origem.endereco.municipio.nome" //25
						+ " ,o.etapaEntrega.entrega.origem.endereco.municipio.uf.abreviatura"
						+ " ,o.etapaEntrega.entrega.destino.nome"
						+ " ,o.etapaEntrega.entrega.destino.endereco.municipio.nome" //25
						+ " ,o.etapaEntrega.entrega.destino.endereco.municipio.uf.abreviatura"
						+ " ,o.etapaEntrega.entrega.unidadeQuantidade.abreviacao" //30
						+ " FROM"
						+ " OperacaoViagem o"
						+ " ,ComponenteVeiculo cv"
						+ " WHERE"
						+ " o.pontoViagem.viagem.veiculo.id = cv.veiculo.id"
						+ " AND cv.posicaoNoVeiculo = 1";

		

		sql = montarSql(sql, conversao, filtro);

		TypedQuery<Object[]> query = getEntityManager()
				.createQuery(sql, Object[].class);

		inicializarParametros(query, filtro);
		List<Object[]> lista = query.getResultList();

		for (Object[] o: lista) {
			OperacaoViagemItemList operacao = new OperacaoViagemItemList();
			operacao.setTipoOperacao((TipoOperacaoViagem)o[1]);
			operacao.setStatusOperacao((StatusOperacaoViagem)o[2]);
			operacao.setDataHoraStatusOperacao((Date)o[3]);
			operacao.setEstabelecimentoNome((String)o[4]);
			operacao.setEstabelecimentoMunicipio((String)o[5]);
			operacao.setEstabelecimentoUf((String)o[6]);
			operacao.setDataChegadaAcordada((Date)o[8]);
			operacao.setDataHoraChegadaPrevista((Date)o[9]);
			operacao.setDataHoraChegada((Date)o[10]);
			operacao.setDataHoraSaida((Date)o[11]);
			operacao.setViagemId((Long)o[13]);
			operacao.setStatusViagem((StatusViagem)o[14]);
			operacao.setMotoristaNome((String)o[16]);
			operacao.setTransportadorNome((String)o[17]);
			operacao.setMotoristaNome((String)o[16]);
			operacao.setMotoristaNome((String)o[16]);
			operacao.setQuantidadeOperacao((Integer)o[18]);
			operacao.setDemandaId((Long)o[21]);
			operacao.setProdutoNome((String)o[22]);
			operacao.setPlaca((String)o[23]);
//			operacao.setPlaca(String.valueOf((Long)o[23]));
			operacao.setOrigemNome((String)o[24]);
			operacao.setOrigemMunicipio((String)o[25]);
			operacao.setOrigemUf((String)o[26]);
			operacao.setDestinoNome((String)o[27]);
			operacao.setDestinoMunicipio((String)o[28]);
			operacao.setDestinoUf((String)o[29]);
			operacao.setUnidadeQuantidade((String)o[30]);
			result.add(operacao);
		}
		return result;
	}


}
