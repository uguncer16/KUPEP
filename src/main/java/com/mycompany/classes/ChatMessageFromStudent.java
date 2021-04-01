/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.classes;

public class ChatMessageFromStudent extends ChatMessage {
    String username;

    public String getUsername() {
        return username;
    }
    public ChatMessageFromStudent(String message, String username) {
        super(message);
        this.username = username;
    }

    
}
