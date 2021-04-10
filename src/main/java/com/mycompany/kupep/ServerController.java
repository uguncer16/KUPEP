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
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class ServerController {
    private ExaminerForm examinerFormGUI;
    private HashMap<String,Student> studentList;
    private HashMap<String,ArrayList<ChatMessage>> messageList;
    private MiniChat miniChat;
    private ExamSettings examSettingsForm;
    private ExamSetting examSetting;
    private int timeRemaining;
    private Timer timer;
    private boolean examStarted;

    public boolean isExamStarted() {
        return examStarted;
    }

    public void setExamStarted(boolean examStarted) {
        this.examStarted = examStarted;
    }

    public void setMiniChat(MiniChat miniChat) {
        this.miniChat = miniChat;
    }
    
    public void setExamSettingsForm(ExamSettings examSettingsForm) {
        this.examSettingsForm = examSettingsForm;
    }
    
    void updateExamSettings(){
        examinerFormGUI.updateExamSettings(examSetting);
        this.timeRemaining = examSetting.getExamDuration()*60;
        sendExamSetting();
        
        
    }
    
    public ServerController(){
        examinerFormGUI = new ExaminerForm(this);
        studentList = new HashMap<String,Student>();
        messageList = new HashMap<String,ArrayList<ChatMessage>>();
        this.miniChat = null;
        this.examSettingsForm = null;
        this.timer = new Timer();
        this.examStarted = false;
    }
    
    public void startExam(){
        TimerTask task = new TimerTask(){
        private int i = 0;
        public void run(){
            
                timeRemaining--;
                examinerFormGUI.updateTimeRemaining(getTimeRemaining());
                TimeRemaining tr = new TimeRemaining(getTimeRemaining());
                sendMessageToAllStudents(tr);
                
        }
    };
    timer.scheduleAtFixedRate(task, 0, 1000); //1000ms = 1sec
    this.examStarted = true;
    //FileMessage fm = new FileMessage(new File("Coursework.zip"));
    //sendMessageToAllStudents(fm);
    }
    
    public void sendExamSetting(){

    
        sendMessageToAllStudents(this.examSetting);
    }
    
    public void sendExamFile() {
       FileMessage fm = new FileMessage(new File(this.examSetting.getExamFile()));
        sendMessageToAllStudents(fm);
    }
    public String getTimeRemaining(){
      String hh = String.format("%02d",timeRemaining/3600)+":";
      String mm = String.format("%02d",timeRemaining/60%60)+":";
      String ss = String.format("%02d",timeRemaining%60);
      
        return hh+mm+ss;
    }
    
    public void openExamSettings(){
        if (this.examSettingsForm==null) {
            if (this.examSetting==null) {
                this.examSetting = new ExamSetting();
            }
            ExamSettings examSettingsForm = new ExamSettings(this,this.examSetting);    
            this.examSettingsForm = examSettingsForm;
        }
                

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
    
    public void sendMessageToAllStudents(Object msg){
        
        for (String username : studentList.keySet()) {
                Student student = studentList.get(username);
                ChannelFuture future = student.getCtx().writeAndFlush(msg);    
                future.addListener(FIRE_EXCEPTION_ON_FAILURE);
            
        }
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
