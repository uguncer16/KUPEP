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
    boolean PMEnabled;
    String connected;
    
    public Student(ChannelHandlerContext ctx, String username, String IP, String connected) {
        this.ctx = ctx;
        this.username = username;
        this.IP = IP;
        this.connected = connected;
    }

    public boolean isPMEnabled() {
        return PMEnabled;
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
