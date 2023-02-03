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
 * @brief Classe EndTransmission
 * @author Jeferson Coli jcoli@tecnocoli.com.br
 * @date   09/11/2014
 */
public class EndTransmission implements Serializable{
    
    private String userName;
    private Integer nObjects;
    private String typeOfConn;
    private Integer recObejcts;
    private Integer sendObejcts;
    private Integer codeOfError;

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

    public Integer getRecObejcts() {
        return recObejcts;
    }

    public void setRecObejcts(Integer recObejcts) {
        this.recObejcts = recObejcts;
    }

    public Integer getSendObejcts() {
        return sendObejcts;
    }

    public void setSendObejcts(Integer sendObejcts) {
        this.sendObejcts = sendObejcts;
    }

    public Integer getCodeOfError() {
        return codeOfError;
    }

    public void setCodeOfError(Integer codeOfError) {
        this.codeOfError = codeOfError;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.userName);
        hash = 53 * hash + Objects.hashCode(this.nObjects);
        hash = 53 * hash + Objects.hashCode(this.typeOfConn);
        hash = 53 * hash + Objects.hashCode(this.recObejcts);
        hash = 53 * hash + Objects.hashCode(this.sendObejcts);
        hash = 53 * hash + Objects.hashCode(this.codeOfError);
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
        final EndTransmission other = (EndTransmission) obj;
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.nObjects, other.nObjects)) {
            return false;
        }
        if (!Objects.equals(this.typeOfConn, other.typeOfConn)) {
            return false;
        }
        if (!Objects.equals(this.recObejcts, other.recObejcts)) {
            return false;
        }
        if (!Objects.equals(this.sendObejcts, other.sendObejcts)) {
            return false;
        }
        if (!Objects.equals(this.codeOfError, other.codeOfError)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EndTransmission{" + "userName=" + userName + ", nObjects=" + nObjects + ", typeOfConn=" + typeOfConn + ", recObejcts=" + recObejcts + ", sendObejcts=" + sendObejcts + ", codeOfError=" + codeOfError + '}';
    }
    
    

}
