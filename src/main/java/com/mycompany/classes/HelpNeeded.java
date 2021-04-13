/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.classes;

import java.io.Serializable;

public class HelpNeeded implements Serializable {
    String username;
    boolean helpNeeded;

    public HelpNeeded(String username, boolean helpNeeded) {
        this.username = username;
        this.helpNeeded = helpNeeded;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isHelpNeeded() {
        return helpNeeded;
    }

    public void setHelpNeeded(boolean helpNeeded) {
        this.helpNeeded = helpNeeded;
    }
    
}
