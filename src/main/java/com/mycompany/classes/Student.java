/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.classes;

import io.netty.channel.ChannelHandlerContext;


public class Student {
    ChannelHandlerContext ctx;
    String username;
    String IP;
    String CompNo;
    String OS;
    boolean PMEnabled;
    boolean submitted;
    String connected;
    
    public Student(ChannelHandlerContext ctx, String username, String IP, String connected) {
        this.ctx = ctx;
        this.username = username;
        this.IP = IP;
        this.connected = connected;
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public String getCompNo() {
        return CompNo;
    }

    public void setCompNo(String CompNo) {
        this.CompNo = CompNo;
    }
    
    

    public boolean isPMEnabled() {
        return PMEnabled;
    }
    
    public String getSubmitted() {
        if (submitted)
            return "Submitted";
                    else
            return "Not-Submitted";
    }

    
    public String getPMEnabled() {
        if (PMEnabled)
            return "ON";
                    else
            return "OFF";
    }

    public void setPMEnabled(boolean PMEnabled) {
        this.PMEnabled = PMEnabled;
    }

    
    
    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getConnected() {
        return connected;
    }

    public void setConnected(String connected) {
        this.connected = connected;
    }
    
    
    
}
