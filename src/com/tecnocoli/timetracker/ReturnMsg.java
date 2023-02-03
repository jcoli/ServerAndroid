package com.tecnocoli.timetracker;

/*
 * Copyright 2014 Tecnocoli/Jeferson Coli
 * http://www.tecnocoli.com.br 
 * All rights reserved
*/




import java.io.Serializable;
import java.util.Objects;

/**
 * @Project teste-socket-server 
 * @brief Classe ReturnMsg
 * @author Jeferson Coli jcoli@tecnocoli.com.br
 * @date   10/11/2014
 */
public class ReturnMsg implements Serializable{
    
    private String userName;
    private String typeOfConn;
    private Integer returnStatus;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTypeOfConn() {
        return typeOfConn;
    }

    public void setTypeOfConn(String typeOfConn) {
        this.typeOfConn = typeOfConn;
    }

    public Integer getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(Integer returnStatus) {
        this.returnStatus = returnStatus;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.userName);
        hash = 83 * hash + Objects.hashCode(this.typeOfConn);
        hash = 83 * hash + Objects.hashCode(this.returnStatus);
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
        final ReturnMsg other = (ReturnMsg) obj;
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.typeOfConn, other.typeOfConn)) {
            return false;
        }
        if (!Objects.equals(this.returnStatus, other.returnStatus)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ReturnMsg{" + "userName=" + userName + ", typeOfConn=" + typeOfConn + ", returnStatus=" + returnStatus + '}';
    }
    
    
}
