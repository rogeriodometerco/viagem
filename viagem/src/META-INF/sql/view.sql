create or replace view pontooperacao as
select p.* from pontoviagem p, operacaoviagem o
where p.id = o.pontoviagem_id;





-- cargas realizadas
select * from operacaoviagem where tipo = 0 (COLETA) and status = 1 (REALIZADA) and datahorastatus >= HOJE;

-- cargas em trânsito
select * from entrega where status = 2 (A_CAMINHO) 

-- descargas realizadas
select * from operacaoviagem where tipo = 1 (COLETA) and status = 1 (REALIZADA) and datahorastatus >= HOJE;

-- cargas pendentes
select * from operacaoviagem where status = 0 (PENDENTE);




select 
	d.id,
	d.origem_id,
	e1.nome as origem_nome,
	m1.id as origem_municipio_id,
	m1.nome as origem_municipio_nome,
	u1.abreviatura as origem_uf_abreviatura,
	e2.nome as destino_nome,
	m2.id as destino_municipio_id,
	m2.nome as destino_municipio_nome,
	u2.abreviatura as destino_uf_abreviatura,
	d.produto_id,
	p.nome as produto_nome,
	d.quantidade,
	d.unidadequantidade_id,
	u.abreviacao as unidadequantidade_abreviacao,
	d.status,
	d.tomador_id,
	t.nome as tomador_nome,
	(
		select count(*) from operacaoviagem o, etapaentrega ee, entrega e
		where o.etapaentrega_id = ee.id and ee.entrega_id = e.id and e.demanda_id = d.id
		and o.tipo = 1 and o.status = 1
	) cargaspendentes,
	(
		select count(*) from operacaoviagem o, etapaentrega ee, entrega e
		where o.etapaentrega_id = ee.id and ee.entrega_id = e.id and e.demanda_id = d.id
		and o.tipo = 1 and o.status = 2
	) cargasrealizadas,
	(
		select count(*) from entrega e
		where e.demanda_id = d.id and e.status in (2,3)
	) cargastransito
from 
	demandatransporte d,
	estabelecimento e1,
	estabelecimento e2,
	municipio m1,
	municipio m2, 
	produto p,
	unidadequantidade u,
	uf u1,
	uf u2,
	conta t
where
	d.origem_id = e1.id
	and d.destino_id = e2.id
	and e1.municipio_id = m1.id
	and m1.uf_id = u1.id
	and e2.municipio_id = m2.id
	and m2.uf_id = u2.id
	and d.produto_id = p.id
	and d.tomador_id = t.id

-----------------------
	
	
	
	
	
	
	SELECT td.id AS transportador_demanda_id,
    td.transportador.id,
    td.ativo AS transportador_ativo,
    c2.nome AS transportador_nome,
    d.id AS demanda_id,
    d.origem.id,
    e1.nome AS origem_nome,
    m1.id AS origem_municipio_id,
    m1.nome AS origem_municipio_nome,
    u1.abreviatura AS origem_uf_abreviatura,
    d.destino.id,
    e2.nome AS destino_nome,
    m2.id AS destino_municipio_id,
    m2.nome AS destino_municipio_nome,
    u2.abreviatura AS destino_uf_abreviatura,
    d.produto.id,
    p.nome AS produto_nome,
    d.quantidade,
    d.unidadeQuantidade.id,
    uq.abreviacao AS unidadequantidade_abreviacao,
    d.status,
    d.tomador.id,
    c1.nome AS tomador_nome,
    ( SELECT count(*) AS count
           FROM OperacaoViagem ov,
            PontoViagem pv,
            Viagem v,
            EtapaEntrega ee,
            Entrega e
          WHERE ov.pontoViagem.id = pv.id AND pv.viagem.id = v.id AND v.transportador.id = td.transportador.id AND ov.etapaEntrega.id = ee.id AND ee.entrega.id = e.id AND e.demanda.id = d.id AND ov.tipo = 1 AND ov.status = 0) AS cargas_pendentes

   FROM TransportadorDemandaAutorizado td,
    DemandaTransporte d,
    Estabelecimento e1,
    Estabelecimento e2,
    Municipio m1,
    Municipio m2,
    Produto p,
    UnidadeQuantidade uq,
    UF u1,
    UF u2,
    Conta c1,
    Conta c2
  WHERE td.transportador.id = c2.id AND td.demanda.id = d.id AND d.origem.id = e1.id AND d.destino.id = e2.id AND e1.endereco.municipio.id = m1.id AND m1.uf.id = u1.id AND e2.endereco.municipio.id = m2.id AND m2.uf.id = u2.id AND d.produto.id = p.id AND d.unidadeQuantidade.id = uq.id AND d.tomador.id = c1.id