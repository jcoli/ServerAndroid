SELECT p.id AS projeto_id,
    p.ativa AS projeto_ativa,
    p.descricaocurta AS projeto_descricaocurta,
    p.numero AS projeto_numero,
    p.statusatual AS projeto_statusatual,
    u.codigo AS usuario_codigo,
    u.login AS usuario_login,
    u.nome AS usuario_nome,
    c.nome AS cliente_nome
   
    FROM projeto p
     JOIN projeto_participante pp ON p.id = pp.projeto_id
     JOIN usuario u ON pp.participante_id  = u.codigo	
     JOIN cliente c ON p.cliente_id = c.id

    WHERE pp.participante_id = 708 AND p.ativa = true; 		

  