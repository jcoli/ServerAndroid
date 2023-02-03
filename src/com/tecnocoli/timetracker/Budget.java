package com.tecnocoli.timetracker;

/*
 * Copyright 2014 Tecnocoli/Jeferson Coli
 * http://www.tecnocoli.com.br 
 * All rights reserved
*/




import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * @Project TestCommAndroid 
 * @brief Classe Budget
 * @author Jeferson Coli jcoli@tecnocoli.com.br
 * @date   13/11/2014
 */
public class Budget implements Serializable{
    
    private Integer id;
    
    private String descricaoCurta;
    
    private String tipoHoras;
    
    private Double valorHora;
    
    private Integer projeto_id;
    
    private Integer budget_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricaoCurta() {
        return descricaoCurta;
    }

    public void setDescricaoCurta(String descricaoCurta) {
        this.descricaoCurta = descricaoCurta;
    }

    public String getTipoHoras() {
        return tipoHoras;
    }

    public void setTipoHoras(String tipoHoras) {
        this.tipoHoras = tipoHoras;
    }

    public Double getValorHora() {
        return valorHora;
    }

    public void setValorHora(Double valorHora) {
        this.valorHora = valorHora;
    }

    public Integer getProjeto_id() {
        return projeto_id;
    }

    public void setProjeto_id(Integer projeto_id) {
        this.projeto_id = projeto_id;
    }

    public Integer getBudget_id() {
        return budget_id;
    }

    public void setBudget_id(Integer budget_id) {
        this.budget_id = budget_id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.descricaoCurta);
        hash = 67 * hash + Objects.hashCode(this.tipoHoras);
        hash = 67 * hash + Objects.hashCode(this.valorHora);
        hash = 67 * hash + Objects.hashCode(this.projeto_id);
        hash = 67 * hash + Objects.hashCode(this.budget_id);
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
        final Budget other = (Budget) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.descricaoCurta, other.descricaoCurta)) {
            return false;
        }
        if (!Objects.equals(this.tipoHoras, other.tipoHoras)) {
            return false;
        }
        if (!Objects.equals(this.valorHora, other.valorHora)) {
            return false;
        }
        if (!Objects.equals(this.projeto_id, other.projeto_id)) {
            return false;
        }
        if (!Objects.equals(this.budget_id, other.budget_id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Budget{" + "id=" + id + ", descricaoCurta=" + descricaoCurta + ", tipoHoras=" + tipoHoras + ", valorHora=" + valorHora + ", projeto_id=" + projeto_id + ", budget_id=" + budget_id + '}';
    }

   

}
