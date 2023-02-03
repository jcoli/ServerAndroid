SELECT id, contahora, datafinal, datainicio, descricao, descricaocurta, 
       totalhoras, valor, valorhora, cliente_id, planta_id, projeto_id, 
       usuario_codigo, totalhorash
  FROM timetracker
	WHERE usuario_codigo = 1;
