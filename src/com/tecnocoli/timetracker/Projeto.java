package com.tecnocoli.timetracker;

/*
 * Copyright 2014 Tecnocoli/Jeferson Coli
 * http://www.tecnocoli.com.br 
 * All rights reserved
*/




import java.io.Serializable;
import java.util.Objects;

/**
 * @Project TestCommAndroid 
 * @brief Classe Projeto
 * @author Jeferson Coli jcoli@tecnocoli.com.br
 * @date   13/11/2014
 */
public class Projeto implements Serializable{
    
    private Integer id;

    private String numero;

    private String descricaoCurta;

    private String statusAtual;

    private Boolean ativa;

    private String cliente;
    
    private int cliente_id;
    
    private int projeto_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescricaoCurta() {
        return descricaoCurta;
    }

    public void setDescricaoCurta(String descricaoCurta) {
        this.descricaoCurta = descricaoCurta;
    }

    public String getStatusAtual() {
        return statusAtual;
    }

    public void setStatusAtual(String statusAtual) {
        this.statusAtual = statusAtual;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getProjeto_id() {
        return projeto_id;
    }

    public void setProjeto_id(int projeto_id) {
        this.projeto_id = projeto_id;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.numero);
        hash = 41 * hash + Objects.hashCode(this.descricaoCurta);
        hash = 41 * hash + Objects.hashCode(this.statusAtual);
        hash = 41 * hash + Objects.hashCode(this.ativa);
        hash = 41 * hash + Objects.hashCode(this.cliente);
        hash = 41 * hash + this.cliente_id;
        hash = 41 * hash + this.projeto_id;
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
        final Projeto other = (Projeto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.numero, other.numero)) {
            return false;
        }
        if (!Objects.equals(this.descricaoCurta, other.descricaoCurta)) {
            return false;
        }
        if (!Objects.equals(this.statusAtual, other.statusAtual)) {
            return false;
        }
        if (!Objects.equals(this.ativa, other.ativa)) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (this.cliente_id != other.cliente_id) {
            return false;
        }
        if (this.projeto_id != other.projeto_id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Projeto{" + "id=" + id + ", numero=" + numero + ", descricaoCurta=" + descricaoCurta + ", statusAtual=" + statusAtual + ", ativa=" + ativa + ", cliente=" + cliente + ", cliente_id=" + cliente_id + ", projeto_id=" + projeto_id + '}';
    }
    
    

    
}
