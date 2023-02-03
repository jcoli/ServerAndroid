/*
 * Copyright 2014 Tecnocoli/Jeferson Coli
 * http://www.tecnocoli.com.br 
 * All rights reserved
 */

import com.tecnocoli.timetracker.Feriado;
import com.tecnocoli.timetracker.TimeTracker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.Instant;
import static java.time.Instant.now;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Project ServerAndroid
 * @brief Classe ConnDB
 * @author Jeferson Coli jcoli@tecnocoli.com.br
 * @date 10/11/2014
 */
public class ConnDBTimeTracker {

    private Connection conPost;
    private ResultSet rsProjPost;
    private String consProjSQlPost;
    private PreparedStatement projStmtPost;

    private String consSQlPost;
    private PreparedStatement pStmtPost;

    private ResultSet rsSql;

    private int usuario_codigo = 0;
    private int projeto_id = 0;
    private int cliente_id = 0;
    private int planta_id = 0;
    private float valor = 0;
    private float valorHora = 0;
    private float totalHoras = 0;
    private String contaHora = "";
    private float horaadnoturno = 0.0F;
    private float horaextra100 = 0.0F;
    private float horaextra50 = 0.0F;
    private float horanormal = 0.0F;
    Date diffHours = new Date();

    /*
     Codigos de Erro 
     1 = Erro Conexão
     2 = Sobreposição de horário
     3 = usuário não encontrado) context).setIdBudget(idBudget)
     4 = dataInicial > dataFinal
     5 = dias diferentes - dataInicial e dataFinal 
     6 = Projeto Inativo
     7 = Projeto não encontrado
     8 = erro Insert TimeTracker
     9 = erro Despesa
     
     */
    private void connection_Postgres() {
        String driver;
        String user;
        String endereco;
        String pass;

        try {
            driver = "org.postgresql.Driver";
            user = "saibr";
            pass = "@utoma09";
            endereco = "jdbc:postgresql://localhost/projetos2";
//            endereco = "jdbc:postgresql://192.168.30.41/projetos2";
            Class.forName(driver);
            conPost = DriverManager.getConnection(endereco, user, pass);

        } catch (Exception e) {
            System.out.println("erro Postgres " + e.toString());
        }
    }

    public int addTimeTracker(TimeTracker timeTracker) {
        int ret = 0;
        boolean cont = true;

        this.connection_Postgres();

        if (timeTracker.getDataInicio().getTime() > timeTracker.getDataFinal().getTime()) {
            ret = 4;
            cont = false;
        }

        if (cont) {
            if (timeTracker.getDataInicio().getDay() != timeTracker.getDataFinal().getDay()) {
                ret = 5;
                cont = false;
            }
        }

        if (cont) {
            if (this.searchUserId(timeTracker.getLogin()) != 0) {
                ret = 3;
                cont = false;
            }
        }

        if (cont) {
            if (this.searchProjeto(timeTracker.getProjeto_id()) != 0) {
                ret = 7;
                cont = false;
            }
        }

        if (cont) {
            if (this.searchTime(timeTracker) != 0) {
                ret = 2;
                cont = false;
            }
        }

        if (cont) {

            if (this.insertTimeTracker(timeTracker) != 0) {
                System.out.println("Erro Insert " + ret);
                ret = 8;
                cont = false;
            }

        }

//        if (cont) {
//            if (this.somaDespesa() != 0);
//            {
//                ret = 0;
//            }
//        }
        try {
            conPost.close();
        } catch (Exception e) {
            System.out.println("erro time " + e.toString());

        }
        System.out.println("============================>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<< " + ret);
        return ret;
    }

