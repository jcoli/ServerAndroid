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
 * @brief Classe BeginTransmission
 * @author Jeferson Coli jcoli@tecnocoli.com.br
 * @date   09/11/2014
 */
public class BeginTransmission implements Serializable{
    
    private String userName;
    private Integer nObjects;
    private String typeOfConn;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getnObjects() {
        return nObjects;
    }

    public void setnObjects(Integer nObjects) {
        this.nObjects = nObjects;
    }

    public String getTypeOfConn() {
        return typeOfConn;
    }

    public void setTypeOfConn(String typeOfConn) {
        this.typeOfConn = typeOfConn;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.userName);
        hash = 53 * hash + Objects.hashCode(this.nObjects);
        hash = 53 * hash + Objects.hashCode(this.typeOfConn);
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
        final BeginTransmission other = (BeginTransmission) obj;
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.nObjects, other.nObjects)) {
            return false;
        }
        if (!Objects.equals(this.typeOfConn, other.typeOfConn)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BeginTransmission{" + "userName=" + userName + ", nObjects=" + nObjects + ", typeOfConn=" + typeOfConn + '}';
    }
    
    
    

}
