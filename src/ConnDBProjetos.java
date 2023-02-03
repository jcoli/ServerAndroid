/*
 * Copyright 2014 Tecnocoli/Jeferson Coli
 * http://www.tecnocoli.com.br 
 * All rights reserved
 */



import com.tecnocoli.timetracker.Projeto;
import com.tecnocoli.timetracker.Budget;
import com.tecnocoli.timetracker.Feriado;
import com.tecnocoli.timetracker.Locais;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Project ServerAndroid
 * @brief Classe ConnDBProjetos
 * @author Jeferson Coli jcoli@tecnocoli.com.br
 * @date 13/11/2014
 */
public class ConnDBProjetos {

    private Connection conPost;
    private int usuario_codigo = 0;

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
                    + "    WHERE f.dataferiado = ; 	";

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
                f.setLocal(rsFer.getInt(7) );
                 System.out.println("DB --->> "+rsFer.getInt(1));
                System.out.println("DB --->> "+f.toString());
                listFeriado.add(f);
                f = new Feriado();
            }

            conPost.close();

        } catch (Exception e) {
            System.out.println("erro Projetos SQL" + e.toString());

        }
        
        
        
        
        
        return listFeriado;
    }    

    public List<Projeto> listProjetoUsuario(String login) {

        List<Projeto> listProjeto = new ArrayList<Projeto>();
        Projeto p = new Projeto();
        

        try {
            this.connection_Postgres();
            this.searchUserId(login);
            //System.out.println("listProjetos");
            String consProj = "SELECT p.id AS projeto_id,\n"
                    + "    p.ativa AS projeto_ativa,\n"
                    + "    p.descricaocurta AS projeto_descricaocurta,\n"
                    + "    p.numero AS projeto_numero,\n"
                    + "    p.statusatual AS projeto_statusatual,\n"
                    + "    c.nome AS cliente_nome, p.cliente_id AS cliente_id\n"
                    + "    FROM projeto p\n"
                    + "     JOIN projeto_participante pp ON p.id = pp.projeto_id\n"
                    + "     JOIN usuario u ON pp.participante_id  = u.codigo	\n"
                    + "     JOIN cliente c ON p.cliente_id = c.id\n"
                    + "    WHERE pp.participante_id = ? AND p.ativa = true; 	";

            PreparedStatement stmtProj = conPost.prepareStatement(consProj);
           // System.out.println("listProjetos 1");
            stmtProj.setInt(1, usuario_codigo);
           // System.out.println("listProjetos 2");
            ResultSet rsProj = stmtProj.executeQuery();
            
           // System.out.println("listProjetos 3");

            while (rsProj.next()) {
                p.setId(rsProj.getInt(1));
                p.setAtiva(rsProj.getBoolean(2));
                p.setDescricaoCurta(rsProj.getString(3));
                p.setNumero(rsProj.getString(4));
                p.setStatusAtual(rsProj.getString(5));
                p.setCliente(rsProj.getString(6));
                p.setCliente_id(rsProj.getInt(7) );
                 System.out.println("DB --->> "+rsProj.getInt(1));
                System.out.println("DB --->> "+p.toString());
                listProjeto.add(p);
                p = new Projeto();
            }

            conPost.close();

        } catch (Exception e) {
            System.out.println("erro Projetos SQL" + e.toString());

        }

        try {
            this.connection_Postgres();
            String consProj = "SELECT p.id AS projeto_id,\n"
                    + "    p.ativa AS projeto_ativa,\n"
                    + "    p.descricaocurta AS projeto_descricaocurta,\n"
                    + "    p.numero AS projeto_numero,\n"
                    + "    p.statusatual AS projeto_statusatual,\n"
                    + "    c.nome AS cliente_nome, p.cliente_id AS cliente_id\n"
                    + "    FROM projeto p\n"
                    + "JOIN cliente c ON p.cliente_id = c.id\n"
                    + "WHERE c.nome = 'SAI-BR' ;";

            PreparedStatement stmtProj = conPost.prepareStatement(consProj);
            ResultSet rsProj = stmtProj.executeQuery();

            while (rsProj.next()) {
                
                p.setId(rsProj.getInt(1));
                p.setAtiva(rsProj.getBoolean(2));
                p.setDescricaoCurta(rsProj.getString(3));
                p.setNumero(rsProj.getString(4));
                p.setStatusAtual(rsProj.getString(5));
                p.setCliente(rsProj.getString(6));
                p.setCliente_id(rsProj.getInt(7) );
               
                listProjeto.add(p);
                p = new Projeto();
            }

            conPost.close();

        } catch (Exception e) {
            System.out.println("erro projeto padrao SQL" + e.toString());

        }

        return listProjeto;
    }

    public List<Budget> listBudget(int projetoId) {
        
       // System.out.println("listBudget");

        List<Budget> listBudget = new ArrayList<Budget>();
        Budget b = new Budget();

       // System.out.println("listBudget1 " + projetoId);
        try {
            this.connection_Postgres();
            String consBud = "SELECT id, \n"
                    + "       descricaocurta,\n"
                    + "       tipohoras,\n"
                    + "       projeto_id,\n"
                    + "       valorhora\n"
                    + "  FROM budget where projeto_id = ? and tipobudget = 'hora';";

           // System.out.println("listBudget2");
            PreparedStatement stmtBud = conPost.prepareStatement(consBud);
            stmtBud.setInt(1, projetoId);
            ResultSet rsBud = stmtBud.executeQuery();

            while (rsBud.next()) {
            //    System.out.println("listBudget3");
                b.setId(rsBud.getInt(1));
                b.setDescricaoCurta(rsBud.getString(2));
                b.setTipoHoras(rsBud.getString(3));
                b.setProjeto_id(rsBud.getInt(4));
                b.setValorHora(rsBud.getDouble(5));
                listBudget.add(b);
                b = new Budget();
            }

            conPost.close();

        } catch (Exception e) {
            System.out.println("erro Budget" + e.toString());

        }

        
        return listBudget;
    }
    
    public List<Locais> listLocais() {
        
       // System.out.println("listBudget");

        List<Locais> listLocais = new ArrayList<Locais>();
        Locais l = new Locais();

       // System.out.println("listBudget1 " + projetoId);
        try {
            this.connection_Postgres();
            String consLoc = "SELECT id, \n"
                    + "       cidade,\n"
                    + "       estado\n"
                    + "  FROM locais;";

           // System.out.println("listBudget2");
            PreparedStatement stmtBud = conPost.prepareStatement(consLoc);
            ResultSet rsBud = stmtBud.executeQuery();

            while (rsBud.next()) {
            //    System.out.println("listBudget3");
                l.setId(rsBud.getInt(1));
                l.setCidade(rsBud.getString(2));
                l.setEstado(rsBud.getString(3));
                
                listLocais.add(l);
                l = new Locais();
            }

            conPost.close();

        } catch (Exception e) {
            System.out.println("erro Locais" + e.toString());

        }

        
        return listLocais;
    }

    public int searchUserId(String login) {

        int ret = 0;

        try {

           // System.out.println("searchUser " + login);

            String consUser = "SELECT codigo\n"
                    + "  FROM usuario where login = ?;";

            
            PreparedStatement consUserStmtPost = conPost.prepareStatement(consUser);
            consUserStmtPost.setString(1, login);

          //  System.out.println("searchUser " + consUserStmtPost.toString());
            
            ResultSet rsUser = consUserStmtPost.executeQuery();

            if (rsUser.next()) {
                ret = 0;
                this.usuario_codigo = rsUser.getInt(1);
            } else {
                ret = 1;
            }

        } catch (Exception e) {
            System.out.println("searchUser " + e.toString());

        }

        return ret;

    }

}
