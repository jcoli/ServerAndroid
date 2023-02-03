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
 * @brief Classe User
 * @author Jeferson Coli jcoli@tecnocoli.com.br
 * @date   09/11/2014
 */
public class User implements Serializable{
    
    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + Objects.hashCode(this.code);
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
        final User other = (User) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "name=" + name + ", code=" + code + '}';
    }
    
    

}