    public int searchTime(TimeTracker inTime) {

        int timeExist = 0;
        try {

            String consTime = "SELECT id FROM timetracker t"
                    + " where usuario_codigo=? and "
                    + "   (t.dataInicio<=? and t.dataFinal>=?) and"
                    + "  ((t.dataInicio>=? and t.dataFinal<=?)"
                    + "OR (t.dataInicio<=? and t.dataFinal<=?)"
                    + "OR (t.dataInicio>=? and t.dataFinal>=?)"
                    + "OR (t.dataInicio<=? and t.dataFinal>=?))";

            PreparedStatement consTimeStmtPost = conPost.prepareStatement(consTime);

            java.util.Date dateIni = inTime.getDataInicio();

            java.sql.Timestamp sqlDateFin = new java.sql.Timestamp(inTime.getDataFinal().getTime());
            java.sql.Timestamp sqlDateIni = new java.sql.Timestamp(dateIni.getTime());

            System.out.println("search Time " + this.usuario_codigo + " - " + sqlDateFin.toString() + " - " + sqlDateIni.toString() + " - " + dateIni.toString());

            consTimeStmtPost.setInt(1, this.usuario_codigo);
            consTimeStmtPost.setTimestamp(2, sqlDateFin);
            consTimeStmtPost.setTimestamp(3, sqlDateIni);

            consTimeStmtPost.setTimestamp(4, sqlDateIni);
            consTimeStmtPost.setTimestamp(5, sqlDateFin);
            consTimeStmtPost.setTimestamp(6, sqlDateIni);
            consTimeStmtPost.setTimestamp(7, sqlDateFin);
            consTimeStmtPost.setTimestamp(8, sqlDateIni);
            consTimeStmtPost.setTimestamp(9, sqlDateFin);
            consTimeStmtPost.setTimestamp(10, sqlDateIni);
            consTimeStmtPost.setTimestamp(11, sqlDateFin);

            System.out.println("search Time " + consTimeStmtPost.toString());

            ResultSet rsTime = consTimeStmtPost.executeQuery();

            if (rsTime.next()) {
                System.out.println("Search Time " + rsTime.getInt(1));
                timeExist = 2;

            } else {

                timeExist = 0;

            }

        } catch (Exception e) {
            System.out.println("erro  Search Time" + e.toString());

        }
        return timeExist;
    }

    public int searchUserId(String login) {

        int ret = 0;

        try {

            System.out.println("searachUser " + login);

            String consUser = "SELECT codigo\n"
                    + "  FROM usuario where login = ?;";

            PreparedStatement consUserStmtPost = conPost.prepareStatement(consUser);
            consUserStmtPost.setString(1, login);

            ResultSet rsUser = consUserStmtPost.executeQuery();

            if (rsUser.next()) {

                ret = 0;
                this.usuario_codigo = rsUser.getInt(1);
            } else {
                ret = 1;
            }

        } catch (Exception e) {
            System.out.println("searachUser " + e.toString());

        }

        return ret;

    }

    public int searchProjeto(int id) {
        int ret = 0;

        try {

            consProjSQlPost = "SELECT id, ativa, cliente_id, planta_id\n"
                    + "  FROM projeto WHERE id = ?";
            projStmtPost = conPost.prepareStatement(consProjSQlPost);
            System.out.println("consultProjeto1 " + id);
            projStmtPost.setInt(1, id);
            System.out.println("consultProjeto2 ");
            rsProjPost = projStmtPost.executeQuery();
            System.out.println("consultProjeto3 ");

            if (rsProjPost.next()) {

                System.out.println("consultProjeto1 " + rsProjPost.getInt(3));
                if (rsProjPost.getBoolean(2)) {
                    this.projeto_id = rsProjPost.getInt(1);
                    this.cliente_id = rsProjPost.getInt(3);
                    this.planta_id = rsProjPost.getInt(4);
                } else {
                    ret = 6;
                }
            } else {
                ret = 7;
            }

        } catch (Exception e) {
            System.out.println("erro projeto " + e.toString());
            ret = 1;

        }
        return ret;
    }

