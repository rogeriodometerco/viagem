create or replace view pontooperacao as
select p.* from pontoviagem p, operacaoviagem o
where p.id = o.pontoviagem_id;



--------------------------

-- cargas realizadas
select * from operacaoviagem where tipo = 0 (COLETA) and status = 1 (REALIZADA) and datahorastatus >= HOJE;

-- cargas em trânsito
select * from entrega where status = 2 (A_CAMINHO) 

-- descargas realizadas
select * from operacaoviagem where tipo = 1 (COLETA) and status = 1 (REALIZADA) and datahorastatus >= HOJE;

-- cargas pendentes
select * from operacaoviagem where status = 0 (PENDENTE);




-----------------
	
	
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
  
  
  ---------------
  
  select o, p, v, e, ee
from OperacaoViagem o, PontoViagem p, Viagem v, Entrega e, EtapaEntrega ee
where o.pontoViagem = p and p.viagem = v
and o.etapaEntrega = ee and ee.entrega = e
and p.estabelecimento.id > 5


--------------


--select * From operacaoviagem
SELECT
	o.id as operacao_id,
	o.tipo as operacao_tipo,
	o.status as operacao_status,
	o.datahorastatus as operacao_datahorastatus,
	p.estabelecimento_id as ponto_estabelecimento_id,
	e1.nome as ponto_estabelecimento_nome,
	e1.municipio_id as ponto_estabelecimento_municipio_id,
	uf1.id as ponto_uf_id,
	uf1.abreviatura as ponto_uf_abreviatura,
	p.status as ponto_status,
	p.datahorastatus as ponto_datahorastatus,
	p.datachegadaacordada as ponto_datachegadaacordada,
	p.datahoraprevistachegada as ponto_datahoraprevistachegada,
	p.datahorachegada as ponto_datahorachegada,
	p.datahorasaida as ponto_datahorasaida,
	v.id as viagem_id,
	v.status as viagem_status,
	v.datahorastatus as viagem_datahorastatus,
	v.motorista_id as viagem_motorista_id,
	v.transportador_id as viagem_transportador_id,
	v.veiculo_id as viagem_veiculo_id,
	e.quantidade as entrega_quantidade,
	e.status as entrega_status,
	e.datahorastatus as entrega_datahorastatus,
	e.demanda_id as entrega_demanda_id,
	e.produto_id as entrega_produto_id,
	e.unidadequantidade_id as entrega_unidadequantidade_id
FROM
	operacaoviagem o,
	estabelecimento e1,
	municipio m1,
	uf uf1,
	pontoviagem p,
	viagem v,
	entrega e,
	etapaentrega ee
WHERE
	o.pontoviagem_id = p.id
	and p.viagem_id = v.id
	and o.etapaentrega_id = ee.id
	and ee.entrega_id = e.id
	and p.estabelecimento_id = e1.id
	and e1.municipio_id = m1.id
	and m1.uf_id = uf1.id