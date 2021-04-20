/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proxy;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Server2Thread extends Thread  
{    
    ProxyController p;
    
    public Server2Thread(ProxyController p) {
        this.p = p;
    }
    public void run()  
    {    
        try {
            ObjectEchoServer.go(p);
        } catch (Exception ex) {
            Logger.getLogger(Server2Thread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
}