    public List<Feriado> listFeriado(java.sql.Date data) {

        List<Feriado> listFeriado = new ArrayList<Feriado>();
        Feriado f = new Feriado();

        try {
            this.connection_Postgres();

            //System.out.println("listProjetos");
            String consProj = "SELECT p.id AS feriado_id,\n"
                    + "    f.abrangencia AS feriado_abran,\n"
                    + "    f.ano AS feriado_ano,\n"
                    + "    f.dataferiado AS feriado_data,\n"
                    + "    f.nome AS feriado_nome,\n"
                    + "    f.tipo AS feriado_tipo,\n "
                    + "    f.local_id AS feriado_localid\n"
                    + "    FROM feriado f\n"
                    + "    WHERE f.dataferiado = ?; ";

            PreparedStatement stmtFer = conPost.prepareStatement(consProj);
            // System.out.println("listProjetos 1");
            stmtFer.setDate(1, data);
            // System.out.println("listProjetos 2");
            ResultSet rsFer = stmtFer.executeQuery();

           // System.out.println("listProjetos 3");
            while (rsFer.next()) {
                f.setId(rsFer.getInt(1));
                f.setAbrangencia(rsFer.getString(2));
                f.setAno(rsFer.getInt(3));
                f.setDataFeriado(rsFer.getDate(4));
                f.setNome(rsFer.getString(5));
                f.setTipo(rsFer.getString(6));
                f.setLocal(rsFer.getInt(7));
                System.out.println("DB --->> " + rsFer.getInt(1));
                System.out.println("DB --->> " + f.toString());
                listFeriado.add(f);
                f = new Feriado();
            }

            conPost.close();

        } catch (Exception e) {
            System.out.println("erro Projetos SQL" + e.toString());

        }

        return listFeriado;
    }

    public int insertTimeTracker(TimeTracker timeTracker) {
        int ret = 0;
        String msg;
        
        this.calculoHoras(timeTracker);
        

        System.out.println("insertTimeTracker");

        try {

            consSQlPost = "INSERT INTO timetracker(\n"
                    + "            contahora, datafinal, datainicio, descricaocurta, \n"  //4 - 
                    + "            totalhoras, valor, valorhora, cliente_id,  projeto_id, \n"  //9 - 5
                    + "            usuario_codigo, totalhorash, tipo, budget_id, local_id,"  //14 -5
                    + "            horaadnoturno, horaextra100,  horaextra50, horanormal  )\n" //18 - 4
                    + "    VALUES (?, ?, ?, ?, \n"
                    + "            ?, ?, ?, ?, ?,  \n"
                    + "            ?, ?, ?, ?, ?,\n"
                    + "            ?, ? ,? , ? );";
            pStmtPost = conPost.prepareStatement(consSQlPost);

            System.out.println("insertTimeTracker1");

            pStmtPost.setString(1, timeTracker.getContaHora());

            java.sql.Timestamp sqlDate = new java.sql.Timestamp(timeTracker.getDataFinal().getTime());
            pStmtPost.setTimestamp(2, sqlDate);

            System.out.println("insertTimeTracker1 " + timeTracker.getDataFinal().toString() + " - " + sqlDate.toString());

            sqlDate = new java.sql.Timestamp(timeTracker.getDataInicio().getTime());
            pStmtPost.setTimestamp(3, sqlDate);

            pStmtPost.setString(4, timeTracker.getDescricaoCurta());

            pStmtPost.setFloat(6, 0);
            pStmtPost.setFloat(7, timeTracker.getValorHora());
            pStmtPost.setInt(8, timeTracker.getClient_id());
            
            pStmtPost.setInt(9, this.projeto_id);
            pStmtPost.setInt(10, this.usuario_codigo);

            System.out.println("insertTimeTracker2");

            pStmtPost.setFloat(5, this.totalHoras);

            pStmtPost.setFloat(6, (this.valor));

            float tempM = (this.totalHoras % 60);
            int tempH = (int) (this.totalHoras / 60);
            diffHours.setMinutes((int) tempM);
            diffHours.setHours(tempH);
            System.out.println("insertTimeTracker3a");

            timeTracker.setTotalHorasH(diffHours);
            java.sql.Time sqlTime = new java.sql.Time(timeTracker.getTotalHorasH().getTime());

            pStmtPost.setTime(11, sqlTime);
            pStmtPost.setString(12, "normal");
            pStmtPost.setInt(13, timeTracker.getBudget_id());
            pStmtPost.setInt(14, timeTracker.getLocais_id());
            
            pStmtPost.setFloat(15, (this.horaadnoturno));
            pStmtPost.setFloat(16, (this.horaextra100));
            pStmtPost.setFloat(17, (this.horaextra50));
            pStmtPost.setFloat(18, (this.horanormal));

            System.out.println("insertTimeTracker4");

            pStmtPost.execute();

            System.out.println("insertTimeTracker4a");

            ret = 0;

        } catch (Exception e) {
            System.out.println("erro insert timeTracker " + e.toString());
            ret = 1;
        }

        return ret;
    }

