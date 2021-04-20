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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

import java.time.*;

public class ServerController {
    private ExaminerForm examinerFormGUI;
    private HashMap<String,Student> studentList;
    private HashMap<String,ArrayList<ChatMessage>> messageList;
    private ArrayList<ChatMessagePublic> publicmessageList;
    private HashMap<String,Boolean> helpList;
    private HashMap<String,FileMessage> studentFiles;
    private MiniChat miniChat;
    private PublicMessageBox publicMessageBox;
    private ExamSettings examSettingsForm;
    private ExamSetting examSetting;
    private int timeRemaining;
    private Timer timer;
    private Timer timer2;
    private boolean examStarted;
    private boolean examStopped;
    private AddExtraTime addExtraTimeForm;
    private HashMap<String,LocalDateTime> lastSeen;
    ChannelHandlerContext proxyCtx;

    public void setProxyCtx(ChannelHandlerContext ctx) {
        this.proxyCtx = ctx;
    }
            
    public PublicMessageBox getPublicMessageBox() {
        return publicMessageBox;
    }

    public void setPublicMessageBox(PublicMessageBox publicMessageBox) {
        this.publicMessageBox = publicMessageBox;
    }

    
    
    public  ArrayList<ChatMessagePublic> getPublicmessageList() {
        return publicmessageList;
    }



    
    
    public boolean isExamStarted() {
        return examStarted;
    }

