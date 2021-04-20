package com.mycompany.kupep;
import com.mycompany.kupep.ObjectEchoClient;
import com.mycompany.kupep.ObjectEchoServer;
import com.mycompany.kupep.ServerController;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientThread extends Thread  
{    
    ServerController s;
    
    public ClientThread(ServerController s) {
        this.s = s;
    }
    public void run()  
    {    

        try {
            ObjectEchoClient.go(s);
        } catch (Exception ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    

}   