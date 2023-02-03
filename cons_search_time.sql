SELECT * FROM TimeTracker 
                where usuario_codigo=1 and 
                (datainicio<='2014-11-01 14:38:00.0' and dataFinal>='2014-11-01 08:38:00') and
                ((datainicio>='2014-11-01 08:38:00.0' and dataFinal<='2014-11-01 14:38:00.0')
                OR (datainicio<='2014-11-01 08:38:00.0' and dataFinal<='2014-11-01 14:38:00.0')
                OR (datainicio>='2014-11-01 08:38:00.0' and dataFinal>='2014-11-01 14:38:00.0')
                OR (datainicio<='2014-11-01 08:38:00.0' and dataFinal>='2014-11-01 14:38:00.0'));