    public void setPmEnabled(boolean b, String s) {
        Student student = studentList.get(s);
        student.setPMEnabled(b);
        ChannelFuture future = student.getCtx().writeAndFlush(new PMEnabled(b));
        future.addListener(FIRE_EXCEPTION_ON_FAILURE);
    }
    public void notifyHelpComing(String username) {
        Student student = studentList.get(username);
        ChannelFuture future = student.getCtx().writeAndFlush(new HelpComing());
        future.addListener(FIRE_EXCEPTION_ON_FAILURE);
        
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
    
    public void addExtraTime(int t) {
        timeRemaining += t*60;
        ExamExtended examExtended = new ExamExtended(t);
        sendMessageToAllStudents(examExtended);
    }
    
    public void setAddExtraTimeForm(AddExtraTime e) {
        this.addExtraTimeForm = e;
    }
    void updateExamSettings(){
        examinerFormGUI.updateExamSettings(examSetting);
        this.timeRemaining = examSetting.getExamDuration()*60;
        sendExamSetting();
        
        
    }
    
    void helpEnabled(boolean b){
        examSetting.setHelpEnabled(b);
        sendExamSetting();
        
    }
    void usbEnabled(boolean b){
        examSetting.setUsbEnabled(b);
        sendExamSetting();
        
    }
    
    public ServerController(){
        examinerFormGUI = new ExaminerForm(this);
        studentList = new HashMap<String,Student>();
        messageList = new HashMap<String,ArrayList<ChatMessage>>();
        publicmessageList = new ArrayList<ChatMessagePublic>() ;
        studentFiles = new HashMap<String,FileMessage> ();
        helpList = new HashMap<String,Boolean>();
        lastSeen = new HashMap<String,LocalDateTime> ();
        this.miniChat = null;
        this.publicMessageBox = null;
        this.examSettingsForm = null;
        this.timer = new Timer();
        this.timer2 = new Timer();
        this.examStarted = false;
        this.examSetting = new ExamSetting();
    }
    
    public void updateLastSeen(String s) {
        lastSeen.put(s,LocalDateTime.now());
    }
    
    public void checkWhoIsOnline(){
        TimerTask task = new TimerTask(){
        public void run(){
            for (String u:lastSeen.keySet()) {
                LocalDateTime lastSeenAt = lastSeen.get(u);
                if (Duration.between(lastSeenAt,LocalDateTime.now()).toSeconds()>=15 && !studentFiles.containsKey(u) ){
                    examinerFormGUI.populateDisconnectedList(studentList.get(u), lastSeenAt);
                }
            }
                
        }
    };
        timer2.scheduleAtFixedRate(task, 0, 10000); //1000ms = 1sec
    }
    
    public void startExam(){
        checkWhoIsOnline();
        TimerTask task = new TimerTask(){
        private int i = 0;
        public void run(){
            
                timeRemaining--;
                examinerFormGUI.updateTimeRemaining(getTimeRemaining());
                TimeRemaining tr = new TimeRemaining(getTimeRemaining());
                sendMessageToAllStudents(tr);
                if (timeRemaining==0) {
                    examStopped = true;
                    ExamStopped examStopped = new ExamStopped();
                    sendMessageToAllStudents(examStopped);
                    cancel();
                }
                
        }
    };
    timer.scheduleAtFixedRate(task, 0, 1000); //1000ms = 1sec
    this.examStarted = true;
    ExamStarted examStarted = new ExamStarted(examSetting.getExamDuration());
    //FileMessage fm = new FileMessage(new File("Coursework.zip"));
    sendMessageToAllStudents(examStarted);
    examinerFormGUI.enableButtonsAfterStartExam();
    }
    
    public void sendExamSetting(){

        String sites = examSetting.getBannedSites();
        String site[] = sites.split(" ");
        ArrayList<String> b= new ArrayList<String>();
        for (String c: site) {
            b.add(c);
        }
        
        BannedSites bannedSites = new BannedSites(b);
        
        ChannelFuture future = proxyCtx.writeAndFlush(bannedSites);    
        future.addListener(FIRE_EXCEPTION_ON_FAILURE);
        
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
    
    public void openAddExtraTime(){
                if (this.addExtraTimeForm==null) {
                    this.addExtraTimeForm =  new AddExtraTime(this);    
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
    public void openPublicMessageBox(){

                if (this.publicMessageBox==null) {
                    PublicMessageBox publicMessageBox = new PublicMessageBox(this);    
                    this.publicMessageBox =  publicMessageBox;
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
    
    public void sendPublicMessage(ChatMessagePublic publicMessage) {
        
        sendMessageToAllStudents(publicMessage);
        publicmessageList.add(publicMessage);
        
    }
    public void sendUpdates(String username){
        Student s = studentList.get(username);
        ChannelFuture future;
        future = s.getCtx().writeAndFlush(new  OpenDialog(false));
        future = s.getCtx().writeAndFlush(examSetting);
        if (this.examSetting.getExamFile()!=null)
            future = s.getCtx().writeAndFlush(new FileMessage(new File(this.examSetting.getExamFile())));
        future = s.getCtx().writeAndFlush(new PMEnabled(s.isPMEnabled()));
        if (this.examStarted==true && this.examStopped==false)
            future = s.getCtx().writeAndFlush(new ExamStarted(this.examSetting.getExamDuration()));
        else if (this.examStopped==true)
            future = s.getCtx().writeAndFlush(new ExamStopped());
        
        for (ChatMessage c:messageList.get(username)) {
            ChatMessageToStudent cs = new ChatMessageToStudent(c.getMessage());
            future = s.getCtx().writeAndFlush(cs);
        }
        for (ChatMessagePublic c:publicmessageList) {
            ChatMessagePublic cs = new ChatMessagePublic(c.getMessage());
            future = s.getCtx().writeAndFlush(cs);
        }
        future = s.getCtx().writeAndFlush(new  OpenDialog(true));
        future.addListener(FIRE_EXCEPTION_ON_FAILURE);
        
    }
    
    public void recieveMessage(ChannelHandlerContext ctx,Object msg) {
        if (msg instanceof ClientArrived) {
            ClientArrived message = (ClientArrived)msg;
            Student s;
            if (!studentList.containsKey(message.getUsername())) {
                s = new Student(ctx,message.getUsername() ,message.getIP(), "YES");
                s.setCompNo(message.getCompNo());
                s.setOS(message.getOS());
                studentList.put(message.getUsername(), s);
                messageList.put(message.getUsername(),new ArrayList<ChatMessage>());
                examinerFormGUI.clientArrived(studentList.get(message.getUsername()));    
            } else {
                s = studentList.get(message.getUsername());
                s.setCompNo(message.getCompNo());
                s.setOS(message.getOS());
                s.setCtx(ctx);
                s.setIP(message.getIP());
                examinerFormGUI.clientBackAgain(studentList.get(message.getUsername()));    
            }
            
            updateLastSeen(message.getUsername());
            sendUpdates(message.getUsername());
            
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
        if (msg instanceof FileMessage) {
            FileMessage fm = (FileMessage)msg;
            try {
                
                FileUtils.writeByteArrayToFile(new File(".\\submittedFiles/"+fm.getUsername()+"/"+fm.getFile().getName()), fm.getBytes());
                this.studentFiles.put(fm.getUsername(),fm );
                studentList.get(fm.getUsername()).setSubmitted(true);
                
                
            } catch (IOException ex) {
                Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            examinerFormGUI.clientSubmitted(fm.getUsername());

        }
        if (msg instanceof HelpNeeded) {
            HelpNeeded h = (HelpNeeded)msg;
            helpList.put(h.getUsername(), h.isHelpNeeded());
            if (h.isHelpNeeded()) {
                if (studentList.containsKey(h.getUsername()))
                    examinerFormGUI.clientNeedsHelp(studentList.get(h.getUsername()));
            } else {
                if (studentList.containsKey(h.getUsername()))
                    examinerFormGUI.clientCancelsHelp(studentList.get(h.getUsername()));
            }
            
        }
        if (msg instanceof IAMAlive) {
            IAMAlive h = (IAMAlive)msg;
            updateLastSeen(h.getUsername());
        }
        
    }
    public void go() throws Exception{
        
        examinerFormGUI.setVisible(true);

        ClientThread c = new ClientThread(this);        
        ServerThread s = new ServerThread(this);                          

        
        s.run();
        c.run();

                    
                    
        
        
    }
    public static void main(String args[]) throws Exception {
        ServerController sController = new ServerController();
        sController.go();
        
    }    
}
