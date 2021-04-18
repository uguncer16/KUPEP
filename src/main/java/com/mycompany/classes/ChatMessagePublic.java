/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.classes;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class ChatMessagePublic implements Serializable  {
    String message;
    String time;
    String Id;
    
   public ChatMessagePublic(String message) {
        this.message = message;
        this.time = new SimpleDateFormat("HH:mm").format(new java.util.Date());
        this.Id = UUID.randomUUID().toString();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

   
    
}
