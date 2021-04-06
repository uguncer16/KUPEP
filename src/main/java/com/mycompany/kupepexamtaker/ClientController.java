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

public class ClientController {
    private ExamTaker examTakerGUI;
    ChannelHandlerContext ctx;

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }
    public ClientController(){
        examTakerGUI = new ExamTaker(this);
    }
    
    public void setTimeRemaining(String t) {
        examTakerGUI.setTimeRemaining(t);
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
