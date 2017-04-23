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