    public int somaDespesa() {
        int ret = 0;
        PreparedStatement pStmtPostDesp;
        ResultSet rsProjPostDesp;
        String consSqlDesp;

        try {
            System.out.println("soma Despesa");

            consSqlDesp = "SELECT id  FROM projeto;";
            pStmtPostDesp = conPost.prepareStatement(consSqlDesp);
            rsProjPostDesp = pStmtPostDesp.executeQuery();
            int i = 1;

            while (rsProjPostDesp.next()) {
                //System.out.println("soma Despesa " + rsProjPostDesp.getInt(1));
                this.consultProjetoDespesa(rsProjPostDesp.getInt(1)); //realizado
            }

        } catch (Exception e) {
            System.out.println("erro somadespesa " + e.toString());

        }

        return ret;
    }

    public Double consultProjetoDespesa(int id) {
        double despesa = 0;
        PreparedStatement pStmtPostPDesp;
        PreparedStatement projStmtPostPDesp;
        ResultSet rsProjPostPDesp;
        String consSqlPDesp;

        try {
            consSqlPDesp = "SELECT SUM(realizado+previsto) as R "
                    + "FROM despesa WHERE projeto_id=? ;";
            projStmtPostPDesp = conPost.prepareStatement(consSqlPDesp);
            projStmtPostPDesp.setInt(1, id);
            rsProjPostPDesp = projStmtPostPDesp.executeQuery();

            while (rsProjPostPDesp.next()) {
                System.out.println("consultProjetoDespesa " + id + "-" + rsProjPostPDesp.getInt(1));
                despesa = rsProjPostPDesp.getDouble(1);
                this.updateDespesa(despesa, id);
                this.updateFlagBudget(despesa, id);
            }
            return despesa;
        } catch (Exception e) {
            System.out.println("erro projetodespesa " + e.toString());
        }
        return despesa;
    }

    public void updateDespesa(double despesa, int id) {

        PreparedStatement pStmtPostUpDesp;
        PreparedStatement projStmtPostUpDesp;
        ResultSet rsProjPostUpDesp;
        String consSqlUpDesp;
        try {
            System.out.println("updateDespesa " + despesa + "- " + id);
            consSqlUpDesp = "UPDATE projeto SET valordespesas = ?"
                    + "WHERE id=? ;";
            projStmtPostUpDesp = conPost.prepareStatement(consSqlUpDesp);
            projStmtPostUpDesp.setDouble(1, despesa);
            projStmtPostUpDesp.setInt(2, id);
            projStmtPostUpDesp.execute();

        } catch (Exception e) {
            System.out.println("erro updatedespesa " + e.toString());

        }

    }

    public void updateFlagBudget(double despesa, int id) {
        double budget = somaBudget(id);
        String flag = "cancelar16";
        double percent;
        percent = 0;
        if (despesa < 0) {
            if (budget > 0) {
                percent = (despesa / budget) * -1;
            } else {
                percent = 0;
            }
        } else {
            percent = 0.10;
        }

        System.out.println("aqui" + percent + " - " + despesa + " - " + budget);
        if (percent == 0) {
            flag = "cancelar16";
        } else if (percent <= 0.80 && percent > 0) {
            flag = "OK";
        } else if (percent > 0.80 && percent < 0.98) {
            flag = "Waning";
        } else if (percent > 0.98) {
            flag = "Danger";
        } else {
            flag = "cancelar16";
        }

        PreparedStatement pStmtPostUpDesp;
        PreparedStatement projStmtPostUpDesp;
        ResultSet rsProjPostUpDesp;
        String consSqlUpDesp;
        try {
            System.out.println("updateFlag " + flag + "- " + id);
            consSqlUpDesp = "UPDATE projeto SET alertabudget = ?"
                    + "WHERE id=? ;";
            projStmtPostUpDesp = conPost.prepareStatement(consSqlUpDesp);
            projStmtPostUpDesp.setString(1, flag);
            projStmtPostUpDesp.setInt(2, id);
            projStmtPostUpDesp.execute();

        } catch (Exception e) {
            System.out.println("erro updateFlag " + e.toString());

        }

    }

