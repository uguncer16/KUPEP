/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.kupep;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThread extends Thread  
{    
    ServerController s;
    
    public ServerThread(ServerController s) {
        this.s = s;
    }
    public void run()  
    {    
        try {
            ObjectEchoServer.go(s);
        } catch (Exception ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

}   