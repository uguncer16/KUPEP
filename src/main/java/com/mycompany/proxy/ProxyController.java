/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proxy;

import io.netty.channel.ChannelHandlerContext;
import java.util.ArrayList;
import com.mycompany.classes.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProxyController {
    ChannelHandlerContext examiner;
    ArrayList<String> bannedSites;
    boolean internetEnabled=true;
    
    public void recieveMessage(ChannelHandlerContext ctx, Object msg){
        if (msg instanceof BannedSites) {
            this.bannedSites = ((BannedSites)msg).getSites();
        } else if (msg instanceof ExamSetting) {
            this.internetEnabled = ((ExamSetting)msg).isInternetEnabled();
        }
    }
    
    public ProxyController(){
        bannedSites = new ArrayList<String>();
    }

    public ChannelHandlerContext getExaminer() {
        return examiner;
    }

    public void setExaminer(ChannelHandlerContext examiner) {
        this.examiner = examiner;
    }

    public ArrayList<String> getBannedSites() {
        return bannedSites;
    }

    public void setBannedSites(ArrayList<String> bannedSites) {
        this.bannedSites = bannedSites;
    }
    
    
    public void go() throws Exception{

        
       ProxyServerThread t1 = new ProxyServerThread(this);
       Server2Thread t2 = new Server2Thread(this);
       
       t1.start();
       t2.start();
       
        
    }
    public static void main(String args[]) throws Exception {
        ProxyController pController = new ProxyController();
        pController.go();
        
    }   
}