    public double somaBudget(int id) {
        double budget = 0;
        PreparedStatement pStmtPostPDesp;
        PreparedStatement projStmtPostPDesp;
        ResultSet rsProjPostPDesp;
        String consSqlPDesp;

        try {
            consSqlPDesp = "SELECT SUM(valorbudget) as R "
                    + "FROM projeto WHERE id=? ;";
            projStmtPostPDesp = conPost.prepareStatement(consSqlPDesp);
            projStmtPostPDesp.setInt(1, id);
            rsProjPostPDesp = projStmtPostPDesp.executeQuery();

            while (rsProjPostPDesp.next()) {
                budget = rsProjPostPDesp.getInt(1);
            }
            return budget;
        } catch (Exception e) {
            System.out.println("erro budget " + e.toString());
        }
        return budget;
    }

    public void calculoHoras(TimeTracker timeTracker) {

        //Utilitates utilitates = new Utilitates();
        String nome = "";
        String mes = "";
        String ano = "";
        String login = "";
        int duration = 0;
        Date dur = new Date();

        this.horanormal = (0.0F);
        this.horaadnoturno = (0.0F);
        this.horaextra50 = (0.0F);
        this.horaextra100 = (0.0F);
        this.totalHoras = (0.0F);

        //Usuario user = timeTracker.getUsuario();
        login = timeTracker.getLogin();

        Instant instantInicio = now();
        LocalDateTime dateInicio = LocalDateTime.ofInstant(instantInicio, ZoneId.systemDefault());

        Instant instantFinal = now();
        LocalDateTime dateFinal = LocalDateTime.ofInstant(instantFinal, ZoneId.systemDefault());

//        Instant instInicioProv = now();
//        LocalDateTime dateInicioProv = LocalDateTime.ofInstant(instInicioProv, ZoneId.systemDefault());
//
//        Instant instFinalProv = now();
//        LocalDateTime dateFinalProv = LocalDateTime.ofInstant(instFinalProv, ZoneId.systemDefault());
//
//        Instant instInicioProv1 = now();
//        LocalDateTime dateInicioProv1 = LocalDateTime.ofInstant(instInicioProv1, ZoneId.systemDefault());
//
//        Instant instFinalProv1 = now();
//        LocalDateTime dateFinalProv1 = LocalDateTime.ofInstant(instFinalProv, ZoneId.systemDefault());
//
//        Instant instDatePeriodB = now();
//        LocalDateTime datePeriodB = LocalDateTime.ofInstant(instFinalProv, ZoneId.systemDefault());
//
//        Instant instDatePeriodF = now();
//        LocalDateTime datePeriodF = LocalDateTime.ofInstant(instFinalProv, ZoneId.systemDefault());
//
//        Instant instDatePeriodCurrent = now();
//        LocalDateTime datePeriodCurrent = LocalDateTime.ofInstant(instFinalProv, ZoneId.systemDefault());
//
        Instant instant = now();
        LocalDateTime localTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        Instant intervalo = localTime.atZone(ZoneId.systemDefault()).toInstant();
        Date dateIntervalo = Date.from(intervalo);

        float diffInMinutes = 0.00F;
        float totalHoras = 0.00F;
        float totalHN = 0.00F;
        float totalAN = 0.00F;
        float totalHe50 = 0.00F;
        float totalHe100 = 0.00F;
        float tempM = 0.00F; //(diffInMinutes % 60);
        int tempH = 0;//(int) (diffInMinutes / 60);

        String weekDay = "";
        String weekDayProv1 = "";
        String tipoProv1 = "";

        boolean feriado = false;

        Date inicioT = timeTracker.getDataInicio();
        Date inicio = new Date();
        Date fim = new Date();

        inicio.setDate(inicioT.getDate());
        inicio.setMonth(inicioT.getMonth());
        inicio.setYear(inicioT.getYear());
        inicio.setHours(0);
        inicio.setMinutes(0);

        fim.setDate(inicioT.getDate());
        fim.setMonth(inicioT.getMonth());
        fim.setYear(inicioT.getYear());
        fim.setHours(23);
        fim.setMinutes(59);

        int iProv = 0;

//      
        diffInMinutes = 0.00F;
        totalAN = 0.00F;
        totalHe50 = 0.00F;
        totalHe100 = 0.00F;
        totalHN = 0.00F;
        totalHoras = 0.00F;

        tempM = 0.00F;
        tempH = 0;

        instantInicio = Instant.ofEpochMilli(timeTracker.getDataInicio().getTime());
        dateInicio = LocalDateTime.ofInstant(instantInicio, ZoneId.systemDefault());
        instantFinal = Instant.ofEpochMilli(timeTracker.getDataFinal().getTime());
        dateFinal = LocalDateTime.ofInstant(instantFinal, ZoneId.systemDefault());

        instant = (timeTracker.getDataInicio().toInstant());

        localTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DayOfWeek dayOfWeek = localTime.getDayOfWeek();
        weekDay = weekDay(dayOfWeek);
        feriado = this.testFeriado(timeTracker);
        

        if (java.time.Duration.between(dateInicio, dateFinal).toMinutes() > 360) {
            totalHoras = java.time.Duration.between(dateInicio, dateFinal).toMinutes() - 60;
        } else {
            totalHoras = java.time.Duration.between(dateInicio, dateFinal).toMinutes();
        }

        //Hora Normal
        if ((!(weekDay.equals("Sabado")) && !(weekDay.equals("Domingo")) && (feriado == false))) {

            instantInicio = Instant.ofEpochMilli(timeTracker.getDataInicio().getTime());
            dateInicio = LocalDateTime.ofInstant(instantInicio, ZoneId.systemDefault());
            instantFinal = Instant.ofEpochMilli(timeTracker.getDataFinal().getTime());
            dateFinal = LocalDateTime.ofInstant(instantFinal, ZoneId.systemDefault());

            diffInMinutes = java.time.Duration.between(dateInicio, dateFinal).toMinutes();

            if (diffInMinutes > 360) {
                totalHN = diffInMinutes - 60;
            } else {
                totalHN = diffInMinutes;
            }

            diffInMinutes = 0.00F;

        }//Fim Hora Normal

        //Adicional Noturno
        instantInicio = Instant.ofEpochMilli(timeTracker.getDataInicio().getTime());
        dateInicio = LocalDateTime.ofInstant(instantInicio, ZoneId.systemDefault());
        instantFinal = Instant.ofEpochMilli(timeTracker.getDataFinal().getTime());
        dateFinal = LocalDateTime.ofInstant(instantFinal, ZoneId.systemDefault());

        if (dateInicio.getHour() < 6) {

            if (dateFinal.getHour() > 6) {
                dateFinal = dateFinal.withHour(06);
                dateFinal = dateFinal.withMinute(00);
            }
            if (dateInicio.getHour() != 0) {
                diffInMinutes = java.time.Duration.between(dateInicio, dateFinal).toMinutes();
            } else {
                diffInMinutes = dateFinal.getHour() * 60;
            }
            totalAN = diffInMinutes;
            diffInMinutes = 0.0F;
        }

        if (dateFinal.getHour() > 22) {

            if (dateInicio.getHour() < 22) {
                dateInicio = dateInicio.withHour(22);
                dateInicio = dateInicio.withMinute(00);
            }
            diffInMinutes = java.time.Duration.between(dateInicio, dateFinal).toMinutes();
            totalAN = totalAN + diffInMinutes;
            diffInMinutes = 0.0F;

        }

        diffInMinutes = 0.0F;

        instantInicio = Instant.ofEpochMilli(timeTracker.getDataInicio().getTime());
        dateInicio = LocalDateTime.ofInstant(instantInicio, ZoneId.systemDefault());
        instantFinal = Instant.ofEpochMilli(timeTracker.getDataFinal().getTime());
        dateFinal = LocalDateTime.ofInstant(instantFinal, ZoneId.systemDefault());

        //HE 50%
        if (((weekDay.equals("Sabado")) && (feriado == false))) {

            diffInMinutes = java.time.Duration.between(dateInicio, dateFinal).toMinutes();

            if (diffInMinutes > 360) {
                totalHe50 = diffInMinutes - 60;
            } else {
                totalHe50 = diffInMinutes;
            }

            diffInMinutes = 0.00F;

        }// FIM HE 50%

        if (((weekDay.equals("Domingo") || (feriado == true)))) {

            diffInMinutes = java.time.Duration.between(dateInicio, dateFinal).toMinutes();
            //totalHoras = java.time.Duration.between(dateInicio, dateFinal).toMinutes();

            if (diffInMinutes > 360) {
                totalHe100 = diffInMinutes - 60;
            } else {
                totalHe100 = diffInMinutes;
            }

            diffInMinutes = 0.00F;

        }

//        }
//        logger.info("Horas Normais: "+totalHN);
//        logger.info("Horas Totais: "+totalHoras);
        this.horanormal = (totalHN / 60);
        this.horaadnoturno = (totalAN / 60);
        this.horaextra50 = (totalHe50 / 60);
        this.horaextra100 = (totalHe100 / 60);
        this.totalHoras = (totalHoras / 60);

       
        this.valor = ((this.horaextra100 * 2) + (this.horaextra50 * 1.50F)
                + this.horanormal + (this.horaadnoturno * 1.2F)) * timeTracker.getValorHora();
        
        

    }

