/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.kupepexamtaker;
import com.mycompany.classes.*;
import io.netty.channel.ChannelFuture;
import static io.netty.channel.ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE;
import io.netty.channel.ChannelHandlerContext;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

public class ClientController {
    private ExamTaker examTakerGUI;
    ChannelHandlerContext ctx;
    ExamSetting examSetting;
    FileMessage fm;
    String submitFile;
    private Timer timer;
    TimerTask task;

    public void helpNeeded(boolean b) {
        HelpNeeded h = new HelpNeeded(System.getProperty("user.name"),b);
        ChannelFuture future = ctx.writeAndFlush(h);
        future.addListener(FIRE_EXCEPTION_ON_FAILURE);
        
    }
    
    public String getSubmitFile() {
        return submitFile;
    }

    public void setSubmitFile(String submitFile) {
        this.submitFile = submitFile;
    }
    
    public void sendSubmitFile() {
       FileMessage fm = new FileMessage(new File(this.submitFile),System.getProperty("user.name"));
                ChannelFuture future = ctx.writeAndFlush(fm);
        future.addListener(FIRE_EXCEPTION_ON_FAILURE);
    }
    
    public void setExamFile(FileMessage fm) {
            try {
                this.fm = fm;
                FileUtils.writeByteArrayToFile(new File(fm.getFile().getName()), fm.getBytes());
                examTakerGUI.setExamInformation(true);
            } catch (IOException ex) {
                Logger.getLogger(ObjectEchoClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public String getExamFile(){
        return fm.getFile().getName();
    }
    
    public void setExamSetting(ExamSetting examSetting) {
        this.examSetting = examSetting;
        examTakerGUI.updateExamSettings(examSetting);
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
        task = new TimerTask(){
        public void run(){
              IAMAlive a = new IAMAlive(System.getProperty("user.name"));
              sendMessage(a);
              
                
        }
    };
    timer.scheduleAtFixedRate(task, 0, 10000); //1000ms = 1sec
    }
    public ClientController(){
        examTakerGUI = new ExamTaker(this);
        this.timer = new Timer();

    }
    

    
    public void sendMessage(Object msg){
        ChannelFuture future = ctx.writeAndFlush(msg);
    }
    public void setTimeRemaining(String t) {
        examTakerGUI.setTimeRemaining(t);
    }
    
    public void informExamStarted(ExamStarted examStarted) {
        examTakerGUI.informExamStarted(examStarted.getDuration());
    }
    
    public void informExamExtended(ExamExtended examExtended) {
        examTakerGUI.informExamExtended(examExtended.getDuration());
    }
    
    public void informExamStopped(ExamStopped examStopped) {
        examTakerGUI.informExamStopped();
    }
    public void sendChatMessage(ChatMessageFromStudent chatMessage) {
        
        ChannelFuture future = ctx.writeAndFlush(chatMessage);
        future.addListener(FIRE_EXCEPTION_ON_FAILURE);
        
    }
    public void receiveChatMessageToStudent(ChatMessageToStudent chatMessageToStudent){
        examTakerGUI.receiveChatMessageToStudent(chatMessageToStudent);
        
    }
    public void go() throws Exception{
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                examTakerGUI.setVisible(true);
            }
        });
        ObjectEchoClient.go(this);
    }
    public static void main(String args[]) throws Exception {
        ClientController cController = new ClientController();
        cController.go();
        
    }
    
}
