/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proxy;

import java.util.logging.Level;
import java.util.logging.Logger;


public class ProxyServerThread extends Thread  
{    
    ProxyController p;
    
    public ProxyServerThread(ProxyController p) {
        this.p = p;
    }
    public void run()  
    {    
        (new Server(p)).start();
        
    }    
}