    public String weekDay(DayOfWeek d) {
        String weekDay = "";
        int dia = d.getValue();
        switch (dia) {

            case 1:
                weekDay = "Segunda";
                break;
            case 2:
                weekDay = "Terça";
                break;
            case 3:
                weekDay = "Quarta";
                break;
            case 4:
                weekDay = "Quinta";
                break;
            case 5:
                weekDay = "Sexta";
                break;
            case 6:
                weekDay = "Sabado";
                break;
            case 7:
                weekDay = "Domingo";
                break;
        }

        return weekDay;
    }

    public boolean testFeriado(TimeTracker t) {

        boolean feriado = false;

        feriado = false;

//        System.out.println("======================================================================================");
//        System.out.println("data inicio " + dateInicioAbrang.toString());
        int dia = t.getDataInicio().getDay();
        int mes = t.getDataInicio().getMonth();;
        int ano = t.getDataInicio().getYear();

        java.sql.Date data = new java.sql.Date(dia, mes, ano);

        List<Feriado> listaFeriado = this.listFeriado(data);

        if (listaFeriado.isEmpty()) {
            feriado = false;

        } else {
            Feriado thisFeriado = new Feriado();
            thisFeriado = listaFeriado.get(0);

            if (thisFeriado.getAbrangencia().equals("Nacional")) {
//                        System.out.println("data inicio depois Nacional " + dateFeriado.toString());
                feriado = true;
            }

            

           

                if (thisFeriado.getAbrangencia().equals("Estadual")) {
//                            System.out.println("data inicio depois Estadual " + dateFeriado.toString());
                    if (t.getLocais_id() == (thisFeriado.getLocal())) {
                        feriado = true;
                    } else {
                        feriado = false;
                    }

                } else if (thisFeriado.getAbrangencia().equals("Municipal")) {
//                            System.out.println("data inicio depois Municipal " + dateFeriado.toString());
//                            System.out.println("data inicio depois Municipal " + dateFeriado.toString() + "--" + t.getLocal().getCidade() + "--" + thisFeriado.getLocal().getCidade());
                    if (t.getLocais_id() == (thisFeriado.getLocal())) {
//                                System.out.println("data inicio depois Municipal True");
                        feriado = true;
                    } else {
                        feriado = false;
//                                System.out.println("data inicio depois Municipal False");
                    }
                }

            
        }

        return feriado;
    }

}
