/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.classes;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.UUID;


public class ChatMessage implements Serializable {
    String message;
    String time;
    String Id;
    boolean isRead;
    
        public ChatMessage(String message) {
        this.message = message;
        this.time = new SimpleDateFormat("HH:mm").format(new java.util.Date());
        this.isRead = false;
        this.Id = UUID.randomUUID().toString();
    }

    public String getMessage() {
        return message;
    }


    public String getTime() {
        return time;
    }


    public String getId() {
        return Id;
    }


    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
    
}
