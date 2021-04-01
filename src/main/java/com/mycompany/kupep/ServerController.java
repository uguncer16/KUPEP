/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.kupep;

import com.mycompany.classes.*;
import io.netty.channel.ChannelFuture;
import static io.netty.channel.ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE;
import io.netty.channel.ChannelHandlerContext;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerController {
    private ExaminerForm examinerFormGUI;
    private HashMap<String,Student> studentList;
    private HashMap<String,ArrayList<ChatMessage>> messageList;
    private MiniChat miniChat;

    public void setMiniChat(MiniChat miniChat) {
        this.miniChat = miniChat;
    }
    
    
    
    public ServerController(){
        examinerFormGUI = new ExaminerForm(this);
        studentList = new HashMap<String,Student>();
        messageList = new HashMap<String,ArrayList<ChatMessage>>();
    }
    
    public void openChat(String username){
            if (studentList.containsKey(username)) {
                Student s = studentList.get(username);
                if (this.miniChat==null) {
                    MiniChat minichat = new MiniChat(this,s);    
                    this.miniChat =  minichat;
                }
                
            }
    }
    public ArrayList<ChatMessage> getMessages(String username) {
        return messageList.get(username);
        
    }
    public void sendChatMessage(ChatMessageToStudent chatMessage,Student student) {
        
        ChannelFuture future = student.getCtx().writeAndFlush(chatMessage);
        future.addListener(FIRE_EXCEPTION_ON_FAILURE);
        messageList.get(student.getUsername()).add(chatMessage);
        
    }
    public void recieveMessage(ChannelHandlerContext ctx,Object msg) {
        if (msg instanceof ClientArrived) {
            ClientArrived message = (ClientArrived)msg;
            if (!studentList.containsKey(message.getUsername())) {
                Student s = new Student(ctx,message.getUsername() ,message.getIP(), "YES");
                studentList.put(message.getUsername(), s);
                messageList.put(message.getUsername(),new ArrayList<ChatMessage>());
                examinerFormGUI.clientArrived(message);    
            }
            
        }
        if (msg instanceof ChatMessageFromStudent) {
            ChatMessageFromStudent chatMessagFromStudent = (ChatMessageFromStudent)msg;
            if (miniChat!=null) {
                miniChat.receiveChatMessageFromStudent(chatMessagFromStudent);
            } else {
                examinerFormGUI.setNewMessage(chatMessagFromStudent);
            }
            messageList.get(chatMessagFromStudent.getUsername()).add(chatMessagFromStudent);
        }
        
        
    }
    public void go() throws Exception{
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                examinerFormGUI.setVisible(true);
            }
        });
        ObjectEchoServer.go(this);
    }
    public static void main(String args[]) throws Exception {
        ServerController sController = new ServerController();
        sController.go();
        
    }    
}
