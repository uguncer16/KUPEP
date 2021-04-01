/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.classes;

import java.io.Serializable;


public class ClientArrived implements Serializable {
    private String IP;
    private String username;
    
    public ClientArrived(String IP, String username) {
        this.IP = IP;
        this.username = username;
    }
    
    public String getIP(){
        return IP;
    }
    
    public String getUsername(){
        return username;
    }
    
    
}
