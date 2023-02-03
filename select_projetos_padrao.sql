SELECT p.id AS projeto_id,
    p.ativa AS projeto_ativa,
    p.descricaocurta AS projeto_descricaocurta,
    p.numero AS projeto_numero,
    p.statusatual AS projeto_statusatual
    FROM projeto p
JOIN cliente c ON p.cliente_id = c.id
WHERE c.nome = 'SAI-BR' ; 		

  