package com.tecnocoli.timetracker;

/*
 * Copyright 2014 Tecnocoli/Jeferson Coli
 * http://www.tecnocoli.com.br 
 * All rights reserved
*/




import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @Project teste-socket-server 
 * @brief Classe timeTracker
 * @author Jeferson Coli jcoli@tecnocoli.com.br
 * @date   09/11/2014
 */
public class TimeTracker implements Serializable{
    
    private Integer id;

    private String descricao;

    private String descricaoCurta;

    private String contaHora;
    
    private Date dataInicio;

    private Date dataFinal;

    private Date totalHorasH;
    
    private Float totalHoras;
    
    private Float valor;
    
    private Float valorHora;
    
    private String login;
    
    private int projeto_id;
    
    private String codigoErro;
    
    private Integer codigoErroInt;
    
    private int budget_id;
    
    private int client_id;
    
    private int locais_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricaoCurta() {
        return descricaoCurta;
    }

    public void setDescricaoCurta(String descricaoCurta) {
        this.descricaoCurta = descricaoCurta;
    }

    public String getContaHora() {
        return contaHora;
    }

    public void setContaHora(String contaHora) {
        this.contaHora = contaHora;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Date getTotalHorasH() {
        return totalHorasH;
    }

    public void setTotalHorasH(Date totalHorasH) {
        this.totalHorasH = totalHorasH;
    }

    public Float getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(Float totalHoras) {
        this.totalHoras = totalHoras;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Float getValorHora() {
        return valorHora;
    }

    public void setValorHora(Float valorHora) {
        this.valorHora = valorHora;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getProjeto_id() {
        return projeto_id;
    }

    public void setProjeto_id(int projeto_id) {
        this.projeto_id = projeto_id;
    }

    public String getCodigoErro() {
        return codigoErro;
    }

    public void setCodigoErro(String codigoErro) {
        this.codigoErro = codigoErro;
    }

    public Integer getCodigoErroInt() {
        return codigoErroInt;
    }

    public void setCodigoErroInt(Integer codigoErroInt) {
        this.codigoErroInt = codigoErroInt;
    }

    public int getBudget_id() {
        return budget_id;
    }

    public void setBudget_id(int budget_id) {
        this.budget_id = budget_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getLocais_id() {
        return locais_id;
    }

    public void setLocais_id(int locais_id) {
        this.locais_id = locais_id;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.descricao);
        hash = 59 * hash + Objects.hashCode(this.descricaoCurta);
        hash = 59 * hash + Objects.hashCode(this.contaHora);
        hash = 59 * hash + Objects.hashCode(this.dataInicio);
        hash = 59 * hash + Objects.hashCode(this.dataFinal);
        hash = 59 * hash + Objects.hashCode(this.totalHorasH);
        hash = 59 * hash + Objects.hashCode(this.totalHoras);
        hash = 59 * hash + Objects.hashCode(this.valor);
        hash = 59 * hash + Objects.hashCode(this.valorHora);
        hash = 59 * hash + Objects.hashCode(this.login);
        hash = 59 * hash + this.projeto_id;
        hash = 59 * hash + Objects.hashCode(this.codigoErro);
        hash = 59 * hash + Objects.hashCode(this.codigoErroInt);
        hash = 59 * hash + this.budget_id;
        hash = 59 * hash + this.client_id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TimeTracker other = (TimeTracker) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.descricaoCurta, other.descricaoCurta)) {
            return false;
        }
        if (!Objects.equals(this.contaHora, other.contaHora)) {
            return false;
        }
        if (!Objects.equals(this.dataInicio, other.dataInicio)) {
            return false;
        }
        if (!Objects.equals(this.dataFinal, other.dataFinal)) {
            return false;
        }
        if (!Objects.equals(this.totalHorasH, other.totalHorasH)) {
            return false;
        }
        if (!Objects.equals(this.totalHoras, other.totalHoras)) {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        if (!Objects.equals(this.valorHora, other.valorHora)) {
            return false;
        }
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (this.projeto_id != other.projeto_id) {
            return false;
        }
        if (!Objects.equals(this.codigoErro, other.codigoErro)) {
            return false;
        }
        if (!Objects.equals(this.codigoErroInt, other.codigoErroInt)) {
            return false;
        }
        if (this.budget_id != other.budget_id) {
            return false;
        }
        if (this.client_id != other.client_id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TimeTracker{" + "id=" + id + ", descricao=" + descricao + ", descricaoCurta=" + descricaoCurta + ", contaHora=" + contaHora + ", dataInicio=" + dataInicio + ", dataFinal=" + dataFinal + ", totalHorasH=" + totalHorasH + ", totalHoras=" + totalHoras + ", valor=" + valor + ", valorHora=" + valorHora + ", login=" + login + ", projeto_id=" + projeto_id + ", codigoErro=" + codigoErro + ", codigoErroInt=" + codigoErroInt + ", budget_id=" + budget_id + ", client_id=" + client_id + '}';
    }
    
    

   
    
   

}
