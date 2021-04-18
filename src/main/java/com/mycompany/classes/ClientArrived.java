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
    private String CompNo;
    private String OS;

    public ClientArrived(String IP, String username, String CompNo, String OS) {
        this.IP = IP;
        this.username = username;
        this.CompNo = CompNo;
        this.OS = OS;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompNo() {
        return CompNo;
    }

    public void setCompNo(String CompNo) {
        this.CompNo = CompNo;
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }
    
    
}
